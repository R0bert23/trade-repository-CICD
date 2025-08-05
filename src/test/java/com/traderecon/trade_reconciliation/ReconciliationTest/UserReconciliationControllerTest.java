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
@WithMockUser(username = "user", roles = {"USER"})
public class UserReconciliationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testUserCannotStartReconciliation() throws Exception {
        mockMvc.perform(post("/api/reconciliation/start"))
                .andExpect(status().isForbidden()); // user is not allowed to POST
    }

    @Test
    public void testUserCanGetStatus() throws Exception {
        mockMvc.perform(get("/api/reconciliation/status/1"))
                .andExpect(status().isOk());
    }

}
