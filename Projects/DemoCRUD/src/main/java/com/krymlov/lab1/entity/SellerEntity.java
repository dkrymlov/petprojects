package com.krymlov.lab1.entity;

import com.krymlov.lab1.model.Seller;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Entity
public class SellerEntity extends Seller {

    public SellerEntity(@NotEmpty String name,
                        @NotEmpty String info,
                        @NotEmpty @Range(min = 6, max = 6) @Min(100000) @Max(999999) Long accreditation) {
        super(name, info, accreditation);
    }

    public SellerEntity() {
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
    public Long getAccreditation() {
        return super.getAccreditation();
    }

    @Override
    public void setAccreditation(Long accreditation) {
        super.setAccreditation(accreditation);
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
