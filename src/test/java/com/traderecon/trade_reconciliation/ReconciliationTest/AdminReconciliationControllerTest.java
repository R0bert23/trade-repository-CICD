package com.traderecon.trade_reconciliation.ReconciliationTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser(username = "admin", roles = {"ADMIN"})

public class AdminReconciliationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAdminCanStartReconciliation() throws Exception {
        mockMvc.perform(post("/api/reconciliation/start"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.status").value("COMPLETED"));
    }

    @Test
    public void testAdminCanGetDifferences() throws Exception {
        mockMvc.perform(get("/api/reconciliation/differences/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAdminCanGetDifferencesOut() throws Exception {
        mockMvc.perform(get("/api/reconciliation/differences/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4)) // Expecting 4 items in the array
                .andExpect(jsonPath("$[0].tradeId").value("T1002"))
                .andExpect(jsonPath("$[0].fieldName").value("price"))
                .andExpect(jsonPath("$[0].valueSystemA").value("150.0"))
                .andExpect(jsonPath("$[0].valueSystemB").value("148.0"))
                .andExpect(jsonPath("$[0].reconciliationRun.status").value("COMPLETED"));
    }


}
