package com.github.lambda.playground.domain.catalog;

import javax.transaction.Transactional;

import base.AbstractServiceTest;
import com.github.lambda.playground.domain.catalog.repository.ProductRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

public class CatalogServiceTest extends AbstractServiceTest {

  @Autowired
  private CatalogService catalogService;

  @Autowired
  private ProductRepository productRepository;

  @Transactional
  @Rollback
  @Test
  public void getProductById_shouldReturnDTO_whenProductExists() {

  }

  @Transactional
  @Rollback
  @Test
  public void getProductById_shouldThrowBadRequest_whenProductDoesNotExist() {
    assertThat(productRepository.findById(-1L)).isEmpty();
  }

  @Test
  public void getPaginatedProducts() {

  }
}
