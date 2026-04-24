package com.restaurant;
public class OrderItem {
    private final MenuItem item;
    private int quantity;

    public OrderItem(MenuItem item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public MenuItem getItem() { return item; }
    public int getQuantity() { return quantity; }
    public void addQuantity(int n) { this.quantity += n; }
    public double lineTotal() { return item.getPrice() * quantity; }
}
