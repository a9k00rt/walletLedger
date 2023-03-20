package com.anymind.btc.wallet.models;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BTCTrxReqDto extends BTCWalletAccountDto {
    @NotNull @NotBlank String dateTime;
    @NotNull Double amount;
}
