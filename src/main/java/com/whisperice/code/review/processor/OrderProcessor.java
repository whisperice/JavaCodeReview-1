package com.whisperice.code.review.processor;

import com.whisperice.code.review.entity.Bundle;
import com.whisperice.code.review.entity.Media;
import com.whisperice.code.review.entity.Order;
import com.whisperice.code.review.entity.ProcessedOrder;
import com.whisperice.code.review.service.MediaBundleReader;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class OrderProcessor {
    public static List<ProcessedOrder> process(List<Order> orders) {
        if (CollectionUtils.isEmpty(orders)) {
            return new ArrayList<>();
        }

        List<ProcessedOrder> processedOrders = new ArrayList<>();
        List<Media> mediaList = MediaBundleReader.getMediaList();

        for (Order order : orders) {
            for (Media media : mediaList) {
                if (StringUtils.equals(order.getFormatCode(), media.getFormatCode())) {
                    ProcessedOrder processedOrder = findOptimizedBundles(order, media);
                    processedOrders.add(processedOrder);
                }
            }
        }
        return processedOrders;
    }

    // todo: Bugs exist. May not finish the whole order. Will be fixed later.
    private static ProcessedOrder findOptimizedBundles(Order order, Media media) {
        // bundleList is already in the increasing order from lowest average price to highest average price
        List<Bundle> bundles = media.getBundles();
        int orderLeftSize = order.getSize();
        ProcessedOrder processedOrder = new ProcessedOrder(order.getSize(), order.getFormatCode());
        for (Bundle bundle : bundles) {

            if (orderLeftSize == 0) {
                break;
            }

            int bundleSize = bundle.getSize();
            int numberOfBundle = orderLeftSize / bundleSize;
            orderLeftSize = orderLeftSize % bundleSize;
            if (numberOfBundle != 0) {
                processedOrder.addConsumedBundle(bundle, numberOfBundle);
            }
        }
        return processedOrder;
    }
}
