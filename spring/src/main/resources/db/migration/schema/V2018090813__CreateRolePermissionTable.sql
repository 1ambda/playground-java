CREATE TABLE `Role` (
  -- primary key
  `id`          BIGINT UNSIGNED                    NOT NULL AUTO_INCREMENT PRIMARY KEY,

  -- common
  `version`     BIGINT UNSIGNED DEFAULT '0'        NOT NULL,
  `created_at`  datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
  `updated_at`  datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
  `deleted_at`  datetime,
  INDEX `idx_Role_deletedAt` (`deleted_at`),

  -- columns
  `code`        VARCHAR(255)                       NOT NULL,
  `description` VARCHAR(255)                       NOT NULL,
  CONSTRAINT uniq_Role_code UNIQUE (`code`)
);

CREATE TABLE `RoleToUser` (
  -- primary key
  `id`          BIGINT                             NOT NULL AUTO_INCREMENT PRIMARY KEY,

  `role_id`    BIGINT UNSIGNED                    NOT NULL,
  `user_id`    BIGINT UNSIGNED                    NOT NULL,
  INDEX `idx_RoleToUser_roleId` (`role_id`),
  INDEX `idx_RoleToUser_userId` (`user_id`),
  CONSTRAINT uniq_RoleToUser_roleIdAndUserId UNIQUE (`role_id`, `user_id`),

  -- common
  `version`    BIGINT UNSIGNED DEFAULT '0'        NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
  `deleted_at` datetime,
  INDEX `idx_RoleToUser_deletedAt` (`deleted_at`),

  -- columns
  FOREIGN KEY (`role_id`) REFERENCES `Role` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  FOREIGN KEY (`user_id`) REFERENCES `User` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE `Permission` (
  -- primary key
  `id`          BIGINT UNSIGNED                    NOT NULL AUTO_INCREMENT PRIMARY KEY,

  -- common
  `version`     BIGINT UNSIGNED DEFAULT '0'        NOT NULL,
  `created_at`  datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
  `updated_at`  datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
  `deleted_at`  datetime,
  INDEX `idx_Permission_deletedAt` (`deleted_at`),

  -- columns
  `code`        VARCHAR(255)                       NOT NULL,
  `description` VARCHAR(255)                       NOT NULL,
  CONSTRAINT uniq_Permission_code UNIQUE (`code`)
);

CREATE TABLE `PermissionToRole` (
  -- primary key
  `id`          BIGINT UNSIGNED                    NOT NULL AUTO_INCREMENT PRIMARY KEY,

  `permission_id` BIGINT UNSIGNED                    NOT NULL,
  `role_id`       BIGINT UNSIGNED                    NOT NULL,
  INDEX `idx_PermissionToRole_permissionId` (`permission_id`),
  INDEX `idx_PermissionToRole_roleId` (`role_id`),
  CONSTRAINT uniq_PermissionToRole_permissionIdAndRoleId UNIQUE (`permission_id`, `role_id`),

  -- common
  `version`       BIGINT UNSIGNED DEFAULT '0'        NOT NULL,
  `created_at`    datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
  `updated_at`    datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
  `deleted_at`    datetime,
  INDEX `idx_PermissionToRole_deletedAt` (`deleted_at`),

  -- columns
  FOREIGN KEY (`permission_id`) REFERENCES `Permission` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  FOREIGN KEY (`role_id`) REFERENCES `Role` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);
