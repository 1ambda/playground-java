CREATE TABLE `Cart` (
  -- primary key
  `id`         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,

  -- FK columns
  `user_id`     BIGINT UNSIGNED NOT NULL,

  INDEX `idx_Cart_userId` (`user_id`),
  CONSTRAINT `uniq_Cart_userId` UNIQUE (`user_id`),

  CONSTRAINT `fk_Cart_userId`
  FOREIGN KEY (`user_id`) REFERENCES `User` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,

  -- common
  `version`    BIGINT UNSIGNED DEFAULT '0'        NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
  `deleted_at` datetime,
  `locked`     VARCHAR(4)                         NOT NULL,
  INDEX `idx_Cart_createdAt` (`created_at`),
  INDEX `idx_Cart_deletedAt` (`deleted_at`),
  INDEX `idx_Cart_locked` (`locked`)

  -- columns
);

CREATE TABLE `CartLine` (
  -- primary key
  `id`         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,

  -- FK columns
  `cart_id`       BIGINT UNSIGNED NULL,
  `product_id`    BIGINT UNSIGNED NOT NULL,

  INDEX `idx_CartLine_cartId` (`cart_id`),
  INDEX `idx_CartLine_productId` (`product_id`),

  CONSTRAINT `fk_CartLine_cartId`
  FOREIGN KEY (`cart_id`) REFERENCES `Cart` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,

  CONSTRAINT `fk_CartLine_productId`
  FOREIGN KEY (`product_id`) REFERENCES `Product` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,

  -- common
  `version`    BIGINT UNSIGNED DEFAULT '0'        NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
  `deleted_at` datetime,
  `locked`     VARCHAR(4)                         NOT NULL,
  INDEX `idx_CartLine_createdAt` (`created_at`),
  INDEX `idx_CartLine_deletedAt` (`deleted_at`),
  INDEX `idx_CartLine_locked` (`locked`),

  -- columns
  `index`         BIGINT UNSIGNED NOT NULL,
  `quantity`      BIGINT UNSIGNED NOT NULL
);

CREATE TABLE `CartLineOption` (
  -- primary key
  `id`         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,

  -- FK columns
  `cart_line_id`         BIGINT UNSIGNED NOT NULL,
  `product_option_id`    BIGINT UNSIGNED NOT NULL,

  INDEX `idx_OrderLine_cartLineId` (`cart_line_id`),
  INDEX `idx_OrderLine_productOptionId` (`product_option_id`),

  CONSTRAINT `fk_CartLineOption_cartLineId`
  FOREIGN KEY (`cart_line_id`) REFERENCES `CartLine` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,

  CONSTRAINT `fk_CartLineOption_productId`
  FOREIGN KEY (`product_option_id`) REFERENCES `ProductOption` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,

  -- common
  `version`    BIGINT UNSIGNED DEFAULT '0'        NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
  `deleted_at` datetime,
  `locked`     VARCHAR(4)                         NOT NULL,
  INDEX `idx_CartLineOption_createdAt` (`created_at`),
  INDEX `idx_CartLineOption_deletedAt` (`deleted_at`),
  INDEX `idx_CartLineOption_locked` (`locked`),

  -- columns
  `quantity`          BIGINT UNSIGNED NOT NULL
);
