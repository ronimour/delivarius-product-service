package com.delivery.product.services.inventory;

import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class ProductInventoryServiceImpl implements ProductInventoryService {

    public Integer getOnhandInventory(UUID productId){
        return 0;
    }
}
