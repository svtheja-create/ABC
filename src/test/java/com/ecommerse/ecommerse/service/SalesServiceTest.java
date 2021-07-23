package com.ecommerse.service;

import com.ecommerse.model.Book;
import com.ecommerse.model.Tax;
import com.ecommerse.sales.SalesApplication;

import com.ecommerse.util.SalesConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(classes= SalesApplication.class)
public class SalesServiceTest {

    @Autowired
    ObjectMapper mapper;
    @Autowired
    SalesService salesService;
    @Test
    public void getDiscountPriceTest()
    {
        assertEquals(2250.0,salesService.getDiscountPrice(2500.00));
        assertEquals(1500.0,salesService.getDiscountPrice(1500.00));
        assertEquals(2800.0,salesService.getDiscountPrice(3500.00));
    }
    @Test
    public void addTaxDetailsTest()
    {
        Map<String,Double> taxMap = new HashMap();
        taxMap.put(SalesConstants.SALES_TAX,SalesConstants.SALES_TAX_PERCENTAGE);
        taxMap.put(SalesConstants.VAT_TAX,SalesConstants.VAT_TAX_PERCENTAGE);
        List<Tax> tax = salesService.addTaxDetails(taxMap,2000.0);
        assertEquals( SalesService.finalPrice,2360.0);
    }
    @Test
    public void getInvoice() throws JsonProcessingException {
        Book book = new Book();
        book.setPrice(2000);
        book.setImported(false);
        book.setCategory("customer");
        book.setName("computers");
       assertEquals("{\"quantity\":0,\"price\":2000.0,\"tax\":[{\"taxTyepe\":\"salesTax\",\"taxPrice\":240.0},{\"taxTyepe\":\"vatTax\",\"taxPrice\":120.0}],\"totalPrice\":2124.0,\"itemName\":\"computers\"}",mapper.writeValueAsString(salesService.getInvoice(book)));
    }
}
