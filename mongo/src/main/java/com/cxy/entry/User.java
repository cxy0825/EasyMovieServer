package com.cxy.entry;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("user")
public class User {
    @Id
    private Long id;

    private String name;

    String account;
    String password;
    Integer power;
    String type;
    List<Chiyou> chiyou;
    List<Long> shouchang;
}
