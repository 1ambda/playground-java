INSERT INTO `Permission` (`id`,
                          `code`,
                          `description`,
                          `created_at`,
                          `updated_at`,
                          `deleted_at`,
                          `version`)
VALUES (1, 'ORDER', 'Order', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null, 0);
INSERT INTO `Permission` (`id`,
                          `code`,
                          `description`,
                          `created_at`,
                          `updated_at`,
                          `deleted_at`,
                          `version`)
VALUES (2, 'STOCK', 'Stock', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null, 0);
INSERT INTO `Permission` (`id`,
                          `code`,
                          `description`,
                          `created_at`,
                          `updated_at`,
                          `deleted_at`,
                          `version`)
VALUES (3, 'PAYMENT', 'Payment', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null, 0);
INSERT INTO `Permission` (`id`,
                          `code`,
                          `description`,
                          `created_at`,
                          `updated_at`,
                          `deleted_at`,
                          `version`)
VALUES (4, 'SETTING', 'Setting', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);
INSERT INTO `Permission` (`id`,
                          `code`,
                          `description`,
                          `created_at`,
                          `updated_at`,
                          `deleted_at`,
                          `version`)
VALUES (5, 'ROLE_INVALID', 'Invalid', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);

INSERT INTO `Role` (`id`,
                    `code`,
                    `description`,
                    `created_at`,
                    `updated_at`,
                    `deleted_at`,
                    `version`)
VALUES (1, 'ROLE_USER', 'User', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null, 0);
INSERT INTO `Role` (`id`,
                    `code`,
                    `description`,
                    `created_at`,
                    `updated_at`,
                    `deleted_at`,
                    `version`)
VALUES (2, 'ROLE_CUSTOMER', 'Customer', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null, 0);
INSERT INTO `Role` (`id`,
                    `code`,
                    `description`,
                    `created_at`,
                    `updated_at`,
                    `deleted_at`,
                    `version`)
VALUES (3, 'ROLE_SELLER', 'Seller', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null, 0);
INSERT INTO `Role` (`id`,
                    `code`,
                    `description`,
                    `created_at`,
                    `updated_at`,
                    `deleted_at`,
                    `version`)
VALUES (4, 'ROLE_ADMIN', 'Admin', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null, 0);
INSERT INTO `Role` (`id`,
                    `code`,
                    `description`,
                    `created_at`,
                    `updated_at`,
                    `deleted_at`,
                    `version`)
VALUES (5, 'ROLE_INVALID', 'Invalid', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);

INSERT INTO `PermissionToRole` (`id`,
                                `permission_id`,
                                `role_id`,
                                `created_at`,
                                `updated_at`,
                                `version`)
VALUES (1, 1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);
INSERT INTO `PermissionToRole` (`id`,
                                `permission_id`,
                                `role_id`,
                                `created_at`,
                                `updated_at`,
                                `version`)
VALUES (2, 3, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);
INSERT INTO `PermissionToRole` (`id`,
                                `permission_id`,
                                `role_id`,
                                `created_at`,
                                `updated_at`,
                                `version`)
VALUES (3, 2, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);
INSERT INTO `PermissionToRole` (`id`,
                                `permission_id`,
                                `role_id`,
                                `created_at`,
                                `updated_at`,
                                `version`)
VALUES (4, 3, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);
INSERT INTO `PermissionToRole` (`id`,
                                `permission_id`,
                                `role_id`,
                                `created_at`,
                                `updated_at`,
                                `version`)
VALUES (5, 1, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);
INSERT INTO `PermissionToRole` (`id`,
                                `permission_id`,
                                `role_id`,
                                `created_at`,
                                `updated_at`,
                                `version`)
VALUES (6, 2, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);
INSERT INTO `PermissionToRole` (`id`,
                                `permission_id`,
                                `role_id`,
                                `created_at`,
                                `updated_at`,
                                `version`)
VALUES (7, 3, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);
INSERT INTO `PermissionToRole` (`id`,
                                `permission_id`,
                                `role_id`,
                                `created_at`,
                                `updated_at`,
                                `version`)
VALUES (8, 4, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);
INSERT INTO `PermissionToRole` (`id`,
                                `permission_id`,
                                `role_id`,
                                `created_at`,
                                `updated_at`,
                                `version`)
VALUES (9, 4, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);
