package com.ecommerse.controller;

import com.ecommerse.model.Book;
import com.ecommerse.sales.SalesApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes= SalesApplication.class)
@AutoConfigureMockMvc
public class SalesControllerTest {
    @Autowired
    ObjectMapper mapper;
    @Autowired
    private MockMvc mockMvc;
    @Test
    public void addToCartTest() throws Exception {
        List<Book> list = new ArrayList();
        Book book = new Book();
        book.setPrice(2000);
        book.setImported(false);
        book.setCategory("customer");
        book.setName("computers");
        list.add(book);
        mockMvc.perform(post("/addtocart")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(list))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        this.mockMvc.perform(get("/checkout")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("")));

    }

}
