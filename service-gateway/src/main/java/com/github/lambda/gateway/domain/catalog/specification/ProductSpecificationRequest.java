package com.github.lambda.gateway.domain.catalog.specification;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode
@ToString
@Validated
public class ProductSpecificationRequest {

  @Min(0)
  private Long categoryId;

  private String search;

  @Min(0)
  private Long minPrice;

  @Min(0)
  private Long maxPrice;

  @Min(0)
  private Long minRate;

  @Builder.Default
  private List<String> tags = new ArrayList();

  private String minShippingDate;
}
