CREATE TABLE `User` (
  -- primary key
  `id`         BIGINT UNSIGNED                    NOT NULL AUTO_INCREMENT PRIMARY KEY,

  -- common
  `version`    BIGINT UNSIGNED DEFAULT '0'        NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
  `deleted_at` datetime,
  INDEX `idx_User_deletedAt` (`deleted_at`),

  -- columns
  `name`       VARCHAR(255)                       NOT NULL,
  `email`      VARCHAR(255)                       NOT NULL,
  `address`    VARCHAR(255)                       NOT NULL,

  CONSTRAINT uniq_User_email UNIQUE (`email`)
);

CREATE TABLE `AuthIdentity` (
  -- primary key
  `id`         BIGINT                             NOT NULL AUTO_INCREMENT PRIMARY KEY,

  -- common
  `version`    BIGINT UNSIGNED DEFAULT '0'        NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
  `deleted_at` datetime,
  INDEX `idx_AuthIdentity_deletedAt` (`deleted_at`),

  -- columns
  `provider`   VARCHAR(255)                       NOT NULL,
  `username`   VARCHAR(255)                       NOT NULL,
  `password`   VARCHAR(255)                       NULL, -- could be null in case of OAuth Provider
  CONSTRAINT uniq_AuthIdentity_username UNIQUE (`username`),

  -- FK columns
  `user_id`    BIGINT UNSIGNED                    NOT NULL,

  FOREIGN KEY (`user_id`) REFERENCES `User` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
);
