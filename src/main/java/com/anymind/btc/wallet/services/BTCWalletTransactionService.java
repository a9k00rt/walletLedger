package com.anymind.btc.wallet.services;

import com.anymind.btc.wallet.common.BaseAPIResponse;
import com.anymind.btc.wallet.interfaces.WalletTransactionInterface;
import com.anymind.btc.wallet.models.BTCTrxReqDto;
import com.anymind.btc.wallet.models.TrxHistoryReqDto;
import com.anymind.btc.wallet.persistence.WalletPersistence;
import com.anymind.btc.wallet.utils.DateTimeUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BTCWalletTransactionService implements WalletTransactionInterface {

    @Override
    public BaseAPIResponse createTransaction(BTCTrxReqDto trxReqDto) throws Exception {
        validateTrx(trxReqDto);
        long epoch = DateTimeUtil.getUnixTimeEpoc(trxReqDto.getDateTime());


        Long lastTimeStamp = WalletPersistence.WalletTransactionMap.get(trxReqDto.getWalletId()).floorKey(epoch);
        if (lastTimeStamp == null) {
            WalletPersistence.WalletTransactionMap.get(trxReqDto.getWalletId()).put(epoch, trxReqDto.getAmount());
        } else {
            double prvAmount = WalletPersistence.WalletTransactionMap.get(trxReqDto.getWalletId()).get(lastTimeStamp);
            double sum = Double.sum(prvAmount, trxReqDto.getAmount());
            WalletPersistence.WalletTransactionMap.get(trxReqDto.getWalletId()).put(epoch, sum);
        }

        return new BaseAPIResponse(HttpStatus.CREATED.name(), "transaction successful");
    }

    @Override
    public List<BTCTrxReqDto> getWalletTransactionHistories(TrxHistoryReqDto trxHistoryReqDto) throws Exception {
        if (!WalletPersistence.WalletTransactionMap.containsKey(trxHistoryReqDto.getWalletId())) {
            throw new Exception("Invalid wallet id");
        }

        TreeMap<Long, Double> timeStampToAmountMap = WalletPersistence.WalletTransactionMap.get(trxHistoryReqDto.getWalletId());

        long startTimeEpoch = DateTimeUtil.getUnixTimeEpoc(trxHistoryReqDto.getStartDateTime());
        long endTimeEpoch = DateTimeUtil.getUnixTimeEpoc(trxHistoryReqDto.getEndDateTime());


        int totalHour = (int) Math.ceil((endTimeEpoch - startTimeEpoch) / 3600.0);

        List<BTCTrxReqDto> result = new LinkedList<>();

        for (int i = 0; i < totalHour; i++) {
            long timeEpoch = ((i + 1) * 3600L) + startTimeEpoch;
            Long currTimeEpoch = timeStampToAmountMap.floorKey(timeEpoch);

            if (currTimeEpoch != null) {
                Double amount = timeStampToAmountMap.get(currTimeEpoch);

                BTCTrxReqDto ledger = BTCTrxReqDto.builder().amount(amount).dateTime(DateTimeUtil.getUTCFromUnixTimeEpoc(currTimeEpoch)).build();

                ledger.setWalletId(trxHistoryReqDto.getWalletId());
                result.add(ledger);
            }
        }


        return result;
    }

    @Override
    public BaseAPIResponse createWallet() {
        String walletId = UUID.randomUUID().toString();
        WalletPersistence.WalletTransactionMap.put(walletId, new TreeMap<>());
        return new BaseAPIResponse(HttpStatus.CREATED.name(), walletId);
    }

    private void validateTrx(BTCTrxReqDto trxReqDto) throws Exception {
        if (!WalletPersistence.WalletTransactionMap.containsKey(trxReqDto.getWalletId())) {
            throw new Exception("Invalid wallet id");
        }

        if (Double.compare(trxReqDto.getAmount(), 0) <= 0) {
            throw new Exception("amount should be greater than zero");
        }

    }
}
