package com.heb.assessment.application.model.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class Cart implements Serializable {
    List<Item> items;
}
