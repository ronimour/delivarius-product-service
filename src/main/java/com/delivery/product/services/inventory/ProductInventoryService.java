package com.delivery.product.services.inventory;

import java.util.UUID;

/**
 * Created by jt on 2019-06-07.
 */
public interface ProductInventoryService {

    Integer getOnHandInventory(UUID productId);
}
