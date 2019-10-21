package com.whisperice.code.review.processor;

import com.whisperice.code.review.entity.Bundle;
import com.whisperice.code.review.entity.Media;
import com.whisperice.code.review.entity.Order;
import com.whisperice.code.review.entity.ProcessedOrder;
import com.whisperice.code.review.service.MediaBundleReader;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class OrderProcessor {
    private static final int BUNDLES_START_INDRX = 0;

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

    private static ProcessedOrder findOptimizedBundles(Order order, Media media) {

        // bundleList is already in the increasing order from lowest average price to highest average price
        List<Bundle> bundles = media.getBundles();
        int orderLeftSize = order.getSize();
        ProcessedOrder processedOrder = new ProcessedOrder(order.getSize(), order.getFormatCode());
        Deque<Bundle> bundleStack = new ArrayDeque<>();

        orderLeftSize = getOrderLeftSize(bundles, BUNDLES_START_INDRX, orderLeftSize, bundleStack);

        completeSingleOrder(bundles, orderLeftSize, bundleStack);

        buildProcessOrder(processedOrder, bundleStack);
        return processedOrder;
    }

    private static void buildProcessOrder(ProcessedOrder processedOrder, Deque<Bundle> bundleStack) {
        Bundle lastBundle = bundleStack.pollFirst();
        int numberOfBundle = 0;
        while (lastBundle != null) {
            numberOfBundle++;
            if (!lastBundle.equals(bundleStack.peekFirst())) {
                processedOrder.addConsumedBundle(lastBundle, numberOfBundle);
                numberOfBundle = 0;
            }
            lastBundle = bundleStack.pollFirst();
        }
    }

    private static void completeSingleOrder(List<Bundle> bundles, int orderLeftSize, Deque<Bundle> bundleStack) {
        Bundle lastBundle;
        while (orderLeftSize != 0) {
            lastBundle = bundleStack.pollLast();
            if (lastBundle != null) {
                orderLeftSize += lastBundle.getSize();
                orderLeftSize = getOrderLeftSize(bundles, bundles.indexOf(lastBundle) + 1, orderLeftSize, bundleStack);
            }
        }
    }

    private static int getOrderLeftSize(List<Bundle> bundles, int startIndex, int orderLeftSize,
                                        Deque<Bundle> bundleStack) {
        for (int i = startIndex; i < bundles.size(); i++) {

            if (orderLeftSize == 0) {
                break;
            }

            Bundle bundle = bundles.get(i);
            int bundleSize = bundle.getSize();
            int numberOfBundle = orderLeftSize / bundleSize;
            orderLeftSize = orderLeftSize % bundleSize;
            for (int j = 0; j < numberOfBundle; j++) {
                bundleStack.offerLast(bundle);
            }
        }
        return orderLeftSize;
    }
}
