package com.anymind.btc.wallet.persistence;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

public class WalletPersistence {
    public static Map<String, TreeMap<Long, Double>> WalletTransactionMap = new ConcurrentHashMap<>();

    private WalletPersistence() {
    }

}
