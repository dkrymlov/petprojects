package com.krymlov.lab1.general;

import com.krymlov.lab1.entity.ItemEntity;
import com.krymlov.lab1.repository.BrandRepo;
import com.krymlov.lab1.repository.CategoryRepo;
import com.krymlov.lab1.repository.ItemRepo;
import com.krymlov.lab1.repository.SellerRepo;
import com.krymlov.lab1.utils.RandomString;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class CreateObjectsTests {

    @Autowired
    private MockMvc mvc;

    private MockHttpSession session;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private BrandRepo brandRepo;

    @Autowired
    private SellerRepo sellerRepo;

    @Autowired
    private ItemRepo itemRepo;

    @BeforeAll
    public static void beforeAll(){

    }

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

    public int getRandomNumber(int min, Long max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    @Test
    public void createCategory() throws Exception {

        String validCategoryName = RandomString.getAlphaNumericString(6);
        String validCategoryInfo = RandomString.getAlphaNumericString(6);

        MockPart mockName = new MockPart("name", validCategoryName.getBytes());
        MockPart mockInfo = new MockPart("info", validCategoryInfo.getBytes());

        mvc.perform(multipart("/category/create").part(mockName, mockInfo).session(session)).andDo(print()).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/category"));

        Assertions.assertNotNull(categoryRepo.findByName(validCategoryName));

    }

    @ParameterizedTest
    @ValueSource(strings = {"Периферія", "Мобільні пристрої"})
    public void createExitingCategory(String existingCategoryName) throws Exception {

        MockPart mockName = new MockPart("name", existingCategoryName.getBytes());
        MockPart mockInfo = new MockPart("info", RandomString.getAlphaNumericString(6).getBytes());

        mvc.perform(multipart("/category/create").part(mockName, mockInfo).session(session)).andDo(print()).andExpect(status().is3xxRedirection());

        Assertions.assertEquals(1, categoryRepo.countAllByName(existingCategoryName));

    }

    @Test
    public void createBrand() throws Exception {

        int minCountryId = 1;
        long maxCountryId = 14L;

        String validBrandName = RandomString.getAlphaNumericString(6);
        String validBrandInfo = RandomString.getAlphaNumericString(6);
        MockPart mockName = new MockPart("name", validBrandName.getBytes());
        MockPart mockInfo = new MockPart("info", validBrandInfo.getBytes());
        MockPart mockCountry = new MockPart("country", String.valueOf(getRandomNumber(minCountryId, maxCountryId)).getBytes());

        mvc.perform(multipart("/brand/create").part(mockName, mockInfo, mockCountry).session(session)).andDo(print()).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/brand"));

        System.out.println(validBrandName);
        Assertions.assertNotNull(brandRepo.findByName(validBrandName));

    }

    @ParameterizedTest
    @ValueSource(strings = {"MSI", "GIGABYTE", "APPLE"})
    public void createExitingBrand(String existingBrandName) throws Exception {

        int minCountryId = 1;
        long maxCountryId = 14L;

        MockPart mockName = new MockPart("name", existingBrandName.getBytes());
        MockPart mockInfo = new MockPart("info", RandomString.getAlphaNumericString(6).getBytes());
        MockPart mockCountry = new MockPart("country", String.valueOf(getRandomNumber(minCountryId, maxCountryId)).getBytes());

        mvc.perform(multipart("/brand/create").part(mockName, mockInfo, mockCountry).session(session)).andDo(print()).andExpect(status().is3xxRedirection());

        Assertions.assertEquals(1, brandRepo.countAllByName(existingBrandName));

    }

    @Test
    public void createSeller() throws Exception {

        int minAccNum = 900000;
        long maxAccNum = 999999L;

        String validSellerName = RandomString.getAlphaNumericString(6);
        String validSellerInfo = RandomString.getAlphaNumericString(6);
        MockPart mockName = new MockPart("name", validSellerName.getBytes());
        MockPart mockInfo = new MockPart("info", validSellerInfo.getBytes());
        MockPart mockAcc = new MockPart("accreditation", String.valueOf(getRandomNumber(minAccNum, maxAccNum)).getBytes());

        mvc.perform(multipart("/seller/create").part(mockName, mockInfo, mockAcc).session(session)).andDo(print()).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/seller"));

        Assertions.assertNotNull(sellerRepo.findByName(validSellerName));

    }

    @ParameterizedTest
    @ValueSource(strings = {"ROZETKA", "COMFY", "CITRUS"})
    public void createExitingSeller(String existingSellerName) throws Exception {

        int minAccNum = 900000;
        long maxAccNum = 999999L;

        MockPart mockName = new MockPart("name", existingSellerName.getBytes());
        MockPart mockInfo = new MockPart("info", RandomString.getAlphaNumericString(6).getBytes());
        MockPart mockAcc = new MockPart("accreditation", String.valueOf(getRandomNumber(minAccNum, maxAccNum)).getBytes());

        mvc.perform(multipart("/seller/create").part(mockName, mockInfo, mockAcc).session(session)).andDo(print()).andExpect(status().is3xxRedirection());

        Assertions.assertEquals(1, sellerRepo.countAllByName(existingSellerName));

    }

    @Test
    public void createItem() throws Exception {

        long countCategories = categoryRepo.count();
        long countBrands = brandRepo.count();
        long countSellers = sellerRepo.count();

        int categoryId = getRandomNumber(1,countCategories-1);
        int brandId = getRandomNumber(1,countBrands-1);
        int sellerId = getRandomNumber(1,countSellers-1);

        String validItemName = RandomString.getAlphaNumericString(6);
        String validItemInfo = RandomString.getAlphaNumericString(6);

        MockPart mockName = new MockPart("name", validItemName.getBytes());
        MockPart mockInfo = new MockPart("info", validItemInfo.getBytes());
        MockPart mockCategory = new MockPart("category", String.valueOf(categoryId).getBytes());
        MockPart mockBrand = new MockPart("brand", String.valueOf(brandId).getBytes());
        MockPart mockSeller = new MockPart("seller", String.valueOf(sellerId).getBytes());
        MockPart mockPrice = new MockPart("price", String.valueOf(getRandomNumber(1, 999L)).getBytes());

        mvc.perform(multipart("/item/create").part(mockName, mockInfo, mockCategory, mockBrand, mockSeller, mockPrice).session(session)).andDo(print()).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/item"));

        Assertions.assertNotNull(itemRepo.findByName(validItemName));

    }

    @ParameterizedTest
    @ValueSource(ints = {1,2,3})
    public void createExitingItem(int id) throws Exception {

        ItemEntity item = itemRepo.getItemEntityById((long) id);

        MockPart mockName = new MockPart("name", item.getName().getBytes());
        MockPart mockInfo = new MockPart("info", item.getInfo().getBytes());
        MockPart mockCategory = new MockPart("category", item.getCategory().getId().toString().getBytes());
        MockPart mockBrand = new MockPart("brand", item.getBrand().getId().toString().getBytes());
        MockPart mockSeller = new MockPart("seller", item.getSeller().getId().toString().getBytes());
        MockPart mockPrice = new MockPart("price", String.valueOf(getRandomNumber(1, 999L)).getBytes());

        mvc.perform(multipart("/item/create").part(mockName, mockInfo, mockCategory, mockBrand, mockSeller, mockPrice).session(session)).andDo(print()).andExpect(status().is3xxRedirection());

        Assertions.assertEquals(1, itemRepo.countAllByName(item.getName()));

    }

}
