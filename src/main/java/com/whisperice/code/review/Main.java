package com.whisperice.code.review;

import com.whisperice.code.review.entity.ProcessedOrder;
import com.whisperice.code.review.processor.OrderProcessor;
import com.whisperice.code.review.service.MediaBundleReader;
import com.whisperice.code.review.service.OrderReader;
import com.whisperice.code.review.service.ResultWriterService;
import com.whisperice.code.review.service.impl.ResultWriterServiceImplToScreen;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Application Start.");
        try {
            ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-context.xml");
            OrderReader orderReader = (OrderReader) applicationContext.getBean("orderReader");
            ResultWriterService resultWriter = (ResultWriterServiceImplToScreen) applicationContext.getBean(
                    "resultWriterService");

            MediaBundleReader.load("MediaBundles.txt");
            orderReader.load("Orders.txt");

            List<ProcessedOrder> processedOrder = OrderProcessor.process(orderReader.getOrders());
            resultWriter.write(processedOrder);

        } catch (Exception e) {
            System.out.println("Something Wrong Happened.");
            e.printStackTrace();
        } finally {
            System.out.println(System.getProperty("line.separator") + "Application End.");
        }
    }
}
