package com.krymlov.lab1.database;

import com.krymlov.lab1.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class UploadDBTests {

    @Autowired
    private MockMvc mvc;

    private MockHttpSession session;

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private CountryRepo countryRepo;

    @Autowired
    private BrandRepo brandRepo;

    @Autowired
    private SellerRepo sellerRepo;

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
    @ValueSource(strings = {"country", "brand", "seller", "category", "item"})
    public void uploadCountryDB(String databasePath) throws Exception{

        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file",
                databasePath + ".xlsx",
                "application/x-xls",
                new ClassPathResource(databasePath + ".xlsx").getInputStream());

        mvc.perform(multipart("/upload/" + databasePath).file(mockMultipartFile)).andDo(print())
                .andExpect(status().is3xxRedirection());

        if (databasePath.equals("country")){
            Assertions.assertTrue(countryRepo.count() > 0);
            Assertions.assertTrue(countryRepo.existsByName("UKRAINE"));
        }
        if (databasePath.equals("brand")){
            Assertions.assertTrue(brandRepo.count() > 0);
            Assertions.assertTrue(brandRepo.existsByName("MSI"));
        }
        if (databasePath.equals("seller")){
            Assertions.assertTrue(sellerRepo.count() > 0);
            Assertions.assertTrue(sellerRepo.existsByName("ROZETKA"));
        }
        if (databasePath.equals("category")){
            Assertions.assertTrue(categoryRepo.count() > 0);
            Assertions.assertTrue(categoryRepo.existsByName("Комп'ютери"));
        }
        if (databasePath.equals("item")){
            Assertions.assertTrue(itemRepo.count() > 0);
            Assertions.assertTrue(itemRepo.existsByName("ADIDAS SUPERSTAR "));
        }
    }



}
