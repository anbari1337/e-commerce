package com.aanbari.inventoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryEvent {
    private Integer orderId;
    private Map<String, Boolean> productAvailability;
}
