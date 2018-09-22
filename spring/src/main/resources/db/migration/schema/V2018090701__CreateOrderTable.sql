CREATE TABLE `Order` (
  -- primary key
  `id`          BIGINT                             NOT NULL AUTO_INCREMENT PRIMARY KEY,

  -- common
  `version`     BIGINT UNSIGNED DEFAULT '0'        NOT NULL,
  `created_at`  datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
  `updated_at`  datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
  `deleted_at`  datetime,
  INDEX `idx_Order_deletedAt` (`deleted_at`),

  -- columns
  `product_id`  BIGINT UNSIGNED                    NOT NULL,
  `quantity`    BIGINT UNSIGNED                    NOT NULL,
  `total_price` BIGINT UNSIGNED                    NOT NULL
);