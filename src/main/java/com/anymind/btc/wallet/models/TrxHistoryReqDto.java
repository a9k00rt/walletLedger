package com.anymind.btc.wallet.models;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TrxHistoryReqDto {
    @NotNull private String walletId;
    @NotNull @NotBlank private String startDateTime;
    @NotNull @NotBlank private String endDateTime;
}
