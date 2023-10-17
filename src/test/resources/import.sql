INSERT INTO app_user (id, username, password)
VALUES ('d41df560-b4a1-4fae-8dca-1ce95e3833e4', 'user1', 'password');

INSERT INTO app_user (id, username, password)
VALUES ('7a40edee-a86d-4847-9907-af12d1c25f1f', 'user2', 'password');

INSERT INTO catalog_item (id, name, price, type, created_by, create_dt)
VALUES ('94ad00eb-cba0-411f-9d78-29bc1e565518', 'item-1', 120.75, 'PRODUCT', 'd41df560-b4a1-4fae-8dca-1ce95e3833e4', TIMESTAMP '2004-10-19 10:23:54+02');