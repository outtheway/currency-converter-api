package com.nurseitov.currencyconverterapi.model;

import lombok.Data;

@Data
public class Currency {
    private String id;
    private String numCode;
    private String charCode;
    private Long nominal;
    private String name;
    private Float value;
}
