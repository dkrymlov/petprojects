package com.krymlov.lab1.general;

import com.krymlov.lab1.repository.UserRepo;
import com.krymlov.lab1.utils.RandomString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockPart;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class AuthTests {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserRepo userRepo;
    static String actualUsername;
    static String actualPassword;
    static String validUsername;
    static String validPassword;
    static String notValidUsername;
    static String notValidPassword;

    @BeforeAll
    public static void beforeAll(){

        actualUsername = "user";
        actualPassword = "123123";

        validUsername = RandomString.getAlphaNumericString(4);
        validPassword = RandomString.getAlphaNumericString(6);

        notValidUsername = RandomString.getAlphaNumericString(2);
        notValidPassword = notValidUsername = RandomString.getAlphaNumericString(3);

    }

    @BeforeEach
    void beforeEach(){
    }

    @Test
    void notSuccessfulLogin() throws Exception{

        mvc.perform(formLogin().user(notValidUsername).password(notValidPassword)).andDo(print()).andExpect(redirectedUrl("/login?error"));

    }

    @Test
    void notSuccessfulRegister() throws Exception{

        MockPart username = new MockPart("username", notValidUsername.getBytes());
        MockPart password = new MockPart("password", notValidPassword.getBytes());

        mvc.perform(multipart("/registration").part(username, password).accept(MediaType.APPLICATION_JSON)).andDo(print());

        Assertions.assertNull(userRepo.findByUsername(notValidUsername));

    }

    @Test
    void successfulRegister() throws Exception{

        MockPart username = new MockPart("username", validUsername.getBytes());
        MockPart password = new MockPart("password", validPassword.getBytes());

        mvc.perform(multipart("/registration").part(username, password).accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(redirectedUrl("/login"));
        Assertions.assertNotNull(userRepo.findByUsername(validUsername));

    }

    @Test
    void successfulLogin() throws Exception{

        mvc.perform(formLogin().user(actualUsername).password(actualPassword)).andDo(print()).andExpect(redirectedUrl("/"));

    }

    @Test
    void logout() throws Exception {

        MvcResult mvcResult = mvc.perform(formLogin().user(actualUsername).password(actualPassword))
                .andExpect(authenticated().withUsername(actualUsername)
                        .withRoles("USER"))
                .andReturn();
        MockHttpSession session = (MockHttpSession) mvcResult.getRequest().getSession(false);

        Assertions.assertNotNull(session);

        mvc.perform(get("/logout").session(session)).andDo(print()).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/login?logout"));
    }

}
