package com.delivery.product.bootstrap;

import com.delivery.product.domain.Product;
import com.delivery.product.domain.Tag;
import com.delivery.product.repositories.ProductRepository;
import com.delivery.product.repositories.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class ProductLoader implements CommandLineRunner {

    public static final String STORE_01_UUID = "e8fe3afb-584a-41b0-8676-1df2900a401a";
    public static final String STORE_02_UUID = "cd0116ae-fd38-4425-a785-79b61617788a";
    public static final String STORE_03_UUID = "4b47c7ec-71fe-470c-b22c-2d25d1aba10f";
    public static final String STORE_04_UUID = "94bb887e-1c55-490a-9a4a-e7a5f9ab7897";
    public static final String STORE_05_UUID = "4a0ef1fd-60dc-4972-9676-e0cf4f468f7b";
    public static final String STORE_06_UUID = "74faac98-a946-4440-87fe-235dcb28db91";
    public static final String STORE_07_UUID = "fc39c120-ba30-406a-b8e3-d0c7e5cdcdd3";
    public static final String STORE_08_UUID = "e9dfd10d-ff54-45dc-aed5-970bf0dda1b5";
    public static final String STORE_09_UUID = "ff5b0ca1-27ba-47b7-b053-2c918ff273e1";
    public static final String STORE_10_UUID = "e9ea4443-0a5f-4399-bf97-3ddb9b0783cb";


    public static final String[] STORES_IDS_ARRAY = {
            STORE_01_UUID ,
            STORE_02_UUID ,
            STORE_03_UUID ,
            STORE_04_UUID ,
            STORE_05_UUID ,
            STORE_06_UUID ,
            STORE_07_UUID ,
            STORE_08_UUID ,
            STORE_09_UUID ,
            STORE_10_UUID
    };

    public static final String[] PRODUCT_NAMES_ARRAY = {
            "Apple,178.11,[technology;smartphone;computer;pc;tablet]",
            "Google,133.25,[technology;search]",
            "CocaCola,73.10,[drink;soda]",
            "Microsoft,72.79,[technology;computer;pc;software]",
            "Toyota,53.58,[car]",
            "IBM,52.50,[pc;technology]",
            "Samsung,51.80,[smartphone;software]",
            "Amazon,50.33,[technology]",
            "MercedesBenz,43.49,[car]",
            "GE,43.13,[industial;car]",
            "BMW,41.53,[car]",
            "McDonalds,39.38,[food]",
            "Disney,38.79,[entertainment]",
            "Intel,36.95,[technology]",
            "Facebook,32.59,[technology;social;software]",
            "Cisco,30.94,[communication]",
            "Oracle,26.55,[software;technology]",
            "Nike,25.03,[shoes]",
            "Louis Vuitton,23.99,[fashion]",
            "H&M,22.68,[fashion]",
            "Honda,22.10,[motocicle;car]",
            "SAP,21.29,[software]",
            "Pepsi,20.26,[drink]",
            "Gillette,19.95,[clean]",
            "American Express,18.35,[post]",
            "IKEA,17.83,[market]",
            "Zara,16.76,[fashion]",
            "Pampers,16.13,[clean]",
            "UPS,15.33",
            "Budweiser,15.09,[drink]",
            "J.P. Morgan,14.22,[financial]",
            "eBay,13.13,[market]",
            "Ford,12.96,[car]",
            "Hermes,12.83,[food]",
            "Hyundai,12.54,[car]",
            "Nescafe,12.51,[drink]",
            "Accenture,12.03,[software]",
            "Audi,11.79,[car]",
            "Kelloggs,11.71",
            "Volkswagen,11.43,[car]",
            "Philips,11.33,[tv,technology]",
            "Canon,11.08,[camera]",
            "Nissan,11.06,[car]",
            "Hewlett Packard Enterprise,11.02,[pc]",
            "LOreal,10.93,[shampoo]",
            "AXA,10.57",
            "HSBC,10.45,[financial]",
            "HP,10.38,[computer]",
            "Citi,10.27,[financial]",
            "Porsche,9.53,[car]",
            "Allianz,9.52,[airplane]",
            "Siemens,9.41,[communication]",
            "Gucci,9.38,[fashion]",
            "Goldman Sachs,9.37,[financial]",
            "Danone,9.19,[drink;food]",
            "Nestle,8.70,[drink;food]",
            "Colgate,8.41,[medical]",
            "Sony,8.31,[technology,game]",
            "3M,8.19,[industrial]",
            "adidas,7.88,[shoes]",
            "Visa,7.74,[financial]",
            "Cartier,7.73",
            "Adobe,7.58,[software]",
            "Starbucks,7.49,[drink]",
            "Morgan Stanley,7.20,[financial]",
            "Thomson Reuters,6.83,[financial]",
            "Lego,6.69,[toys]",
            "Panasonic,6.36,[tv;electronic;tecnhology]",
            "Kia,6.32,[car]",
            "Santander,6.22,[financial]",
            "Discovery,5.94,[tv;show]",
            "Huawei,5.83,[smartphone]",
            "Johnson & Johnson,5.79,[medical]",
            "Tiffany & Co.,5.76,[fashion]",
            "KFC,5.74,[food]",
            "MasterCard,5.73,[financial;payment]",
            "DHL,5.70",
            "Land Rover,5.69,[car]",
            "FedEx,5.57,[post]",
            "HarleyDavidson,5.52,[motocicle]",
            "Prada,5.50,[fashion]",
            "Caterpillar,5.42",
            "Burberry,5.36,[smartphone]",
            "Xerox,5.29,[camera]",
            "Jack Daniels,5.19,[drink]",
            "Sprite,5.14",
            "Heineken,5.12,[drink]",
            "Mini,4.98",
            "Dior,4.90,[fashion]",
            "PayPal,4.83,[financial;payment]",
            "John Deere,4.81,[industrial]",
            "Shell,4.59,[fuel]",
            "Corona,4.50,[drink]",
            "MTV,4.32,[tv;show]",
            "Johnnie Walker,4.31,[drink]",
            "Smirnoff,4.25,[drink]",
            "Moet & Chandon,4.11,[drink]",
            "Ralph Lauren,4.09,[fashion]",
            "Lenovo,4.04,[pc;computer]",
            "Tesla,4.01,[technology;space]"
    };

    private final ProductRepository productRepository;

    private final TagRepository tagRepository;

    @Override
    public void run(String... args) throws Exception {

          if(productRepository.count() == 0 ) {
              loadProductObjects();
          }
    }

    private void loadProductObjects() {
        List<Product> productList = new ArrayList<>();
        List<Tag> tagList = new ArrayList<>();

        for(int i = 0; i < STORES_IDS_ARRAY.length; i++){
            String storeId = STORES_IDS_ARRAY[i];
            for(int j = 0; j < 100; j++) {
                String product = PRODUCT_NAMES_ARRAY[new Random().nextInt(PRODUCT_NAMES_ARRAY.length)];
                String[] productTemplate = product.split(",");
                String name = productTemplate[0];
                String price = productTemplate[1];

                Product product1 = Product.builder()
                        .name(name)
                        .storeId(UUID.fromString(storeId))
                        .price(new BigDecimal(price))
                        .upc(UUID.randomUUID().toString())
                        .build();

                if(productTemplate.length > 2 ){
                    String[] tags = productTemplate[2]
                            .replace("[","")
                            .replace("]","")
                            .split(";");
                    for(int k = 0; k < tags.length; k++){
                        if(tags[k] != null && !tags[k].isBlank()) {
                            tagList.add(Tag.builder()
                                    .value(tags[k])
                                    .product(product1)
                                    .build());
                        }
                    }
                }

                productList.add(product1);
            }
        }
        productRepository.saveAll(productList);
        tagRepository.saveAll(tagList);
    }
}
