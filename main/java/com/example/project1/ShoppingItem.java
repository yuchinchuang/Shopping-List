package com.example.project1;

public class ShoppingItem {
    private long id;
    private String name;
    private int quantity;
    private int isPurchased;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getIsPurchased() {
        return isPurchased;
    }

    public void setIsPurchased(int isPurchased) {
        this.isPurchased = isPurchased;
    }

    public ShoppingItem(String name, int quantity)
    {
        this.name = name;
        this.quantity = quantity;
        this.isPurchased = 0;
    }

    @Override
    public String toString() {
        return name + " " + quantity ;
    }
}
