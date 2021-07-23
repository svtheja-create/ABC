package com.ecommerse.controller;


import com.ecommerse.model.Book;
import com.ecommerse.model.Invoice;
import com.ecommerse.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class SalesController {
    @Autowired
    SalesService salesService;

    @PostMapping("/addtocart")
    public void addToCart(@RequestBody List<Book> bookList)
    {
        salesService.addItemTOCart(bookList);
    }
    @GetMapping("/checkout")
    public List<Invoice> checkout()
    {
       return salesService.calculatePrice();
    }
}
