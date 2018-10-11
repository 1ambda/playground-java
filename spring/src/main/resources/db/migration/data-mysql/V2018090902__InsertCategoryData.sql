INSERT INTO `Category` (`id`,
                        `parent_category_id`,
                        `path`,
                        `name`,
                        `display_name`,
                        `description`,
                        `created_at`,
                        `updated_at`,
                        `deleted_at`,
                        `locked`,
                        `version`)
VALUES (1, null, '/other', 'other', 'Other', '',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null, 'N', 0);

INSERT INTO `Category` (`id`,
                        `parent_category_id`,
                        `path`,
                        `name`,
                        `display_name`,
                        `description`,
                        `created_at`,
                        `updated_at`,
                        `deleted_at`,
                        `locked`,
                        `version`)
VALUES (2, null, '/gadget', 'gadget', 'Gadget', '',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null, 'N', 0);

INSERT INTO `Category` (`id`,
                        `parent_category_id`,
                        `path`,
                        `name`,
                        `display_name`,
                        `description`,
                        `created_at`,
                        `updated_at`,
                        `deleted_at`,
                        `locked`,
                        `version`)
VALUES (3, null, '/computer', 'computer', 'Computer', '',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null, 'N', 0);

INSERT INTO `Category` (`id`,
                        `parent_category_id`,
                        `path`,
                        `name`,
                        `display_name`,
                        `description`,
                        `created_at`,
                        `updated_at`,
                        `deleted_at`,
                        `locked`,
                        `version`)
VALUES (4, 3, '/computer/desktop', 'desktop', 'Desktop', '',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null, 'N', 0);

INSERT INTO `Category` (`id`,
                        `parent_category_id`,
                        `path`,
                        `name`,
                        `display_name`,
                        `description`,
                        `created_at`,
                        `updated_at`,
                        `deleted_at`,
                        `locked`,
                        `version`)
VALUES (5, 3, '/computer/laptop', 'laptop', 'Laptop', '',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null, 'N', 0);

INSERT INTO `Category` (`id`,
                        `parent_category_id`,
                        `path`,
                        `name`,
                        `display_name`,
                        `description`,
                        `created_at`,
                        `updated_at`,
                        `deleted_at`,
                        `locked`,
                        `version`)
VALUES (6, 3, '/computer/printer', 'printer', 'Printer', '',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null, 'N', 0);

INSERT INTO `Category` (`id`,
                        `parent_category_id`,
                        `path`,
                        `name`,
                        `display_name`,
                        `description`,
                        `created_at`,
                        `updated_at`,
                        `deleted_at`,
                        `locked`,
                        `version`)
VALUES (7, 3, '/computer/tablet', 'tablet', 'Tablet', '',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null, 'N', 0);

INSERT INTO `Category` (`id`,
                        `parent_category_id`,
                        `path`,
                        `name`,
                        `display_name`,
                        `description`,
                        `created_at`,
                        `updated_at`,
                        `deleted_at`,
                        `locked`,
                        `version`)
VALUES (8, null, '/software', 'software', 'Software', '',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null, 'N', 0);

INSERT INTO `Category` (`id`,
                        `parent_category_id`,
                        `path`,
                        `name`,
                        `display_name`,
                        `description`,
                        `created_at`,
                        `updated_at`,
                        `deleted_at`,
                        `locked`,
                        `version`)
VALUES (9, 8, '/software/os', 'os', 'OS', '',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null, 'N', 0);

INSERT INTO `Category` (`id`,
                        `parent_category_id`,
                        `path`,
                        `name`,
                        `display_name`,
                        `description`,
                        `created_at`,
                        `updated_at`,
                        `deleted_at`,
                        `locked`,
                        `version`)
VALUES (10, 8, '/software/office_application', 'office_application', 'Office Application', '',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null, 'N', 0);

INSERT INTO `Category` (`id`,
                        `parent_category_id`,
                        `path`,
                        `name`,
                        `display_name`,
                        `description`,
                        `created_at`,
                        `updated_at`,
                        `deleted_at`,
                        `locked`,
                        `version`)
VALUES (11, 8, '/software/house_application', 'house_application', 'House Application', '',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null, 'N', 0);

INSERT INTO `Category` (`id`,
                        `parent_category_id`,
                        `path`,
                        `name`,
                        `display_name`,
                        `description`,
                        `created_at`,
                        `updated_at`,
                        `deleted_at`,
                        `locked`,
                        `version`)
VALUES (12, null, '/book', 'book', 'Book', '',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null, 'N', 0);

INSERT INTO `Category` (`id`,
                        `parent_category_id`,
                        `path`,
                        `name`,
                        `display_name`,
                        `description`,
                        `created_at`,
                        `updated_at`,
                        `deleted_at`,
                        `locked`,
                        `version`)
VALUES (13, 12, '/book/computer_science', 'computer_science', 'Computer Science', '',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null, 'N', 0);

INSERT INTO `Category` (`id`,
                        `parent_category_id`,
                        `path`,
                        `name`,
                        `display_name`,
                        `description`,
                        `created_at`,
                        `updated_at`,
                        `deleted_at`,
                        `locked`,
                        `version`)
VALUES (14, 12, '/book/biographical', 'biographical', 'Biographical', '',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null, 'N', 0);

INSERT INTO `Category` (`id`,
                        `parent_category_id`,
                        `path`,
                        `name`,
                        `display_name`,
                        `description`,
                        `created_at`,
                        `updated_at`,
                        `deleted_at`,
                        `locked`,
                        `version`)
VALUES (15, 12, '/book/adventure', 'adventure', 'Adventure', '',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null, 'N', 0);

INSERT INTO `Category` (`id`,
                        `parent_category_id`,
                        `path`,
                        `name`,
                        `display_name`,
                        `description`,
                        `created_at`,
                        `updated_at`,
                        `deleted_at`,
                        `locked`,
                        `version`)
VALUES (16, 12, '/book/science_fiction', 'science_fiction', 'Science Fiction', '',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null, 'N', 0);

INSERT INTO `Category` (`id`,
                        `parent_category_id`,
                        `path`,
                        `name`,
                        `display_name`,
                        `description`,
                        `created_at`,
                        `updated_at`,
                        `deleted_at`,
                        `locked`,
                        `version`)
VALUES (17, 12, '/book/romance', 'romance', 'Romance', '',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null, 'N', 0);

INSERT INTO `Category` (`id`,
                        `parent_category_id`,
                        `path`,
                        `name`,
                        `display_name`,
                        `description`,
                        `created_at`,
                        `updated_at`,
                        `deleted_at`,
                        `locked`,
                        `version`)
VALUES (18, 12, '/book/police', 'police', 'Police', '',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null, 'N', 0);

INSERT INTO `Category` (`id`,
                        `parent_category_id`,
                        `path`,
                        `name`,
                        `display_name`,
                        `description`,
                        `created_at`,
                        `updated_at`,
                        `deleted_at`,
                        `locked`,
                        `version`)
VALUES (19, null, '/food', 'food', 'Food', '',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null, 'N', 0);
