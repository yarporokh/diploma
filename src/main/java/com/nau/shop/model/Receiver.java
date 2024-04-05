package com.nau.shop.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Receiver {
    private String receiverFirstname;
    private String receiverLastname;
    private String receiverPhone;
    private String receiverAddress;
}
