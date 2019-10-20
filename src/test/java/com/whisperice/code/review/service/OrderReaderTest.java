package com.whisperice.code.review.service;

import com.whisperice.code.review.entity.Order;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class OrderReaderTest {
    private static OrderReader orderReader;

    @BeforeClass
    public static void beforeClass() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-context.xml");
        orderReader = (OrderReader) applicationContext.getBean("orderReader");
    }

    @Test
    public void testOrderFileRead() throws Exception {
        orderReader.load("Orders.txt");
        List<Order> orders = orderReader.getOrders();
        orders.forEach(System.out::println);
        assertEquals(new Order(10, "IMG"), orders.get(0));
        assertEquals(new Order(15, "FLAC"), orders.get(1));
        assertEquals(new Order(13, "VID"), orders.get(2));
    }
}
