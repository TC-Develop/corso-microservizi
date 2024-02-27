package it.tcgroup.transactiondemo.controller.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class CartResponse {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductInfo{
        private UUID productId;
        private Integer quantity;
    }

    private Long seq;
    private String username;
    private List<ProductInfo> productInfos = new ArrayList<>();

}
