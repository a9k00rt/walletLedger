package com.anymind.btc.wallet.constants;

public class Endpoints {
    private Endpoints() {
    }

    public static final String BASE_ENDPOINT = "/api/v1/wallet";
    public static final String CREATE_BTC_TRANSACTION = BASE_ENDPOINT + "/transaction";
    public static final String HISTORY_BTC_TRANSACTION = BASE_ENDPOINT + "/history";
}
