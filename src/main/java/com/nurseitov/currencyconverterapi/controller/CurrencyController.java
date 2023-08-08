package com.nurseitov.currencyconverterapi.controller;


import com.nurseitov.currencyconverterapi.DTO.CurrencyDTO;
import com.nurseitov.currencyconverterapi.service.CurrencyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CurrencyController {

    private CurrencyService currencyConverterService;

    @GetMapping("/convert")
    public Float convertCurrency(@RequestBody CurrencyDTO currencyDTO) {
        return currencyConverterService.convertCurrency(currencyDTO.getSourceCurrencyCode(),
                currencyDTO.getTargetCurrencyCode(), currencyDTO.getValue());
    }

}
