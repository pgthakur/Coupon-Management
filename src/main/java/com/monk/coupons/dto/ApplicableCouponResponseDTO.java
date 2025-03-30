package com.monk.coupons.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class ApplicableCouponResponseDTO {
    private List<ApplicableCouponDTO> applicableCoupons;
}
