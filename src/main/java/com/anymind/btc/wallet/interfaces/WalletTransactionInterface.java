package com.anymind.btc.wallet.interfaces;


import com.anymind.btc.wallet.models.BTCTrxReqDto;
import com.anymind.btc.wallet.common.BaseAPIResponse;
import com.anymind.btc.wallet.models.TrxHistoryReqDto;

import java.util.List;
import java.util.Set;

public interface WalletTransactionInterface {
    BaseAPIResponse createTransaction(BTCTrxReqDto trxReqDto) throws Exception;
    List<BTCTrxReqDto> getWalletTransactionHistories(TrxHistoryReqDto trxHistoryReqDto) throws Exception;

    BaseAPIResponse createWallet();

}
