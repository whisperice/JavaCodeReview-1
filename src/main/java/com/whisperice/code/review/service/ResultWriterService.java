package com.whisperice.code.review.service;

import com.whisperice.code.review.entity.ProcessedOrder;

import java.util.List;

public interface ResultWriterService {
    void write(List<ProcessedOrder> processedOrders);
}
