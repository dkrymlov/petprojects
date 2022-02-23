package com.krymlov.lab1.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Country {

    @Min(1)
    private Long id;

    @NotEmpty
    @NotNull
    private String name;

    public Country(String name) {
        this.name = name;
    }

    public Country(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
