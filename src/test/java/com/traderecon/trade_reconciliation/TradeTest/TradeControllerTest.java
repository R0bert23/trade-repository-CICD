package com.traderecon.trade_reconciliation.TradeTest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.traderecon.trade_reconciliation.Trade.Trade;
import com.traderecon.trade_reconciliation.Trade.TradeController;
import com.traderecon.trade_reconciliation.Trade.TradeServices;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TradeController.class)
public class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TradeServices tradeServices;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testGetAllTrades() throws Exception {
        Trade trade1 = new Trade("T1001", "AAPL", 180.5, 100, "Bloomberg", LocalDate.of(2025, 7, 30));
        Trade trade2 = new Trade("T1002", "GOOG", 1200.0, 50, "Reuters", LocalDate.of(2025, 7, 30));

        Mockito.when(tradeServices.getAllTrades()).thenReturn(Arrays.asList(trade1, trade2));

        mockMvc.perform(get("/api/trades"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].tradeId").value("T1001"))
                .andExpect(jsonPath("$[1].tradeId").value("T1002"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testGetTradeByIdFound() throws Exception {
        Trade trade = new Trade("T1001", "AAPL", 180.5, 100, "Bloomberg", LocalDate.of(2025, 7, 30));

        Mockito.when(tradeServices.getTradeById(1L)).thenReturn(Optional.of(trade));

        mockMvc.perform(get("/api/trades/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.tradeId").value("T1001"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testGetTradeByIdNotFound() throws Exception {
        Mockito.when(tradeServices.getTradeById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/trades/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testCreateTrade() throws Exception {
        Trade tradeToCreate = new Trade("T1003", "MSFT", 250.0, 75, "Bloomberg", LocalDate.of(2025, 7, 30));
        Trade createdTrade = new Trade("T1003", "MSFT", 250.0, 75, "Bloomberg", LocalDate.of(2025, 7, 30));
        // Assuming your service returns the saved trade with an ID or same data

        Mockito.when(tradeServices.createTrade(Mockito.any(Trade.class))).thenReturn(createdTrade);

        mockMvc.perform(post("/api/trades")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tradeToCreate)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testDeleteTradeSuccess() throws Exception {
        Long tradeId = 1L;

        Mockito.when(tradeServices.deleteTrade(tradeId)).thenReturn(true);

        mockMvc.perform(delete("/api/trades/{id}", tradeId)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("Trade deleted successfully"));
    }

}
