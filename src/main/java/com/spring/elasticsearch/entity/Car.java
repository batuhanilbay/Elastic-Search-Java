package com.spring.elasticsearch.entity;


import com.spring.elasticsearch.enums.CarType;
import com.spring.elasticsearch.enums.FuelType;
import com.spring.elasticsearch.enums.GearType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "cars")
public class Car {

    @Id
    private String id;
    @Field(name = "brand", type = FieldType.Text)
    private String brand;
    @Field(name = "model", type = FieldType.Text)
    private String model;
    @Field(name = "car_type", type = FieldType.Text)
    private CarType carType;
    @Field(name = "fuel_type", type = FieldType.Text)
    private FuelType fuelType;
    @Field(name = "gear_type", type = FieldType.Text)
    private GearType gearType;
    @Field(name = "price", type = FieldType.Long)
    private Long price;
    @Field(name = "km", type = FieldType.Long)
    private Long km;
    @Field(name = "origin", type = FieldType.Text)
    private String country;
    @Field(name = "timestamp", type = FieldType.Date)
    private Date timestamp;
    @Field(name="update_date",type = FieldType.Date)
    private Date updatedDate;

    @Override
    public String toString() {
        return "Car{" +
                "id='" + id + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                '}';
    }
}
