package com.krymlov.lab1.model;

import com.krymlov.lab1.entity.CountryEntity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Brand {

    @Min(1)
    private Long id;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String info;

    @NotNull
    @NotEmpty
    private CountryEntity country;

    public Brand(@NotNull @NotEmpty String name, @NotNull @NotEmpty String info, @NotNull @NotEmpty CountryEntity country) {
        this.name = name;
        this.info = info;
        this.country = country;
    }

    public Brand() {
    }

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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public CountryEntity getCountry() {
        return country;
    }

    public void setCountry(CountryEntity country) {
        this.country = country;
    }

}
