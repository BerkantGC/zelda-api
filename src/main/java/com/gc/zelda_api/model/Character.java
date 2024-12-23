package com.gc.zelda_api.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "Characters")
public class Character {
    private String name;
    private String description;
    private String gender;
    private String race;
    private String id;
    private List<String> appearances;
}
