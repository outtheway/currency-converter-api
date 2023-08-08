package com.nurseitov.currencyconverterapi.DTO;

import lombok.Data;

@Data
public class CurrencyDTO {
    private String sourceCurrencyCode;
    private String targetCurrencyCode;
    private Float value;
}
