package com.traderecon.trade_reconciliation.ReconciliationTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser(username = "admin", roles = {"ADMIN"})
public class ReconciliationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testStartReconciliation() throws Exception {
        mockMvc.perform(post("/api/reconciliation/start")
                        .with(httpBasic("admin", "admin")))
                .andExpect(status().isCreated()) // or isOk() if you return 200
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.status").value("COMPLETED")); // or whatever you return
    }

    @Test
    public void testGetDifferences() throws Exception {
        mockMvc.perform(get("/api/reconciliation/differences/1")
                        .with(httpBasic("user", "user")))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetStatusNotFound() throws Exception {
        mockMvc.perform(get("/api/reconciliation/status/999")
                        .with(httpBasic("user", "user")))
                .andExpect(status().isNotFound());
    }
}
