package com.krymlov.lab1.general;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class PagesExistsTests {

	@Autowired
	private MockMvc mvc;

	@Test
	void categoryPageLoad() throws Exception{
		mvc.perform(get("/category")).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("Категорії")));
	}

	@Test
	void productsPageLoad() throws Exception{
		mvc.perform(get("/item")).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("Товари")));
	}

	@Test
	void brandsPageLoad() throws Exception{
		mvc.perform(get("/brand")).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("Бренди")));
	}

	@Test
	void sellersPageLoad() throws Exception{
		mvc.perform(get("/seller")).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("Товари")));
	}

	@Test
	void loginPageLoad() throws Exception{
		mvc.perform(get("/login")).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("Авторизація")));
	}

	@Test
	void registerPageLoad() throws Exception{
		mvc.perform(get("/registration")).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("Реєстрація")));
	}

	@Test
	void productsSortAsc() throws Exception{
		mvc.perform(get("/item/sort/up")).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("Товари")));
	}

	@Test
	void productsSortDesc() throws Exception{
		mvc.perform(get("/item/sort/down")).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("Товари")));
	}

	@Test
	void notAuthorizedCart() throws Exception{
		mvc.perform(get("/cart")).andDo(print()).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("http://localhost/login"));
	}


}