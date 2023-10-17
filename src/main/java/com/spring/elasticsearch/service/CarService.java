package com.spring.elasticsearch.service;


import com.spring.elasticsearch.entity.Car;
import java.util.List;

public interface CarService {

    List<Car> getCarsByBrand(String brand);
    Car save(Car car);
    Iterable<Car> getAllCars();
    List<Car> getCarsByModel(String model);
    List<Car> findByCarPriceWithLessThanCriteria(Long price);
    List<Car> findByCarPriceWithGreaterThanCriteria(Long price);
    List<Car> findByCarUpperAndLowerPriceInterval(Long upperPrice,Long lowerPrice);
    List<Car> findByCarsByModel(String model);




}
