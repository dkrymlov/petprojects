package com.krymlov.lab1.entity;

import com.krymlov.lab1.model.Category;

import javax.persistence.*;

@Entity
public class CategoryEntity extends Category {

    public CategoryEntity() {
        super();
    }

    public CategoryEntity(String name, String info) {
        super(name, info);
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

    @Column(nullable = false, length = 50, unique = true)
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

}
