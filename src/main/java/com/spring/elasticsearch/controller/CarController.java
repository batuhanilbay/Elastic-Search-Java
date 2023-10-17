package com.spring.elasticsearch.controller;

import com.spring.elasticsearch.entity.Car;
import com.spring.elasticsearch.service.CarServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
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
    @GetMapping(value = "/getAll")
    public ResponseEntity<Iterable<Car>> getAllCars() {
        Iterable<Car> cars = carService.getAllCars();
        return ResponseEntity.ok(cars);
    }

    @GetMapping(value = "/get-car-with-model-name")
    public ResponseEntity<List<Car>> getCarsByModelName(@RequestParam("modelName") String modelName) {
        List<Car> cars = carService.getCarsByModel(modelName);
        return ResponseEntity.ok(cars);
    }
}
