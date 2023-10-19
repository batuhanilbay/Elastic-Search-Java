package com.spring.elasticsearch.controller;

import com.spring.elasticsearch.entity.Car;
import com.spring.elasticsearch.service.CarServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarServiceImpl carService;

    @PostMapping(value = "/save")
    public Car save(@RequestBody Car car)  {
        return carService.save(car);
    }

    @GetMapping(value = "/{brand}")
    public ResponseEntity<List<Car>> findCarsByBrand(@PathVariable String brand) {
        List<Car> cars = carService.getCarsByBrand(brand);
        return ResponseEntity.ok(cars);
    }
    @GetMapping(value = "/get-cars")
    public ResponseEntity<Iterable<Car>> getAllCars() {
        Iterable<Car> cars = carService.getAllCars();
        return ResponseEntity.ok(cars);
    }
    @GetMapping(value = "/get-cars-model-name")
    public ResponseEntity<List<Car>> getCarsByModelName(@RequestParam("modelName") String modelName) {
        List<Car> cars = carService.getCarsByModel(modelName);
        return ResponseEntity.ok(cars);
    }
    @GetMapping(value = "/less-than-criteria/{price}")
    public ResponseEntity<List<Car>> findCarsWithLessThanCriteria(@PathVariable Long price) {
        List<Car> cars = carService.findCarPriceWithLessThanCriteria(price);
        return ResponseEntity.ok(cars);
    }
    @GetMapping(value = "/greater-than-criteria/{price}")
    public ResponseEntity<List<Car>> findCarsWithGreaterThanCriteria(@PathVariable Long price) {
        List<Car> cars = carService.findCarPriceWithGreaterThanCriteria(price);

        return ResponseEntity.ok(cars);
    }

    @GetMapping(value = "/interval-criteria/{upperPrice}/{lowerPrice}")
    public ResponseEntity<List<Car>> findCarsWithInterval(@PathVariable Long upperPrice,@PathVariable Long lowerPrice) {
        List<Car> cars = carService.findCarUpperAndLowerPriceInterval(upperPrice,lowerPrice);
        return ResponseEntity.ok(cars);
    }

    @GetMapping(value = "/get-cars-with-model")
    public ResponseEntity<List<Car>> getCarsByModelNameWithStringQuery(@RequestParam("model") String model) {
        List<Car> cars = carService.findCarsByModel(model);
        return ResponseEntity.ok(cars);
    }

    @GetMapping(value = "/get-cars-with-car-type")
    public ResponseEntity<List<Car>> getCarsByTypeWithNativeQuery(@RequestParam("type") String type) {
        List<Car> cars = carService.findCarsByCarType(type);
        return ResponseEntity.ok(cars);
    }

    @GetMapping(value = "/get-cars-with-fuel-type")
    public ResponseEntity<List<Car>> getCarsByFuelType(@RequestParam("type") String type) {
        List<Car> cars = carService.findCarsByFuelType(type);
        return ResponseEntity.ok(cars);
    }
}
