package com.edanuryildirim.applant.Activities.Model.Model;

public class Order {

    private String ProductId;
    private String ProductName;
    private String Qunatity;
    private String Price;
    private String Discount;

    public Order(){

    }

    public Order(String productId, String productName, String qunatity, String price, String discount) {
        ProductId = productId;
        ProductName = productName;
        Qunatity = qunatity;
        Price = price;
        Discount = discount;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getQunatity() {
        return Qunatity;
    }

    public void setQunatity(String qunatity) {
        Qunatity = qunatity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }
}
