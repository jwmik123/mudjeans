package com.team3.mudjeans.entity;

import javax.persistence.*;


@Entity
@NamedQuery(name="find_all_orders", query="select j from JeanOrder j")
public class JeanOrder implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderNumber;
    private String name;
    private int quantity;

    private String productCode;
    private String description;
    private String fabric;
    private String size;
    private int orderFix;

    public JeanOrder() {}

    public JeanOrder(long orderNumber, String name, int quantity, String productCode, String description, String fabric, String size, int orderFix) {

        this.orderNumber = orderNumber;
        this.name = name;
        this.quantity = quantity;
        this.productCode = productCode;
        this.description = description;
        this.fabric = fabric;
        this.size = size;
        this.orderFix = orderFix;
    }



    public long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(long orderNumber) {
        this.orderNumber = orderNumber;
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

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFabric() {
        return fabric;
    }

    public void setFabric(String fabric) {
        this.fabric = fabric;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getOrderFix() {
        return orderFix;
    }

    public void setOrderFix(int orderFix) {
        this.orderFix = orderFix;
    }

    @Override
    public long getId() {
        return orderNumber;
    }

    @Override
    public void setId(long id) {
        this.orderNumber = id;
    }
}
