package com.ecommerse.service;

import com.ecommerse.model.Book;
import com.ecommerse.model.Invoice;
import com.ecommerse.model.Tax;
import com.ecommerse.util.SalesConstants;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SalesService {
    Invoice invoice = new Invoice();
    List<Book> bookList  = new ArrayList();

private static Map<Double,Double> discountMap = new TreeMap();
    private static Map<String,Double> taxMap = new TreeMap();
    private static double finalPrice=0.0;
static{
    discountMap.put(1500.0,5.0);
    discountMap.put(2000.0,10.0);
    discountMap.put(2500.0,15.0);
    discountMap.put(3000.0,20.0);
    taxMap.put(SalesConstants.SALES_TAX,SalesConstants.SALES_TAX_PERCENTAGE);
    taxMap.put(SalesConstants.VAT_TAX,SalesConstants.VAT_TAX_PERCENTAGE);

}

public void addItemTOCart(List<Book> list)
{
    bookList=list;
}

    public List<Invoice> calculatePrice()
    {
        List<Invoice> invoiceList = new ArrayList();
        for(Book book:bookList)
        {

             Invoice invoice  =   getInvoice(book);

            invoiceList.add(invoice);
        }
       return invoiceList;
    }

    public void addDiscounts(double discountprice,double discountpercent)
    {
        discountMap.put(discountprice,discountpercent);
    }
    public double getDiscountPrice(Double price)
    {

        Double finalPrice=price;
       for(Map.Entry<Double,Double> map:discountMap.entrySet())
       {
           if(price>map.getKey())
           {
              finalPrice =   ((100-map.getValue())*price)/100;
           }
           else
           {
               break;
           }
       }
       return finalPrice;
    }
    public Invoice getInvoice(Book book)
    {
        if(book.isImported())
        {
            taxMap.put(SalesConstants.ADDITIONAL_TAX,SalesConstants.ADDITIONAL_TAX_PERCENTAGE);
        }
        invoice.setPrice(book.getPrice());
        invoice.setItemName(book.getName());
        invoice.setTax(addtaxDetails(taxMap,book.getPrice()));
        if(!book.getCategory().equalsIgnoreCase("management")) {
            invoice.setTotalPrice(getDiscountPrice(finalPrice));
        }
        else
        {
            invoice.setTotalPrice(finalPrice);
        }
        return invoice;
    }

   public List<Tax> addtaxDetails(Map<String,Double> map,Double price)
    {
        List<Tax> taxList = new ArrayList();
        for(Map.Entry<String,Double> entry:map.entrySet())
        {
           double taxPrice =  calculateTaxPrice(entry.getValue(),price);
            finalPrice = finalPrice+taxPrice;
            taxList.add(new Tax(entry.getKey(),taxPrice));
        }
        return taxList;
    }
    private Double calculateTaxPrice(Double taxPrice,Double ItemPrice)
    {

       return (ItemPrice*taxPrice)/100;
    }
}
