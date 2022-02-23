package com.krymlov.lab1.entity;

import com.krymlov.lab1.model.Country;

import javax.persistence.*;

@Entity
public class CountryEntity extends Country {

    public CountryEntity(String name) {
        super(name);
    }

    public CountryEntity() {
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
}
