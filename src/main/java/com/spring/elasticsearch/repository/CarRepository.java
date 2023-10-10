package com.spring.elasticsearch.repository;

import com.spring.elasticsearch.entity.Car;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CarRepository extends ElasticsearchRepository<Car,Long> {

    List<Car> findByBrand(String brand);

}
