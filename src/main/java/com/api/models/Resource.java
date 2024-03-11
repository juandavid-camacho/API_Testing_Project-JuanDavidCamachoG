package com.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Resource {

    private String name;

    private String trademark;

    private int stock;

    private float price;

    private String description;

    private String tags;

    private Boolean active;

    private String id;

}
