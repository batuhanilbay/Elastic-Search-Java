package com.spring.elasticsearch.repository;

import com.spring.elasticsearch.entity.Car;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
@Repository
public interface CarRepository extends ElasticsearchRepository<Car,Long> {

    List<Car> findByBrandLike(String brand);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"model\": \"?0\"}}]}}")
    Page<Car> findByModelName(String modelName, Pageable pageable);



}
