package com.spring.elasticsearch.service;


import com.spring.elasticsearch.entity.Car;
import com.spring.elasticsearch.enums.CarType;
import com.spring.elasticsearch.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.erhlc.NativeSearchQuery;
import org.springframework.data.elasticsearch.client.erhlc.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService{

    private final CarRepository carRepository;

    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public List<Car> getCarsByBrand(String brand) {
        return carRepository.findByBrandLike(brand);
    }

    @Override
    public Car save(Car car) {
        Date currentDate = new Date();

        if (car.getTimestamp() == null) {
            car.setTimestamp(currentDate);
        }
        if (car.getUpdatedDate() == null) {
            car.setUpdatedDate(currentDate);
        }

        return carRepository.save(car);
    }
    //lOBlOYsBXbKPnByAQihL
    @Override
    public Iterable<Car> getAllCars() {


        return carRepository.findAll();
    }

    @Override
    public List<Car> getCarsByModel(String model) {
         Page<Car> carsPage =
                carRepository.findByModelName(model, PageRequest.of(0, 20));
        return carsPage.getContent();
    }


    //Criteria Query
    @Override
    public List<Car> findCarPriceWithLessThanCriteria(Long price) {
        Criteria criteria = new Criteria("price")
                .lessThanEqual(price);

        Query searchQuery = new CriteriaQuery(criteria);
        SearchHits<Car> searchCars = elasticsearchOperations
                .search(searchQuery,
                        Car.class);

        return searchCars.stream().map(SearchHit::getContent).collect(Collectors.toList());
    }

    @Override
    public List<Car> findCarPriceWithGreaterThanCriteria(Long price) {
        Criteria criteria = new Criteria("price")
                .greaterThanEqual(price);

        Query searchQuery = new CriteriaQuery(criteria);
        SearchHits<Car> searchCars = elasticsearchOperations
                .search(searchQuery,
                        Car.class);

        return searchCars.stream().map(SearchHit::getContent).collect(Collectors.toList());
    }

    @Override
    public List<Car> findCarUpperAndLowerPriceInterval(Long upperPrice, Long lowerPrice) {
        Criteria criteria = new Criteria("price")
                .greaterThanEqual(lowerPrice)
                .lessThanEqual(upperPrice);

        Query searchQuery = new CriteriaQuery(criteria);
        SearchHits<Car> searchCars = elasticsearchOperations
                .search(searchQuery,
                        Car.class);

        return searchCars.stream().map(SearchHit::getContent).collect(Collectors.toList());

    }

    //StringQuery
    @Override
    public List<Car> findCarsByModel(String model) {

        Query searchQuery = new StringQuery(
                "{\"match\":{\"model\":{\"query\":\""+ model + "\"}}}\"");
        SearchHits<Car> cars = elasticsearchOperations.search(
                searchQuery,
                Car.class);
        return cars.stream().map(SearchHit::getContent).collect(Collectors.toList());


    }

    //Native-Query
    @Override
    public List<Car> findCarsByCarType(String type) {

        CarType carType = CarType.valueOf(type.toUpperCase());

        Query nativeQuery = NativeQuery.builder()
                .withQuery(q -> q.match(m -> m.field("car_type").query(carType.toString()))).build();

        SearchHits<Car> cars = elasticsearchOperations.search(
                nativeQuery,
                Car.class);

        return cars.stream().map(SearchHit::getContent).collect(Collectors.toList());
    }


}
