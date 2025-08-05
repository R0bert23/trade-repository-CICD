package com.traderecon.trade_reconciliation.InstrumentTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.traderecon.trade_reconciliation.Instrument.Instrument;
import com.traderecon.trade_reconciliation.Instrument.InstrumentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class InstrumentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InstrumentRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createInstrument_shouldReturnCreated() throws Exception {
        Instrument instrument = new Instrument();
        instrument.setName("Apple Inc.");
        instrument.setSymbol("AAPL");
        instrument.setIsin("US0378331005");

        mockMvc.perform(post("/api/instruments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(instrument)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.symbol").value("AAPL"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void getById_shouldReturnInstrument_whenExists() throws Exception {
        Instrument instrument = repository.save(new Instrument(null, "MSFT", "Microsoft", "US5949181045"));

        mockMvc.perform(get("/api/instruments/" + instrument.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.symbol").value("MSFT"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void getById_shouldReturn404_whenNotFound() throws Exception {
        mockMvc.perform(get("/api/instruments/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateInstrument_shouldReturnUpdated() throws Exception {
        Instrument existing = repository.save(new Instrument(null, "TSLA", "Tesla", "US88160R1014"));
        existing.setName("Tesla Inc.");

        mockMvc.perform(put("/api/instruments/" + existing.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(existing)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Tesla Inc."));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteInstrument_shouldReturn204() throws Exception {
        Instrument instrument = repository.save(new Instrument(null, "GOOG", "Alphabet", "US02079K3059"));

        mockMvc.perform(delete("/api/instruments/" + instrument.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "USER")
    void deleteInstrument_shouldReturn403_forNonAdmin() throws Exception {
        Instrument instrument = repository.save(new Instrument(null, "NFLX", "Netflix", "US64110L1061"));

        mockMvc.perform(delete("/api/instruments/" + instrument.getId()))
                .andExpect(status().isForbidden());
    }
}
