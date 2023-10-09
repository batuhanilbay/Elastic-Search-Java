package com.spring.elasticsearch.service;


import com.spring.elasticsearch.entity.Car;
import com.spring.elasticsearch.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
        return carRepository.save(car);
    }

    @Override
    public Iterable<Car> getAllCars() {


        return carRepository.findAll();
    }
}
