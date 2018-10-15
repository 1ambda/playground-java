package com.github.lambda.gateway.domain.catalog;

import java.util.UUID;

import base.AbstractServiceTest;
import com.github.lambda.gateway.domain.catalog.entity.Category;
import com.github.lambda.gateway.domain.catalog.entity.Image;
import com.github.lambda.gateway.domain.catalog.entity.Product;
import com.github.lambda.gateway.domain.catalog.entity.ProductOption;
import com.github.lambda.gateway.domain.catalog.repository.CategoryRepository;
import com.github.lambda.gateway.domain.catalog.repository.ImageRepository;
import com.github.lambda.gateway.domain.catalog.repository.ProductOptionRepository;
import com.github.lambda.gateway.domain.catalog.repository.ProductRepository;
import com.github.lambda.gateway.exception.type.BadRequestException;
import com.github.lambda.gateway.swagger.model.CategoryListDTO;
import com.github.lambda.gateway.swagger.model.ProductDTO;
import com.github.lambda.gateway.swagger.model.ProductListDTO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class CatalogServiceTest extends AbstractServiceTest {

  @Autowired private CatalogService catalogService;

  @Autowired private ProductRepository productRepository;

  @Autowired private CategoryRepository categoryRepository;

  @Autowired private ImageRepository imageRepository;

  @Autowired private ProductOptionRepository productOptionRepository;

  @Test
  public void getProductById_shouldReturnDTO_whenProductExists() {
    // given
    Product product = prepareProduct();

    // when
    ProductDTO productDTO = catalogService.getProductById(product.getId());

    // then
    assertThat(productDTO).isNotNull();
    assertThat(productDTO.getItem().getId()).isEqualTo(product.getId());
  }

  @Test(expected = BadRequestException.class)
  public void getProductById_shouldThrowBadRequest_whenProductDoesNotExist() {
    // given
    Long productId = -1L;
    assertThat(productRepository.findById(productId)).isEmpty();

    // when
    catalogService.getProductById(productId);

    // then
    fail("should throw an exception");
  }

  @Test
  public void getPaginatedProducts_shouldReturnProductListDTO() {
    // given
    long totalProductCount = 25;
    for (int i = 0; i < totalProductCount; i++) {
      prepareProduct();
    }
    assertThat(productRepository.count()).isEqualTo(totalProductCount);

    PageRequest pagination1 = PageRequest.of(0, 10);
    PageRequest pagination2 = PageRequest.of(2, 10);

    // when
    ProductListDTO dto1 = catalogService.getPaginatedProducts(pagination1);
    ProductListDTO dto2 = catalogService.getPaginatedProducts(pagination2);

    // then
    assertThat(dto1.getItems().size()).isEqualTo(10);
    assertThat(dto2.getItems().size()).isEqualTo(5);
  }

  @Test
  public void getAllCategories_shouldReturnCategoryListDTO() {
    // given
    long totalCategoryCount = 10;
    for (int i = 0; i < totalCategoryCount; i++) {
      prepareCategory();
    }
    assertThat(categoryRepository.count()).isEqualTo(totalCategoryCount);

    // when
    CategoryListDTO categoryListDTO = catalogService.getAllCategories();

    // then
    assertThat(categoryListDTO.getItems().size()).isEqualTo(totalCategoryCount);
  }

  private ProductOption prepareProductOption() {
    Product product = prepareProduct();

    Image productOptionImage = null;
    ProductOption productOption =
        ProductOption.builder()
            .product(product)
            .name("Memory 8 GB+")
            .price(160000L)
            .description("")
            .image(productOptionImage)
            .build();

    productOption = productOptionRepository.save(productOption);
    return productOption;
  }

  private Category prepareCategory() {
    String name = UUID.randomUUID().toString();

    Category category =
        Category.builder()
            .parentCategoryId(null)
            .path("/" + name)
            .name(name)
            .displayName(name)
            .description("")
            .build();
    category = categoryRepository.save(category);
    return category;
  }

  private Product prepareProduct() {
    Category category = prepareCategory();

    Image image = null;

    Product product =
        Product.builder()
            .name("LG Notebook GRAM 13")
            .price(1350000L)
            .description(
                "LG gram Thin & Light Laptop - Up to 16.5 hrs, Thunderbolt 3, Finger Print Reader (Windows not installed)")
            .category(category)
            .image(image)
            .build();

    product = productRepository.save(product);

    return product;
  }
}
