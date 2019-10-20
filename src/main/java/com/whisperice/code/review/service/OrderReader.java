package com.whisperice.code.review.service;

import com.whisperice.code.review.entity.Order;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderReader {
    private static final int ORDER_NUMBER_INDEX = 0;
    private static final int ORDER_FORMAT_CODE_INDEX = 1;
    private static final String EMPTY_STRING = "";

    private final List<Order> orders = new ArrayList<>();

    public List<Order> getOrders() {
        return Collections.unmodifiableList(orders);
    }

    public void load(String fileName) throws Exception {
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                addOrderList(line);
            }
        } catch (NullPointerException | IOException e) {
            System.out.println("Load MediaBundle File Failed.");
            e.printStackTrace();
            throw new Exception("Load MediaBundle File Failed.", e);
        }
    }

    private void addOrderList(String line) {
        String[] strings = StringUtils.split(StringUtils.trimToEmpty(line.toUpperCase()));

        if (!StringUtils.equals(EMPTY_STRING, strings[ORDER_NUMBER_INDEX])) {
            Order order = new Order(Integer.parseInt(strings[ORDER_NUMBER_INDEX]), strings[ORDER_FORMAT_CODE_INDEX]);
            orders.add(order);
        }
    }
}
