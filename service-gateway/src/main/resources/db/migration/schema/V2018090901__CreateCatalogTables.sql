CREATE TABLE `Category` (
  -- primary key
  `id`         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,

  -- FK columns
  `parent_category_id` BIGINT UNSIGNED NULL,

  INDEX `idx_Category_parentCategoryId` (`parent_category_id`),

  CONSTRAINT `fk_Product_parentCategoryId`
  FOREIGN KEY (`parent_category_id`) REFERENCES `Category` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,

  -- common
  `version`    BIGINT UNSIGNED DEFAULT '0'        NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
  `deleted_at` datetime,
  `locked`     VARCHAR(4)                         NOT NULL,
  INDEX `idx_Category_createdAt` (`created_at`),
  INDEX `idx_Category_deletedAt` (`deleted_at`),
  INDEX `idx_Category_locked` (`locked`),

  -- columns
  `name`               VARCHAR(255)         NOT NULL,
  `path`               VARCHAR(255)         NOT NULL,
  `display_name`       VARCHAR(255)         NOT NULL,
  `description`        TEXT                 NOT NULL,

  INDEX `idx_Category_path` (`path`),
  CONSTRAINT `uniq_Category_path` UNIQUE (`path`)
);


CREATE TABLE `Image` (
  -- primary key
  `id`         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,

  -- FK columns

  -- common
  `version`    BIGINT UNSIGNED DEFAULT '0'        NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
  `deleted_at` datetime,
  `locked`     VARCHAR(4)                         NOT NULL,
  INDEX `idx_Image_createdAt` (`created_at`),
  INDEX `idx_Image_deletedAt` (`deleted_at`),
  INDEX `idx_Image_locked` (`locked`),

  -- columns
  `name`       VARCHAR(255)         NOT NULL,
  `type`       VARCHAR(255)         NOT NULL,
  `path`       TEXT                 NOT NULL
);

CREATE TABLE `Product` (
  -- primary key
  `id`         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,

  -- FK columns
  `category_id` BIGINT UNSIGNED NOT NULL,
  `image_id`    BIGINT UNSIGNED NULL,

  INDEX `idx_Product_productId` (`category_id`),
  INDEX `idx_Product_imageId` (`image_id`),

  CONSTRAINT `fk_Product_categoryId`
  FOREIGN KEY (`category_id`) REFERENCES `Category` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Product_imageId`
  FOREIGN KEY (`image_id`) REFERENCES `Image` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,

  -- common
  `version`    BIGINT UNSIGNED DEFAULT '0'        NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
  `deleted_at` datetime,
  `locked`     VARCHAR(4)                         NOT NULL,
  INDEX `idx_Product_createdAt` (`created_at`),
  INDEX `idx_Product_deletedAt` (`deleted_at`),
  INDEX `idx_Product_locked` (`locked`),

  -- columns
  `name`        VARCHAR(255)         NOT NULL,
  `price`       BIGINT UNSIGNED      NOT NULL,
  `description` TEXT                 NOT NULL
);

CREATE TABLE `ProductOption` (
  -- primary key
  `id`         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,

  -- FK columns
  `product_id`  BIGINT UNSIGNED NOT NULL,
  `image_id`    BIGINT UNSIGNED NULL,

  INDEX `idx_ProductOption_productId` (`product_id`),
  INDEX `idx_ProductOption_imageId` (`image_id`),

  CONSTRAINT `fk_ProductOption_productId`
  FOREIGN KEY (`product_id`) REFERENCES `Product` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_ProductOption_imageId`
  FOREIGN KEY (`image_id`) REFERENCES `Image` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,

  -- common
  `version`    BIGINT UNSIGNED DEFAULT '0'        NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
  `deleted_at` datetime,
  `locked`     VARCHAR(4)                         NOT NULL,
  INDEX `idx_ProductOption_createdAt` (`created_at`),
  INDEX `idx_ProductOption_deletedAt` (`deleted_at`),
  INDEX `idx_ProductOption_locked` (`locked`),

  -- columns
  `name`        VARCHAR(255)         NOT NULL,
  `price`       BIGINT UNSIGNED      NOT NULL,
  `description` TEXT                 NOT NULL
);
