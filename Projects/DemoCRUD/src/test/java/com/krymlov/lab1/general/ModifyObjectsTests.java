package com.krymlov.lab1.general;

import com.krymlov.lab1.entity.BrandEntity;
import com.krymlov.lab1.entity.CategoryEntity;
import com.krymlov.lab1.entity.ItemEntity;
import com.krymlov.lab1.entity.SellerEntity;
import com.krymlov.lab1.repository.BrandRepo;
import com.krymlov.lab1.repository.CategoryRepo;
import com.krymlov.lab1.repository.ItemRepo;
import com.krymlov.lab1.repository.SellerRepo;
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
public class ModifyObjectsTests {
    @Autowired
    private MockMvc mvc;
    private MockHttpSession session;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private SellerRepo sellerRepo;
    @Autowired
    private BrandRepo brandRepo;

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

    @ParameterizedTest
    @ValueSource(ints = {1,2,3})
    public void modifyExitingCategory(int id) throws Exception {

        Long categoryId = (long) id;

        CategoryEntity category = categoryRepo.getCategoryEntityById(categoryId);

        String modifiedCategoryName = category.getName() + "a";
        String modifiedCategoryInfo = category.getInfo() + "-";

        MockPart mockId = new MockPart("id", categoryId.toString().getBytes());
        MockPart mockName = new MockPart("name", modifiedCategoryName.getBytes());
        MockPart mockInfo = new MockPart("info", modifiedCategoryInfo.getBytes());

        mvc.perform(multipart("/category/edit").part(mockId, mockName, mockInfo).session(session)).andDo(print()).andExpect(status().is3xxRedirection());

        Assertions.assertNull(categoryRepo.findByName(category.getName()));

        Assertions.assertNotNull(categoryRepo.findByName(modifiedCategoryName));

    }

    @ParameterizedTest
    @ValueSource(ints = {1,2,3})
    public void modifyExitingBrand(int id) throws Exception {

        int minCountryId = 1;
        long maxCountryId = 14L;

        Long brandId = (long) id;
        BrandEntity brand = brandRepo.getBrandEntityById(brandId);

        String modifiedBrandName = brand.getName() + "a";
        String modifiedBrandInfo = brand.getInfo() + "-";

        MockPart mockId = new MockPart("id", brandId.toString().getBytes());
        MockPart mockName = new MockPart("name", modifiedBrandName.getBytes());
        MockPart mockInfo = new MockPart("info", modifiedBrandInfo.getBytes());
        MockPart mockCountry = new MockPart("country", String.valueOf(getRandomNumber(minCountryId, maxCountryId)).getBytes());

        mvc.perform(multipart("/brand/edit").part(mockId, mockName, mockInfo, mockCountry).session(session)).andDo(print()).andExpect(status().is3xxRedirection());

        Assertions.assertNull(brandRepo.findByName(brand.getName()));

        Assertions.assertNotNull(brandRepo.findByName(modifiedBrandName));

    }

    @ParameterizedTest
    @ValueSource(ints = {1,2,3})
    public void modifyExitingSeller(int id) throws Exception {

        int minAccNum = 900000;
        long maxAccNum = 999999L;

        Long sellerId = (long) id;
        SellerEntity seller = sellerRepo.getSellerEntityById(sellerId);

        String modifiedSellerName = seller.getName() + "a";
        String modifiedSellerInfo = seller.getInfo() + "-";

        MockPart mockId = new MockPart("id", sellerId.toString().getBytes());
        MockPart mockName = new MockPart("name", modifiedSellerName.getBytes());
        MockPart mockInfo = new MockPart("info", modifiedSellerInfo.getBytes());
        MockPart mockAcc = new MockPart("accreditation", String.valueOf(getRandomNumber(minAccNum, maxAccNum)).getBytes());

        mvc.perform(multipart("/seller/edit").part(mockId, mockName, mockInfo, mockAcc).session(session)).andDo(print()).andExpect(status().is3xxRedirection());

        Assertions.assertNull(sellerRepo.findByName(seller.getName()));

        Assertions.assertNotNull(sellerRepo.findByName(modifiedSellerName));

    }

    @ParameterizedTest
    @ValueSource(ints = {1,2,3})
    public void modifyExistingItem(int id) throws Exception {

        Long itemId = (long) id;
        ItemEntity item = itemRepo.getItemEntityById(itemId);

        String modifiedItemName = item.getName() + "a";
        String modifiedItemInfo = item.getInfo() + "-";

        MockPart mockId = new MockPart("id", itemId.toString().getBytes());
        MockPart mockName = new MockPart("name", modifiedItemName.getBytes());
        MockPart mockInfo = new MockPart("info", modifiedItemInfo.getBytes());
        MockPart mockCategory = new MockPart("category", item.getCategory().getId().toString().getBytes());
        MockPart mockBrand = new MockPart("brand", item.getBrand().getId().toString().getBytes());
        MockPart mockSeller = new MockPart("seller", item.getSeller().getId().toString().getBytes());
        MockPart mockPrice = new MockPart("price", String.valueOf(getRandomNumber(1, 999L)).getBytes());

        mvc.perform(multipart("/item/edit").part(mockId, mockName, mockInfo, mockCategory, mockBrand, mockSeller, mockPrice).session(session)).andDo(print()).andExpect(status().is3xxRedirection());

        Assertions.assertNull(itemRepo.findByName(item.getName()));

        Assertions.assertNotNull(itemRepo.findByName(modifiedItemName));
    }

}
