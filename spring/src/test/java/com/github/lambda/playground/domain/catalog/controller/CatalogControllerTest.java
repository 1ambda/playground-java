package com.github.lambda.playground.domain.catalog.controller;

import java.util.List;

import base.AbstractControllerTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.lambda.playground.swagger.model.CategoryDTO;
import com.github.lambda.playground.swagger.model.CategoryListDTO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CatalogControllerTest extends AbstractControllerTest {

  @Autowired private MockMvc mvc;

  private ObjectMapper mapper = new ObjectMapper();

  @WithMockUser(username = "user")
  @Test
  public void shouldGetAllCategories() throws Exception {
    // when
    MvcResult result = this.mvc
        .perform(get("/api/catalog/categories"))
        .andExpect(status().isOk())
        .andReturn();

    // then
    String content = result.getResponse().getContentAsString();
    CategoryListDTO dto = mapper.readValue(content, CategoryListDTO.class);
    List<CategoryDTO> categories = dto.getItems();

    assertThat(categories).isNotEmpty();
  }
}
