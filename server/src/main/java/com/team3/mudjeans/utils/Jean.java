package com.team3.mudjeans.utils;

public class Jean {
    public String productCode;
    public String description;
    public String size;;
    public int total;
    public int currentStock;
    public int lastThreeMonths;

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Jean{" +
                "productCode='" + productCode + '\'' +
                ", description='" + description + '\'' +
                ", size='" + size + '\'' +
                ", total=" + total +
                ", currentStock=" + currentStock +
                ", lastThreeMonths=" +  lastThreeMonths +
                "}\n";
    }
}
