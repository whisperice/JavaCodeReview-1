package com.whisperice.code.review.entity;

import java.util.Objects;

public class Bundle {
    private int size;
    private double price;

    public Bundle(int size, double price) {
        this.size = size;
        this.price = price;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getAveragePrice() {
        return this.price / this.size;
    }

    @Override
    public String toString() {
        return "Bundle{" +
               "size=" + size +
               ", price=" + price +
               '}';
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Bundle bundle = (Bundle) o;
        return size == bundle.size &&
               Double.compare(bundle.price, price) == 0;
    }

    @Override public int hashCode() {
        return Objects.hash(size, price);
    }
}
