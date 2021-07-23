package com.ecommerse.model;

public class Tax {
    private String taxTyepe;
    private double taxPrice;

    public Tax(String taxTyepe, double taxPrice) {
        this.taxTyepe = taxTyepe;
        this.taxPrice = taxPrice;
    }

    public String getTaxTyepe() {
        return taxTyepe;
    }

    public void setTaxTyepe(String taxTyepe) {
        this.taxTyepe = taxTyepe;
    }

    public double getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(double taxPrice) {
        this.taxPrice = taxPrice;
    }
}
