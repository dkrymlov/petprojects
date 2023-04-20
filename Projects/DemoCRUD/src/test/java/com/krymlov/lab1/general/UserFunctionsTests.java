package com.krymlov.lab1.general;

import com.krymlov.lab1.entity.CartItemEntity;
import com.krymlov.lab1.service.CartItemService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockPart;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class UserFunctionsTests {

    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private MockMvc mvc;
    private MockHttpSession session;
    public static String validUsername;
    public static String validPassword;

    @BeforeAll
    public static void beforeAll(){
        validUsername = "user";
        validPassword = "123123";
    }

    @BeforeEach
    public void beforeEach() throws Exception {

        MvcResult mvcResult = mvc.perform(formLogin().user(validUsername).password(validPassword))
                .andExpect(authenticated().withUsername(validUsername)
                        .withRoles("USER"))
                .andReturn();
        session = (MockHttpSession) mvcResult.getRequest().getSession(false);

        Assertions.assertNotNull(session);

    }

    @AfterEach
    public void afterEach() throws Exception {
        mvc.perform(get("/logout").session(session)).andDo(print()).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/login?logout"));
    }

    @Test
    void openCart() throws Exception{
        mvc.perform(get("/cart").session(session)).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("Корзина")));
    }

    @Test
    void cleanCart() throws Exception{
        mvc.perform(get("/cart/clean").session(session)).andDo(print()).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/cart"));
    }

    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4,5,6,7,8,9,10})
    void addProductToCart(int id) throws Exception {
        mvc.perform(get("/cart/add?id=" + id).session(session)).andDo(print()).andExpect(status().is3xxRedirection());
    }

    @ParameterizedTest
    @ValueSource(ints = {1})
    void createOrder(int id) throws Exception {
        mvc.perform(get("/cart/add?id=" + id).session(session)).andDo(print()).andExpect(status().is3xxRedirection());

        Iterable<CartItemEntity> cartItems = cartItemService.getCartItemRepo().findAll();
        MockPart mockTotalPrice = new MockPart("totalPrice", String.valueOf(cartItemService.getTotalCartPrice(cartItems)).getBytes());
        MockPart mockItems = new MockPart("items", cartItems.toString().getBytes());
        MockPart mockEmail = new MockPart("email", "test@gmail.com".getBytes());

        Assertions.assertNotNull(cartItems);

        mvc.perform(multipart("/order/create").part(mockItems,mockEmail,mockTotalPrice).session(session)).andDo(print()).andExpect(status().isOk());
    }


}
