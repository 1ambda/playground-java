package com.github.lambda.gateway.domain.catalog.repository;

import base.RepositoryTest;
import com.github.lambda.gateway.domain.catalog.entity.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@RepositoryTest
@ComponentScan("com.github.lambda.gateway.domain.catalog.repository")
public class ProductCustomRepositoryImplTest {

  @Autowired
  protected TestEntityManager entityManager;

  @Autowired
  private ProductCustomRepositoryImpl productCustomRepositoryImpl;

  @Before
  public void setUp() {
  }

  @Test
  public void findLockedProducts() {
    // given 
    final List<Product> expectedResult = Arrays.asList();

    // when 
    final List<Product> productList1 = productCustomRepositoryImpl.findLockedProducts();

    // then 
    assertThat(productList1.size()).isEqualTo(0);
  }
}
