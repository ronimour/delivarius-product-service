package com.delivery.product.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageDto {
    private String imageLarge;
    private String imageMedium;
    private String imageSmall;
}
