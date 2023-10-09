package com.spring.elasticsearch.service;


import com.spring.elasticsearch.entity.Car;
import java.util.List;

public interface CarService {

    List<Car> getCarsByBrand(String brand);

    Car save(Car car);

    Iterable<Car> getAllCars();


}
