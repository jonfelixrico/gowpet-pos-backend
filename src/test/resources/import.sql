INSERT INTO app_user (id, username, password) VALUES ('d41df560-b4a1-4fae-8dca-1ce95e3833e4', 'user1', '$2a$10$/FxVvhPKTm0FkuXi6h8NxekWKNTiawo4Szd1YgDLy3T0xCEgSTXEK');

INSERT INTO catalog_item (id, name, price, type, create_by_id, create_dt, update_by_id, update_dt, update_ctr) VALUES ('3e2d537a-3b2a-476d-804b-9ab4c4556cbf', 'Piattos Cheese 85g', 40.00, 'PRODUCT', 'd41df560-b4a1-4fae-8dca-1ce95e3833e4', TIMESTAMP '2023-10-24 23:35:53+08:00', 'd41df560-b4a1-4fae-8dca-1ce95e3833e4', TIMESTAMP '2023-10-24 23:35:53+08:00', 0);
INSERT INTO catalog_item (id, name, price, type, create_by_id, create_dt, update_by_id, update_dt, update_ctr) VALUES ('002a95ff-00b1-48ee-98ce-6469a076d201', 'Miggos Nacho Cheese 105g', 30.00, 'PRODUCT', 'd41df560-b4a1-4fae-8dca-1ce95e3833e4', TIMESTAMP '2023-10-24 23:35:53+08:00', 'd41df560-b4a1-4fae-8dca-1ce95e3833e4', TIMESTAMP '2023-10-24 23:35:53+08:00', 0);

INSERT INTO billing (id, serial_no, create_dt, create_by_id, notes) VALUES ('f03c072c-fb7b-490b-9ab9-2f092b99c755', 1, TIMESTAMP '2023-10-25 23:35:53+08:00', 'd41df560-b4a1-4fae-8dca-1ce95e3833e4', 'Billing 1');
INSERT INTO billing_item (id, catalog_item_id, price, quantity, item_no, billing_id) VALUES ('fb011b0e-9a59-4b55-ad75-62ba61ef318f', '002a95ff-00b1-48ee-98ce-6469a076d201', 30, 5, 0, 'f03c072c-fb7b-490b-9ab9-2f092b99c755');

INSERT INTO billing (id, serial_no, create_dt, create_by_id, notes) VALUES ('60977e99-e1a6-4973-963c-8df377a2b171', 2, TIMESTAMP '2023-10-25 23:35:53+08:00', 'd41df560-b4a1-4fae-8dca-1ce95e3833e4', 'Billing 2');
INSERT INTO billing_item (id, catalog_item_id, price, quantity, item_no, billing_id) VALUES ('6de11535-76f4-413b-aa9a-028ca0891291', '3e2d537a-3b2a-476d-804b-9ab4c4556cbf', 40, 2, 0, '60977e99-e1a6-4973-963c-8df377a2b171');