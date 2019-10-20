package com.whisperice.code.review.entity;

import java.util.*;

public class ProcessedOrder extends Order{
    private Map<Bundle, Integer> consumedBundles = new LinkedHashMap<>();

    public ProcessedOrder(int size, String formatCode) {
        super(size, formatCode);
    }

    public double getTotalCost() {
        final double[] totalCost = {0.0};
        consumedBundles.forEach((bundle, integer) -> totalCost[0] += bundle.getPrice() * integer);
        return totalCost[0];
    }

    public Map<Bundle, Integer> getConsumedBundles() {
        return Collections.unmodifiableMap(consumedBundles);
    }

    public void addConsumedBundle(Bundle bundle, int numberOfBundle) {
        if (consumedBundles == null) {
            consumedBundles = new LinkedHashMap<>();
        }

        consumedBundles.put(bundle, numberOfBundle);
    }

    @Override public String toString() {
        return "ProcessedOrder{" +
               "consumedBundles:" + consumedBundles +
               '}';
    }
}
