package com.spring.elasticsearch;



import com.spring.elasticsearch.entity.Car;
import com.spring.elasticsearch.enums.CarType;
import com.spring.elasticsearch.enums.FuelType;
import com.spring.elasticsearch.enums.GearType;
import com.spring.elasticsearch.exceptions.CarNotFoundException;
import com.spring.elasticsearch.repository.CarRepository;
import com.spring.elasticsearch.service.CarService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,properties = {"spring.config.name=docker-compose"})
public class CarServiceTest {

    @Autowired
    private  CarService carService;
    @Autowired
    private  ElasticsearchTemplate template;

    List<Car> allCars = new ArrayList<>();
    @Container
    private static final ElasticsearchContainer elasticsearchContainer = new CarTestContainer();

    @BeforeAll
    static void setUp() {
        elasticsearchContainer.start();

    }

    @BeforeEach
    void testIsContainerRunning() {
        assertTrue(elasticsearchContainer.isRunning());
        recreateIndex();
    }

    @Test
    public void testCreateCar()  {
        Car testCreateCar = carService.save(createCar("Mercedes", "EQ", CarType.SEDAN, FuelType.DIESEL,GearType.AUTO,36000L,10000L,"Germany"));

        assertNotNull(testCreateCar);
        assertNotNull(testCreateCar.getId());
        assertEquals("Mercedes", testCreateCar.getBrand());
        assertEquals("EQ", testCreateCar.getModel());
    }

    @Test
    public void testGetCarsByBrand(){
        performElasticsearchIndexOperations();
        loadCars();

        String searchBrand = "BMW";
        String searchBrandWithLike = "Merc";

        List<Car> searchResult = carService.getCarsByBrand(searchBrand);
        List<Car> searchResultWithLike = carService.getCarsByBrand(searchBrandWithLike);

        assertFalse(searchResultWithLike.isEmpty());
        assertEquals(1,searchResultWithLike.size());

        assertFalse(searchResult.isEmpty());
        assertEquals(2, searchResult.size());
        assertTrue(searchResult.stream().allMatch(car -> car.getBrand().equals(searchBrand)));

    }

    @Test
    public void testFindCarPriceWithLessThanCriteria(){

        performElasticsearchIndexOperations();
        loadCars();

        Long maxPrice = 35900L;
        Long maxPrice2 = 27850L;

        List<Car> cars1 = carService.findCarPriceWithLessThanCriteria(maxPrice);
        List<Car> cars2 = carService.findCarPriceWithLessThanCriteria(maxPrice2);

        assertEquals(4, cars1.size());
        assertEquals(3, cars2.size());

    }

    @Test
    public void testFindCarPriceWithGreaterThanCriteria(){
        performElasticsearchIndexOperations();
        loadCars();

        Long minPrice = 33000L;
        Long minPrice2 = 22400L;

        List<Car> cars1 = carService.findCarPriceWithGreaterThanCriteria(minPrice);
        List<Car> cars2 = carService.findCarPriceWithGreaterThanCriteria(minPrice2);

        assertEquals(1,cars1.size());
        assertEquals(4,cars2.size());
    }

    @Test
    public void testFindCarPriceWithIntervalCriteria(){
        performElasticsearchIndexOperations();
        loadCars();

        Long lowerPrice = 32000L;
        Long upperPrice = 37000L;

        Long lowerPrice2 = 22400L;
        Long upperPrice2 = 36000L;

        List<Car> cars1 = carService.findCarUpperAndLowerPriceInterval(upperPrice,lowerPrice);
        List<Car> cars2 = carService.findCarUpperAndLowerPriceInterval(upperPrice2,lowerPrice2);

        assertEquals(2,cars1.size());
        assertEquals(4,cars2.size());

    }

    @Test
    public void testFindCarsByModel(){
        performElasticsearchIndexOperations();
        loadCars();

        String testModel = "320";
        List<Car> cars = carService.findCarsByModel(testModel);

        String testModel2 = "X5";
        List<Car> cars2 = carService.findCarsByModel(testModel2);

        String testModel3 = "Corolla";
        List<Car> cars3 = carService.findCarsByModel(testModel3);


        assertEquals(1, cars.size());
        assertTrue(cars2.isEmpty());
        assertNotNull(cars3);

    }

    @Test void  testFindCarsByCarType() {
        performElasticsearchIndexOperations();
        loadCars();

        String sedan = "SEDAN";
        String hatchback = "HATCHBACK";
        String invalidCarType = "invalidType";

        List<Car> cars = carService.findCarsByCarType(sedan);
        List<Car> cars2 = carService.findCarsByCarType(hatchback);


        assertEquals(3, cars.size());
        assertFalse(cars2.isEmpty());
        assertEquals(2,cars2.size());


    }


    @Test
    public void testFindCarsByFuelType(){
        performElasticsearchIndexOperations();
        loadCars();

        String gasoline = "GASOLINE";
        String diesel = "DIESEL";
        String electric = "ELECTRIC";

        List<Car> cars = carService.findCarsByFuelType(gasoline);
        List<Car> cars2 = carService.findCarsByFuelType(diesel);
        List<Car> cars3 = carService.findCarsByFuelType(electric);

        assertNotNull(cars);
        assertNotNull(cars2);
        assertEquals(3,cars.size());
        assertEquals(2,cars2.size());
        assertTrue(cars3.isEmpty());


    }

    @Test
    public void testFindCarsByGearType(){
        performElasticsearchIndexOperations();
        loadCars();

        String auto = "AUTO";
        String manuel = "MANUEL";

        List<Car> cars = carService.getCarsByGearType(auto);
        List<Car> cars2 = carService.getCarsByGearType(manuel);

        assertEquals(3,cars.size());
        assertEquals(2,cars2.size());
        assertNotNull(cars);
    }

    @Test
    public void testUpdateCar() throws CarNotFoundException {

        Car testCreateCar = carService.save(createCar("Mercedes", "EQ", CarType.SEDAN, FuelType.DIESEL,GearType.AUTO,36000L,10000L,"Germany"));


        String carId = testCreateCar.getId();

        Car updatedCar = new Car();
        updatedCar.setBrand("Mercedes");
        updatedCar.setModel("EQE");
        updatedCar.setCarType(CarType.SEDAN);
        updatedCar.setFuelType(FuelType.ELECTRIC);
        updatedCar.setGearType(GearType.AUTO);
        updatedCar.setPrice(37250L);
        updatedCar.setKm(44000L);

        // Arabayı güncelleyin
        Car result = carService.updateCar(carId, updatedCar);

        // Güncellenen arabayı kontrol edin
        assertEquals(carId, result.getId());
        assertEquals(testCreateCar.getBrand(), result.getBrand());
        assertEquals(testCreateCar.getCarType(), result.getCarType());
        assertEquals(testCreateCar.getGearType(), result.getGearType());

        assertNotEquals(testCreateCar.getFuelType(), result.getFuelType());
        assertNotEquals(testCreateCar.getModel(), result.getModel());
        assertNotEquals(testCreateCar.getPrice(), result.getPrice());
        assertNotEquals(testCreateCar.getKm(), result.getKm());

        assertThrows(CarNotFoundException.class, () -> {
            carService.updateCar("Invalid id: ", updatedCar);
        });
    }


    @AfterAll
    static void destroy() {
        elasticsearchContainer.stop();
    }

    private Car createCar(String brand,
                          String model,
                          CarType carType,
                          FuelType fuelType,
                          GearType gearType,
                          Long price,
                          Long km,
                          String country
                        ) {
        Date nowDate = new Date();
        Car car = new Car();
        car.setBrand(brand);
        car.setModel(model);
        car.setCarType(carType);
        car.setFuelType(fuelType);
        car.setGearType(gearType);
        car.setPrice(price);
        car.setKm(km);
        car.setCountry(country);
        car.setTimestamp(nowDate);
        car.setUpdatedDate(nowDate);
        return car;
    }


    public void performElasticsearchIndexOperations() {
        template.indexOps(Car.class).delete();
        template.indexOps(Car.class).create();
        template.indexOps(Car.class).putMapping();
        template.indexOps(Car.class).refresh();
    }
    private synchronized void loadCars(){

        Car testCreateCar = carService.save(createCar("Mercedes", "EQ", CarType.SEDAN, FuelType.DIESEL,GearType.AUTO,36000L,10000L,"Germany"));
        Car testCreateCar2 = carService.save(createCar("BMW", "320", CarType.HATCHBACK, FuelType.GASOLINE,GearType.AUTO,27850L,98000L,"Germany"));
        Car testCreateCar3 = carService.save(createCar("BMW", "520", CarType.SEDAN, FuelType.GASOLINE,GearType.AUTO,32000L,77000L,"Germany"));
        Car testCreateCar4 = carService.save(createCar("Ford", "Fiesta", CarType.HATCHBACK, FuelType.GASOLINE,GearType.MANUEL,19250L,123000L,"America"));
        Car testCreateCar5 = carService.save(createCar("Toyota", "Corolla", CarType.SEDAN, FuelType.DIESEL,GearType.MANUEL,22400L,165000L,"Japanese"));


        allCars.add(testCreateCar);
        allCars.add(testCreateCar2);
        allCars.add(testCreateCar3);
        allCars.add(testCreateCar4);
        allCars.add(testCreateCar5);

    }




    private void recreateIndex() {
        if (!template.indexOps(Car.class).exists()) {
            template.indexOps(Car.class).create();
        }
    }
}
