    package com.heb.assessment.application.model.response;

    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.io.Serializable;
    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class ReceiptDto implements Serializable {
            Double grandTotal;
            Double taxTotal;
            Double taxableSubTotalAfterDiscount;
            Double subTotalAfterDiscount;
            Double discountTotal;
            Double subTotalBeforeDiscount;
    }
