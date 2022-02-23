package com.krymlov.lab1.entity;

import com.krymlov.lab1.model.Brand;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class BrandEntity extends Brand {

    public BrandEntity(@NotNull @NotEmpty String name,
                       @NotNull @NotEmpty String info,
                       @NotNull @NotEmpty CountryEntity country) {
        super(name, info, country);
    }

    public BrandEntity() {
        super();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    @Column(length = 50, nullable = false, unique = true)
    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Column(nullable = false)
    @Override
    public String getInfo() {
        return super.getInfo();
    }

    @Override
    public void setInfo(String info) {
        super.setInfo(info);
    }

    @OneToOne
    @Override
    public CountryEntity getCountry() {
        return super.getCountry();
    }

    @Override
    public void setCountry(CountryEntity country) {
        super.setCountry(country);
    }
}
