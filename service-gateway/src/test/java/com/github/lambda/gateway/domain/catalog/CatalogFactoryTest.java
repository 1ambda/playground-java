package com.github.lambda.gateway.domain.catalog;

import com.github.lambda.gateway.domain.base.YesNo;
import com.github.lambda.gateway.domain.catalog.entity.ProductOption;
import com.github.lambda.gateway.swagger.model.ProductOptionDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {CatalogFactory.class})
public class CatalogFactoryTest {

  @Autowired CatalogFactory catalogFactory;

  @Test
  public void convertToProductOptionDTO_shouldReturnDTO() {
    // given
    ProductOption entity = ProductOption.builder()
        .name("name")
        .price(1L)
        .description("description")
        .build();
    entity.setLocked(YesNo.N);

    // when
    ProductOptionDTO dto = catalogFactory.convertToProductOptionDTO(entity);

    // then
    assertThat(dto.getId()).isEqualTo(entity.getId());
    assertThat(dto.getName()).isEqualTo(entity.getName());
    assertThat(dto.getPrice()).isEqualTo(entity.getPrice());
    assertThat(dto.getDescription()).isEqualTo(entity.getDescription());
    assertThat(dto.getOnSale()).isEqualTo(entity.getLocked().reverse().value());
  }

}