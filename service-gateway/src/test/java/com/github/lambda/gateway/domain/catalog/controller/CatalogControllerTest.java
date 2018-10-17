package com.github.lambda.gateway.domain.catalog.controller;

import base.ControllerTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.lambda.gateway.domain.catalog.repository.ProductRepository;
import com.github.lambda.gateway.swagger.model.CategoryDTO;
import com.github.lambda.gateway.swagger.model.CategoryListDTO;
import com.github.lambda.gateway.swagger.model.Failure;
import com.github.lambda.gateway.swagger.model.PaginatedProductDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ControllerTest
public class CatalogControllerTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private ProductRepository productRepository;

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

  @WithMockUser(username = "user")
  @Test
  public void findPaginatedProducts_shouldReturnPaginatedDTO() throws Exception {
    // given
    Long page = 0L;
    Long count = 10L;
    String endpoint = new StringBuilder()
        .append("/api/catalog/products")
        .append("?page=" + page)
        .append("&count=" + count)
        .toString();
    assertThat(productRepository.count()).isGreaterThanOrEqualTo(10);

    // when
    MvcResult result = this.mvc
        .perform(get(endpoint))
        .andReturn();

    // then
    assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());

    String content = result.getResponse().getContentAsString();
    PaginatedProductDTO dto = mapper.readValue(content, PaginatedProductDTO.class);
    assertThat(dto.getProducts().size()).isEqualTo(count.intValue());
  }

  @WithMockUser(username = "user")
  @Test
  public void findPaginatedProducts_shouldReturnFailure_WhenGotInvalidPagination() throws Exception {
    // given
    Long page = Long.MAX_VALUE;
    Long count = 10L;
    String endpoint = new StringBuilder()
        .append("/api/catalog/products")
        .append("?page=" + page)
        .append("&count=" + count)
        .toString();

    // when
    MvcResult result = this.mvc
        .perform(get(endpoint))
        .andReturn();

    // then
    assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    String content = result.getResponse().getContentAsString();
    Failure dto = mapper.readValue(content, Failure.class);
    assertThat(dto.getType()).isEqualTo("java.lang.IllegalArgumentException");
  }
}
