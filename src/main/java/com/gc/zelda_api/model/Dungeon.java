package com.gc.zelda_api.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "Dungeons")
public class Dungeon {
    private String id;
    private String name;
    private String description;
    private List<String> appearances;
}
