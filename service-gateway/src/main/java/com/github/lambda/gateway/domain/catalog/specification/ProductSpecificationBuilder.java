package com.github.lambda.gateway.domain.catalog.specification;

import com.github.lambda.gateway.domain.catalog.entity.Product;
import com.github.lambda.gateway.domain.catalog.entity.Product_;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductSpecificationBuilder {

  public static Specification<Product> rangePrice(Long minPrice, Long maxPrice) {
    return new Specification<Product>() {
      @Override
      public Predicate toPredicate(Root<Product> root,
                                   CriteriaQuery<?> query,
                                   CriteriaBuilder criteriaBuilder) {

        Predicate predicate = null;

        if (minPrice != null && minPrice > 0L) {
          predicate = criteriaBuilder.greaterThanOrEqualTo(
              root.get(Product_.PRICE), minPrice);
        }

        if (maxPrice != null && maxPrice > 0L) {
          Predicate max = criteriaBuilder.lessThanOrEqualTo(root.get(Product_.PRICE), maxPrice);
          predicate = criteriaBuilder.and(predicate, max);
        }

        return predicate;
      }
    };
  }

}
