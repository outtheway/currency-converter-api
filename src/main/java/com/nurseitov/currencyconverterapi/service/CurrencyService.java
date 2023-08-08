package com.nurseitov.currencyconverterapi.service;

import com.nurseitov.currencyconverterapi.model.Currency;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class CurrencyService {
    public Float convertCurrency(String sourceCurrencyCode, String targetCurrencyCode, Float value) {
        List<Currency> currencies = parseCurrenciesFromURL("https://www.cbr-xml-daily.ru/daily_eng.xml");

        float sourceCurrencyValue = 1.0f;
        float targetCurrencyValue = 1.0f;

        for (Currency currency : currencies) {
            if (currency.getCharCode().equals(sourceCurrencyCode)) {
                sourceCurrencyValue = currency.getValue() / currency.getNominal();
            } else if (currency.getCharCode().equals(targetCurrencyCode)) {
                targetCurrencyValue = currency.getValue() / currency.getNominal();
            }
        }

        return (value * sourceCurrencyValue) / targetCurrencyValue;
    }
    public static List<Currency> parseCurrenciesFromURL(String url) {
        List<Currency> currencies = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            URL xmlUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) xmlUrl.openConnection();
            connection.setRequestMethod("GET");

            InputStream is = connection.getInputStream();
            Document document = builder.parse(is);

            Element rootElement = document.getDocumentElement();
            NodeList valuteNodes = rootElement.getElementsByTagName("Valute");

            for (int i = 0; i < valuteNodes.getLength(); i++) {
                Element valuteElement = (Element) valuteNodes.item(i);

                String id = valuteElement.getAttribute("ID");
                String numCode = getElementValue(valuteElement, "NumCode");
                String charCode = getElementValue(valuteElement, "CharCode");
                Long nominal = Long.parseLong(getElementValue(valuteElement, "Nominal"));
                String name = getElementValue(valuteElement, "Name");
                Float value = Float.parseFloat(getElementValue(valuteElement, "Value").replace(',', '.'));

                Currency currency = new Currency();
                currency.setId(id);
                currency.setNumCode(numCode);
                currency.setCharCode(charCode);
                currency.setNominal(nominal);
                currency.setName(name);
                currency.setValue(value);

                currencies.add(currency);
            }

            is.close();
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return currencies;
    }

    private static String getElementValue(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList != null && nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return null;
    }
}
