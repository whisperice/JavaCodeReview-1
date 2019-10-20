package com.whisperice.code.review.service.impl;

import com.whisperice.code.review.processor.OrderProcessor;
import com.whisperice.code.review.service.MediaBundleReader;
import com.whisperice.code.review.service.OrderReader;
import com.whisperice.code.review.service.ResultWriterService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ResultWriterServiceImplToScreenTest {
    private static OrderReader orderReader;
    private static ResultWriterServiceImplToScreen resultWriter;

    @BeforeClass
    public static void beforeClass() throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-context.xml");
        orderReader = (OrderReader) applicationContext.getBean("orderReader");
        resultWriter = (ResultWriterServiceImplToScreen) applicationContext.getBean("resultWriterService");

        MediaBundleReader.load("MediaBundles.txt");
        orderReader.load("Orders.txt");
    }

    @Test
    public void testWriterToScreen() {
        resultWriter.write(OrderProcessor.process(orderReader.getOrders()));
    }
}
