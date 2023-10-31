package com.team3.mudjeans.entity;

import org.aspectj.lang.annotation.Before;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "select_all_jeans", query = "select j from Jean j")
})
public class Jean implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String productCode;
    private String description;
    private String size;
    private int total;
    private int currentStock;
    private int lastThreeMonths;


    public Jean(long id, String productCode, String description, String size, int total, int currentStock, int lastThreeMonths) {
        this.id = id;
        this.productCode = productCode;
        this.description = description;
        this.size = size;
        this.total = total;
        this.currentStock = currentStock;
        this.lastThreeMonths = lastThreeMonths;
    }

    public Jean(String productCode, String description, String size, int total, int currentStock, int lastThreeMonths) {
        this.productCode = productCode;
        this.description = description;
        this.size = size;
        this.total = total;
        this.currentStock = currentStock;
        this.lastThreeMonths = lastThreeMonths;
    }

    public Jean(long id, String productCode) {
        this.id = id;
        this.productCode = productCode;
    }

    public Jean() {
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(int currentStock) {
        this.currentStock = currentStock;
    }

    public int getLastThreeMonths() {
        return lastThreeMonths;
    }

    public void setLastThreeMonths(int lastThreeMonths) {
        this.lastThreeMonths = lastThreeMonths;
    }

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public void setId(long id) {

    }
}
