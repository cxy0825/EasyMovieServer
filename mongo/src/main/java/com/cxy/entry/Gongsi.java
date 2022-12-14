package com.cxy.entry;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Document
@Data
public class Gongsi implements Serializable {
    @Id
    Long id;
    String name;
    String gsdm;
    LocalDate birthDay;
    String type;
    String ceo;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    List<Long> jijing;
    List<jijing> jijings;
}
