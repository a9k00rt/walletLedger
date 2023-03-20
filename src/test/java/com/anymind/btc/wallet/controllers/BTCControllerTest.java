package com.anymind.btc.wallet.controllers;

import com.anymind.btc.wallet.constants.Endpoints;
import com.anymind.btc.wallet.interfaces.WalletTransactionInterface;
import com.anymind.btc.wallet.services.BTCWalletTransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BTCControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WalletTransactionInterface walletTransactionInterface;

    private static String walletId = null;

    @BeforeEach
    void init() {
        walletTransactionInterface = new BTCWalletTransactionService();
        walletId = walletTransactionInterface.createWallet().getMessage();
    }

    @Test
    void createWallet() throws Exception {
        this.mockMvc.perform(post(Endpoints.BASE_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isCreated());
    }

    @Test
    void addTransaction() throws Exception {
        this.mockMvc.perform(post(Endpoints.CREATE_BTC_TRANSACTION)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getTransactionJson()))
                .andExpect(status().isCreated());

    }

    private String getTransactionJson() {
        return "{\n" +
                "    \"walletId\":" + "\"" + walletId + "\",\n" +
                "    \"dateTime\": \"2019-10-05T17:00:00+00:00\",\n" +
                "    \"amount\": 400.0\n" +
                "}";
    }

    @Test
    void getLedgerHistories() throws Exception {
        addTransaction();
        String requestPayload = "{\n" +
                "    \"walletId\":" + "\"" + walletId + "\",\n" +
                "    \"startDateTime\": \"2019-10-05T13:00:00+00:00\",\n" +
                "    \"endDateTime\": \"2019-10-05T17:00:00+00:00\"\n" +
                "}";

        this.mockMvc.perform(get(Endpoints.HISTORY_BTC_TRANSACTION)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestPayload))
                .andExpect(status().isOk());
    }
}