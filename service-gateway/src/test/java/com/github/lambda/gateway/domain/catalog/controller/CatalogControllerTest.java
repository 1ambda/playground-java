package com.github.lambda.gateway.domain.catalog.controller;

import java.util.List;

import base.AbstractControllerTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.lambda.gateway.swagger.model.CategoryDTO;
import com.github.lambda.gateway.swagger.model.CategoryListDTO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CatalogControllerTest extends AbstractControllerTest {

  @Autowired
  private MockMvc mvc;

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

  @WithMockUser(username = "user")
  @Test
  public void findOneProduct_shouldReturn400_withInvalidProductId() throws Exception {
    // given
    Long invalidProductId1 = 0L;
    Long invalidProductId2 = Long.MAX_VALUE;

    // when
    MvcResult result1 = this.mvc
        .perform(get("/api/catalog/product/" + invalidProductId1))
        .andReturn();
    MvcResult result2 = this.mvc
        .perform(get("/api/catalog/product/" + invalidProductId2))
        .andReturn();

    // then
    assertThat(result1.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    assertThat(result2.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }
}
