package com.whisperice.code.review.service.impl;

import com.whisperice.code.review.entity.ProcessedOrder;
import com.whisperice.code.review.processor.OrderProcessor;
import com.whisperice.code.review.service.MediaBundleReader;
import com.whisperice.code.review.service.OrderReader;
import com.whisperice.code.review.service.ResultWriterService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class ResultWriterServiceImplToScreenTest {
    private static OrderReader orderReader;
    private static ResultWriterService resultWriter;

    @BeforeClass
    public static void beforeClass() throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-context.xml");
        orderReader = (OrderReader) applicationContext.getBean("orderReader");
        resultWriter = (ResultWriterService) applicationContext.getBean("resultWriterService");

        MediaBundleReader.load("MediaBundles.txt");
        orderReader.load("Orders.txt");
    }

    @Test
    public void testWriterToScreen() {
        List<ProcessedOrder> processedOrder = OrderProcessor.process(orderReader.getOrders());
        resultWriter.write(processedOrder);
        assertEquals(800.0, processedOrder.get(0).getTotalCost());
        assertEquals(1957.5, processedOrder.get(1).getTotalCost());
        assertEquals(2370.0, processedOrder.get(2).getTotalCost());
        assertEquals(2040.0, processedOrder.get(3).getTotalCost());
        assertEquals(2940.0, processedOrder.get(4).getTotalCost());
    }
}
