package com.krymlov.lab1.database;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class DBDownloadTests {

    @Autowired
    private MockMvc mvc;

    private MockHttpSession session;

    @BeforeEach
    public void beforeEach() throws Exception {

        String adminUsername = "admin";
        String adminPassword = "123123";

        MvcResult mvcResult = mvc.perform(formLogin().user(adminUsername).password(adminPassword))
                .andExpect(authenticated().withUsername(adminUsername)
                        .withRoles("ADMIN"))
                .andReturn();
        session = (MockHttpSession) mvcResult.getRequest().getSession(false);

        Assertions.assertNotNull(session);

    }

    @AfterEach
    public void afterEach() throws Exception {

        mvc.perform(get("/logout").session(session)).andDo(print()).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/login?logout"));

    }

    @ParameterizedTest
    @ValueSource(strings = {"category", "brand", "seller", "item"})
    public void downloadDB(String databasePath) throws Exception {

        mvc.perform(get("/export/" + databasePath).session(session)).andDo(print()).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM));

    }

}
