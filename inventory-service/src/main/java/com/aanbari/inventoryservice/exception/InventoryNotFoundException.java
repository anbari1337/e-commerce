package com.aanbari.inventoryservice.exception;

public class InventoryNotFoundException extends RuntimeException{
    public InventoryNotFoundException(){
        super("Inventory not found");
    }
}
