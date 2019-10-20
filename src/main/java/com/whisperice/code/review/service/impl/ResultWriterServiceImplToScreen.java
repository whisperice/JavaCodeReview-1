package com.whisperice.code.review.service.impl;

import com.whisperice.code.review.entity.Bundle;
import com.whisperice.code.review.entity.ProcessedOrder;
import com.whisperice.code.review.service.ResultWriterService;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Map;

public class ResultWriterServiceImplToScreen implements ResultWriterService {
    private static final String LINE_BREAK = System.getProperty("line.separator");

    @Override
    public void write(List<ProcessedOrder> processedOrders) {
        if (CollectionUtils.isEmpty(processedOrders)) {
            return;
        }

        System.out.println("The Result is:" + LINE_BREAK);
        for (ProcessedOrder processedOrder : processedOrders) {
            System.out.printf("%d %s $%.2f" + LINE_BREAK, processedOrder.getSize(), processedOrder.getFormatCode(),
                              processedOrder.getTotalCost());

            Map<Bundle, Integer> consumedBundles = processedOrder.getConsumedBundles();
            for (Bundle bundle : consumedBundles.keySet()) {
                System.out.printf("\t%d x %s $%.2f" + LINE_BREAK, consumedBundles.get(bundle), bundle.getSize(),
                                  bundle.getPrice());
            }
        }
    }
}
