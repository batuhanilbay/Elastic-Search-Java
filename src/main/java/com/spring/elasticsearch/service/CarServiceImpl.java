package com.spring.elasticsearch.service;


import com.spring.elasticsearch.entity.Car;
import com.spring.elasticsearch.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService{

    private final CarRepository carRepository;



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


}
