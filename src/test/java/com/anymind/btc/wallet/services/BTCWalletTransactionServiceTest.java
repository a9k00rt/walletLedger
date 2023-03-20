package com.anymind.btc.wallet.services;

import com.anymind.btc.wallet.services.BTCWalletTransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import com.anymind.btc.wallet.common.BaseAPIResponse;
import com.anymind.btc.wallet.interfaces.WalletTransactionInterface;
import com.anymind.btc.wallet.models.BTCTrxReqDto;
import com.anymind.btc.wallet.models.TrxHistoryReqDto;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@SuppressWarnings("unchecked")
class BTCWalletTransactionServiceTest {

    private WalletTransactionInterface walletTransactionInterface;

    @BeforeEach
    void setUp() {
        walletTransactionInterface = new BTCWalletTransactionService();
    }

    @Test
    void createWallet() {
        BaseAPIResponse response = walletTransactionInterface.createWallet();
        assertEquals(HttpStatus.CREATED.name(), response.getStatus());
        assertNotNull(response.getMessage());

    }

    @Test
    void createTransaction() throws Exception {
        BaseAPIResponse response = walletTransactionInterface.createWallet();

        BTCTrxReqDto trxReq = BTCTrxReqDto.builder()
                .dateTime("2019-10-05T13:00:00+00:00")
                .amount(1000.0).build();
        trxReq.setWalletId(response.getMessage());

        BaseAPIResponse trxRes1 = walletTransactionInterface.createTransaction(trxReq);

        trxReq.setDateTime("2019-10-05T14:00:00+00:00");
        trxReq.setAmount(1001.0);
        BaseAPIResponse trxRes2 = walletTransactionInterface.createTransaction(trxReq);

        trxReq.setDateTime("2019-10-05T15:00:00+00:30");
        trxReq.setAmount(1003.0);
        BaseAPIResponse trxRes3 = walletTransactionInterface.createTransaction(trxReq);


        trxReq.setDateTime("2019-10-05T16:00:00+00:30");
        trxReq.setAmount(1004.0);
        BaseAPIResponse trxRes4 = walletTransactionInterface.createTransaction(trxReq);

        System.out.println("\ntrxResponse : \n1:" + trxRes1 + "\n2: " + trxRes2 + "\n3: " + trxRes3 + "\n4: " + trxRes4);

        assertEquals(trxRes1.getStatus(), HttpStatus.CREATED.name());
        assertEquals(trxRes2.getStatus(), HttpStatus.CREATED.name());
        assertEquals(trxRes3.getStatus(), HttpStatus.CREATED.name());
        assertEquals(trxRes4.getStatus(), HttpStatus.CREATED.name());

        assertEquals(trxRes1.getMessage(), "transaction successful");
        assertEquals(trxRes2.getMessage(), "transaction successful");
        assertEquals(trxRes3.getMessage(), "transaction successful");
        assertEquals(trxRes4.getMessage(), "transaction successful");

    }

    @Test
    void getWalletTransactionHistories() throws Exception {

        BaseAPIResponse response = walletTransactionInterface.createWallet();

        BTCTrxReqDto trxReq = BTCTrxReqDto.builder()
                .dateTime("2019-10-05T13:00:00+00:00")
                .amount(1000.0).build();
        trxReq.setWalletId(response.getMessage());

        BaseAPIResponse trxRes1 = walletTransactionInterface.createTransaction(trxReq);

        trxReq.setDateTime("2019-10-05T14:00:00+00:00");
        trxReq.setAmount(1001.0);
        BaseAPIResponse trxRes2 = walletTransactionInterface.createTransaction(trxReq);

        trxReq.setDateTime("2019-10-05T15:00:00+00:30");
        trxReq.setAmount(1003.0);
        BaseAPIResponse trxRes3 = walletTransactionInterface.createTransaction(trxReq);


        trxReq.setDateTime("2019-10-05T16:00:00+00:30");
        trxReq.setAmount(1004.0);
        BaseAPIResponse trxRes4 = walletTransactionInterface.createTransaction(trxReq);


        TrxHistoryReqDto trxHistoryReqDto = TrxHistoryReqDto.builder().walletId(response.getMessage())
                .startDateTime("2019-10-05T13:00:00+00:00").endDateTime("2019-10-05T17:00:00+00:00").build();

        List<BTCTrxReqDto> history = walletTransactionInterface.getWalletTransactionHistories(trxHistoryReqDto);

        assertFalse(history.isEmpty());

        System.out.println("\nhistory : ");

        int i = 1;
        for (BTCTrxReqDto his : history) {
            System.out.println("\nhistory :" + i++ + " " + his.getWalletId() + " date: " + his.getDateTime() + " amount: " + his.getAmount());
        }
    }

}