package com.anymind.btc.wallet.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseAPIResponse {
    protected String status;
    protected String message;
}