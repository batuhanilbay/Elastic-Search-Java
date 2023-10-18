package com.spring.elasticsearch.service;


import com.spring.elasticsearch.entity.Car;
import java.util.List;

public interface CarService {

    List<Car> getCarsByBrand(String brand);
    Car save(Car car);
    Iterable<Car> getAllCars();
    List<Car> getCarsByModel(String model);
    List<Car> findCarPriceWithLessThanCriteria(Long price);
    List<Car> findCarPriceWithGreaterThanCriteria(Long price);
    List<Car> findCarUpperAndLowerPriceInterval(Long upperPrice,Long lowerPrice);
    List<Car> findCarsByModel(String model);
    List<Car> findCarsByCarType(String type);



}
