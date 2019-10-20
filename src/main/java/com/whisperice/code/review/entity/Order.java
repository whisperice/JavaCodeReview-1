package com.whisperice.code.review.entity;

import java.util.Objects;

public class Order {
    private int size;
    private String formatCode;

    public Order(int size, String formatCode) {
        this.size = size;
        this.formatCode = formatCode;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getFormatCode() {
        return formatCode;
    }

    public void setFormatCode(String formatCode) {
        this.formatCode = formatCode;
    }

    @Override public String toString() {
        return "Order{" +
               "size=" + size +
               ", formatCode='" + formatCode + '\'' +
               '}';
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        return size == order.size &&
               Objects.equals(formatCode, order.formatCode);
    }

    @Override public int hashCode() {
        return Objects.hash(size, formatCode);
    }
}
