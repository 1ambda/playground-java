package com.github.lambda.gateway.domain.order.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonValue;
import com.github.lambda.gateway.domain.base.BaseEntity;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(
    name = "`Order`",
    indexes = {
      @Index(name = "`idx_Order_createdAt`", columnList = "`created_at`", unique = false),
      @Index(name = "`idx_Order_deletedAt`", columnList = "`deleted_at`", unique = false),
      @Index(name = "`idx_Order_locked`", columnList = "`locked`", unique = false),
      @Index(name = "`idx_Order_state`", columnList = "`state`", unique = false),
    })
public class Order extends BaseEntity {
  // ref: https://schema.org/OrderStatus
  public enum State {
    CANCELLED("CANCELLED"),
    DELIVERED("DELIVERED"),
    IN_TRANSIT("IN_TRANSIT"),
    PROBLEM("PROBLEM"),
    PROCESSING("PROCESSING"),
    RETURNED("RETURNED");

    private String value;

    State(String value) {
      this.value = value;
    }

    @JsonValue
    public String value() {
      return value;
    }
  }

  @Enumerated(EnumType.STRING)
  @Column(name = "`state`", nullable = false)
  private State state;

  @PositiveOrZero
  @Column(name = "`amount`", nullable = false)
  private Long amount;

  @Size(min = 0, max = 50)
  @Column(name = "`shipping_country`", nullable = false)
  private String shippingCountry;

  @Size(min = 0, max = 50)
  @Column(name = "`shipping_city`", nullable = false)
  private String shippingCity;

  @Size(min = 0, max = 20)
  @Column(name = "`shipping_zipcode`", nullable = false)
  private String shippingZipCode;

  @Size(min = 0, max = 255)
  @Column(name = "`shipping_address1`", nullable = false)
  private String shippingAddress1;

  @Size(min = 0, max = 255)
  @Column(name = "`shipping_address2`", nullable = false)
  private String shippingAddress2;

  @Size(min = 0, max = 65535)
  @Column(name = "`shipping_message`", nullable = false, columnDefinition = "TEXT")
  private String shippingMessage;

  @Size(min = 0, max = 50)
  @Column(name = "`orderer_name`", nullable = false)
  private String ordererName;

  @Size(min = 0, max = 50)
  @Column(name = "`orderer_phone`", nullable = false)
  private String ordererPhone;

  @Size(min = 0, max = 50)
  @Column(name = "`orderer_email`", nullable = false)
  private String ordererEmail;

  @Size(min = 0, max = 50)
  @Column(name = "`recipient_name`", nullable = false)
  private String recipientName;

  @Size(min = 0, max = 50)
  @Column(name = "`recipient_phone`", nullable = false)
  private String recipientPhone;

  @Size(min = 0, max = 50)
  @Column(name = "`recipient_email`", nullable = false)
  private String recipientEmail;

  /** other domains */
  @Column(name = "`user_id`", nullable = false)
  private Long userId;

  /** relations */
  @ToString.Exclude
  @Builder.Default
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "order")
  private List<OrderDetail> orderDetails = new ArrayList<>();

  /** functions */
  public void addOrderDetail(OrderDetail orderDetail) {
    orderDetail.setOrder(this);
    orderDetails.add(orderDetail);
  }

  public void removeOrderDetail(OrderDetail orderDetail) {
    orderDetail.setOrder(null);
    orderDetails.remove(orderDetail);
  }
}
