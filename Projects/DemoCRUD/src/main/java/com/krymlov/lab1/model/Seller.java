package com.krymlov.lab1.model;

import com.sun.istack.NotNull;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class Seller {

    @Min(1)
    private Long id;

    @NotEmpty
    @NotNull
    private String name;

    @NotEmpty
    @NotNull
    private String info;

    @NotEmpty
    @NotNull
    @Range(min=6, max=6)
    @Min(100000)
    @Max(999999)
    private Long accreditation;

    public Seller(@NotEmpty String name, @NotEmpty String info, @NotEmpty @Range(min = 6, max = 6) @Min(100000) @Max(999999) Long accreditation) {
        this.name = name;
        this.info = info;
        this.accreditation = accreditation;
    }

    public Seller(){}

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

    public Long getAccreditation() {
        return accreditation;
    }

    public void setAccreditation(Long accreditation) {
        this.accreditation = accreditation;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
