package com.whisperice.code.review.processor;

import com.whisperice.code.review.entity.ProcessedOrder;
import com.whisperice.code.review.service.MediaBundleReader;
import com.whisperice.code.review.service.OrderReader;
import com.whisperice.code.review.service.ResultWriterService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class OrderProcessorTest {
    private static OrderReader orderReader;

    @BeforeClass
    public static void beforeClass() throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-context.xml");
        orderReader = (OrderReader) applicationContext.getBean("orderReader");

        MediaBundleReader.load("MediaBundles.txt");
        orderReader.load("Orders.txt");
    }

    @Test
    public void testProcessOrder() {
        List<ProcessedOrder> processedOrders = OrderProcessor.process(orderReader.getOrders());
        System.out.println(processedOrders);
    }
}
