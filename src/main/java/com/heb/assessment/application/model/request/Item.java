package com.heb.assessment.application.model.request;

import lombok.Data;

import java.io.Serializable;
@Data
public class Item implements Serializable {
    String itemName;
    long sku;
    Boolean isTaxable;
    Boolean ownBrand;
    Double price;
}
