package base.fixture;

import com.github.lambda.gateway.domain.catalog.entity.Category;
import com.github.lambda.gateway.domain.catalog.entity.Product;
import com.github.lambda.gateway.domain.catalog.entity.ProductOption;
import com.github.lambda.gateway.domain.catalog.repository.CategoryRepository;
import com.github.lambda.gateway.domain.catalog.repository.ProductOptionRepository;
import com.github.lambda.gateway.domain.catalog.repository.ProductRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface CatalogFixture {

  ProductRepository getProductRepository();

  ProductOptionRepository getProductOptionRepository();

  CategoryRepository getCategoryRepository();

  default Product prepareProductAndOptions() {
    Category category = prepareCategory();
    Product product = prepareProduct(category);
    prepareProductOptions(product);

    return product;
  }

  default List<ProductOption> prepareProductOptions(Product product) {

    List<ProductOption> productOptions = new ArrayList<>();

    ProductOption productOption =
        ProductOption.builder()
            .product(product)
            .name("Memory 8 GB+")
            .price(160000L)
            .description("")
            .build();

    ProductOptionRepository productOptionRepository = getProductOptionRepository();
    productOption = productOptionRepository.save(productOption);

    productOptions.add(productOption);

    return productOptions;
  }

  default Category prepareCategory() {
    String name = UUID.randomUUID().toString();

    Category category =
        Category.builder()
            .parentCategoryId(null)
            .path("/" + name)
            .name(name)
            .displayName(name)
            .description("")
            .build();

    CategoryRepository categoryRepository = getCategoryRepository();
    category = categoryRepository.save(category);
    return category;
  }

  default Product prepareProduct(Category category) {
    Product product =
        Product.builder()
            .name("LG Notebook GRAM 13")
            .price(1350000L)
            .description(
                "LG gram Thin & Light Laptop - Up to 16.5 hrs, Thunderbolt 3, Finger Print Reader (Windows not installed)")
            .category(category)
            .build();

    ProductRepository productRepository = getProductRepository();
    product = productRepository.save(product);

    return product;
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  default Long prepareProductInTransaction() {
    // given: prepare Product
    Category category = prepareCategory();
    Product product = prepareProduct(category);
    Long productId = product.getId();

    return productId;
  }
}
