package com.github.lambda.playground.domain.catalog;

import java.util.List;
import java.util.stream.Collectors;

import com.github.lambda.playground.domain.catalog.entity.Category;
import com.github.lambda.playground.domain.catalog.entity.Product;
import com.github.lambda.playground.domain.catalog.entity.ProductOption;
import com.github.lambda.playground.swagger.model.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class CatalogFactory {

  public ProductOptionDTO convertToProductOptionDTO(ProductOption productOption) {
    return ProductOptionDTO.builder()
        .id(productOption.getId())
        .name(productOption.getName())
        .price(productOption.getPrice())
        .description(productOption.getDescription())
        .onSale(productOption.getLocked().reverse().value())
        .build();
  }

  public ProductItemDTO convertToProductItemDTO(Product product) {
    ProductItemDTO.ProductItemDTOBuilder builder =
        ProductItemDTO.builder()
            .id(product.getId())
            .createdAt(product.getCreateTimestamp())
            .updatedAt(product.getUpdateTimestamp())
            .name(product.getName())
            .price(product.getPrice())
            .description(product.getDescription())
            .onSale(product.getLocked().reverse().value());

    if (product.getCategory() != null) {
      builder
          .categoryId(product.getCategory().getId())
          .categoryDisplayName(product.getCategory().getDisplayName())
          .categoryPath(product.getCategory().getPath());
    }

    if (product.getImage() != null) {
      builder
          .imageId(product.getImage().getId())
          .imageType(product.getImage().getType())
          .imagePath(product.getImage().getPath());
    }

    return builder.build();
  }

  public ProductDTO convertToProductDTO(Product product) {
    List<ProductOptionDTO> optionDTOList =
        product
            .getProductOptions()
            .stream()
            .map(this::convertToProductOptionDTO)
            .collect(Collectors.toList());

    ProductDTO.ProductDTOBuilder builder =
        ProductDTO.builder().item(convertToProductItemDTO(product)).options(optionDTOList);

    return builder.build();
  }

  public ProductListDTO convertToProductListDTO(Page<Product> paginated) {
    List<ProductDTO> productDTOList =
        paginated
            .getContent()
            .stream()
            .map(this::convertToProductDTO)
            .collect(Collectors.toList());

    Pagination pagination =
        Pagination.builder()
            .currentPageOffset(paginated.getNumber())
            .itemCountPerPage(paginated.getNumberOfElements())
            .totalItemCount(paginated.getTotalElements())
            .build();

    ProductListDTO.ProductListDTOBuilder builder =
        ProductListDTO.builder().items(productDTOList).pagination(pagination);

    return builder.build();
  }

  public CategoryDTO convertToCategoryDTO(Category category) {
    return CategoryDTO.builder()
        .id(category.getId())
        .parentCategoryId(category.getParentCategoryId())
        .name(category.getName())
        .path(category.getPath())
        .displayName(category.getDisplayName())
        .description(category.getDescription())
        .build();
  }

  public CategoryListDTO convertToCategoryListDTO(List<Category> categories) {
    List<CategoryDTO> items = categories
        .stream()
        .map(this::convertToCategoryDTO)
        .collect(Collectors.toList());

    return CategoryListDTO.builder()
        .items(items)
        .build();
  }
}
