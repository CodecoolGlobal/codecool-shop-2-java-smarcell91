
DROP TABLE IF EXISTS public.products;
CREATE TABLE products (
    id serial NOT NULL PRIMARY KEY,
    name text,
    description text,
    currency text,
    default_price float,
    category_id int,
    supplier_id int
);

DROP TABLE IF EXISTS public.categories;
CREATE TABLE categories (
    id int,
    name text,
    description text,
    department text
);

DROP TABLE IF EXISTS public.carts;
CREATE TABLE carts (
    id serial NOT NULL PRIMARY KEY,
    user_id int,
    product_id int
);

DROP TABLE IF EXISTS public.billing;
CREATE TABLE billing (
    id serial NOT NULL PRIMARY KEY,
    user_id int,
    country text,
    city text,
    zipcode int,
    address text
);

DROP TABLE IF EXISTS public.shipping;
CREATE TABLE shipping (
    id serial NOT NULL PRIMARY KEY,
    user_id int,
    country text,
    city text,
    zipcode int,
    address text
);

DROP TABLE IF EXISTS public.payment;
CREATE TABLE payment (
      id serial NOT NULL PRIMARY KEY,
      user_id int,
      PP_user text,
      PP_password text,
      card_number text,
      card_holder text,
      card_date text,
      card_code text
);

DROP TABLE IF EXISTS public.suppliers;
CREATE TABLE suppliers (
    id int,
    name text,
    description text
);

DROP TABLE IF EXISTS public.users;
CREATE TABLE users (
    id serial NOT NULL PRIMARY KEY,
    first_name text,
    last_name text,
    email text,
    pw_hash text,
    salt text
);

DROP TABLE IF EXISTS public.orders;
CREATE TABLE orders (
    id serial NOT NULL PRIMARY KEY,
    date date,
    paid boolean,
    user_id int,
    cart_id int
);

--ALTER TABLE products
--ADD CONSTRAINT fk_products_categories
--FOREIGN KEY (category_id)
--REFERENCES categories (id);
--
--ALTER TABLE products
--ADD CONSTRAINT fk_products_suppliers
--FOREIGN KEY (supplier_id)
--REFERENCES suppliers (id);

--ALTER TABLE carts
--ADD CONSTRAINT fk_carts_users
--FOREIGN KEY (user_id)
--REFERENCES users(id);
--
--ALTER TABLE orders
--ADD CONSTRAINT fk_orders_users
--FOREIGN KEY (user_id)
--REFERENCES users(id);
--
--ALTER TABLE orders
--ADD CONSTRAINT fk_orders_carts
--FOREIGN KEY (cart_id)
--REFERENCES carts(id);
--
--ALTER TABLE shipping
--ADD CONSTRAINT fk_shipping_users
--FOREIGN KEY (user_id)
--REFERENCES users(id);
--
--ALTER TABLE billing
--ADD CONSTRAINT fk_billing_users
--FOREIGN KEY (user_id)
--REFERENCES users(id);


INSERT INTO suppliers (id, name, description) 
VALUES
(1, 'Nike', 'Clotes'),
(2, 'Adidas', 'Clotes'),
(3, 'Drink', 'Drink'),
(4, 'Food', 'Food'),
(5, 'Other', 'Others'),
(6, 'Sony', 'Sony and similar');

INSERT INTO categories (id, name, department, description) 
VALUES
(1, 'Clotes', 'wear', 'Jumpers, T-shirts, Slippers, Shoes, ...'),
(2, 'Games', 'Hardware', 'Electronic games and figures'),
(3, 'Foods', 'Foods', 'Foods, drinks and candies');

INSERT INTO products (name, default_price, currency, description, category_id, supplier_id)
VALUES
('Mike Shoes', 49.9, 'USD', 'Fantastic price. Best Mike shoe.', 1, 1), 
('Hike Slippers', 23, 'USD', 'Best slippers for summer', 1, 1), 
('Kine Slippers', 38, 'USD', 'Best new Slippers', 1, 1),
('Nake jumper', 42, 'USD', 'Best jumper today', 1, 1),
('Adcids sport bag', 48, 'USD', 'Best bag for you', 1, 2),
('Adidona soccer shoes', 98, 'USD', 'Best shoes for soccer', 1, 2),
('Johnnie Worker whiskey', 18, 'USD', 'Original whiskey', 3, 3),
('Heimekem beer', 6, 'USD', 'Best beer', 3, 3),
('Olmeo', 3, 'USD', 'Best biscuit', 3, 4),
('Kic Ker', 2, 'USD', 'Best chocolate', 3, 4),
('Poly Station', 76, 'USD', 'Best console', 2, 6),
('FTZA cap', 21, 'USD', 'Best cap in the world', 1, 5),
('Avangers game', 17, 'USD', 'Best avangers game', 2, 5),
('SQMY controller', 35, 'USD', 'sqmy controller', 2, 6),
('Pop Station', 116, 'USD', 'best portable game', 2, 6),
('Nokla phone', 64, 'USD', 'Best phone in the world', 2, 5),
('Harry Potter bag', 23, 'USD', 'Nice bag for kids', 1, 5),
('Game Child', 49, 'USD', 'Nice game for kids', 2, 5),
('Special man', 24, 'USD', 'Nice game for kids', 2, 5);

