package com.spring.elasticsearch.controller;

import com.spring.elasticsearch.entity.Car;
import com.spring.elasticsearch.service.CarServiceImpl;
import lombok.RequiredArgsConstructor;
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
    public List<Car> findCarsByBrand(@PathVariable String brand) {
        return carService.getCarsByBrand(brand);
    }

}
