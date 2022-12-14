package com.cxy.entry;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document("jijing")
public class jijing {
    @Id
    Long _id;

    String name;

    String type;
    LocalDate birthday;
    Double price;
}
