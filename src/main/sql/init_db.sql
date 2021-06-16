
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
    id serial NOT NULL PRIMARY KEY,
    name text,
    description text,
    department text
);

DROP TABLE IF EXISTS public.carts;
CREATE TABLE carts (
    id serial NOT NULL PRIMARY KEY,
    user_id int,
    product_id int[]
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

DROP TABLE IF EXISTS public.suppliers;
CREATE TABLE suppliers (
    id serial NOT NULL PRIMARY KEY,
    name text,
    description text
);

DROP TABLE IF EXISTS public.users;
CREATE TABLE users (
    id serial NOT NULL PRIMARY KEY,
    name text,
    email text,
    pw_hash text
);

DROP TABLE IF EXISTS public.orders;
CREATE TABLE orders (
    id serial NOT NULL PRIMARY KEY,
    date date,
    paid boolean,
    user_id int,
    cart_id int
);

ALTER TABLE products
ADD CONSTRAINT fk_products_categories
FOREIGN KEY (category_id) 
REFERENCES categories (id);

ALTER TABLE products
ADD CONSTRAINT fk_products_suppliers
FOREIGN KEY (supplier_id) 
REFERENCES suppliers (id);

ALTER TABLE carts
ADD CONSTRAINT fk_carts_users
FOREIGN KEY (user_id)
REFERENCES users(id);

ALTER TABLE orders
ADD CONSTRAINT fk_orders_users
FOREIGN KEY (user_id)
REFERENCES users(id);

ALTER TABLE orders
ADD CONSTRAINT fk_orders_carts
FOREIGN KEY (cart_id)
REFERENCES carts(id);

ALTER TABLE shipping
ADD CONSTRAINT fk_shipping_users
FOREIGN KEY (user_id)
REFERENCES users(id);

ALTER TABLE billing
ADD CONSTRAINT fk_billing_users
FOREIGN KEY (user_id)
REFERENCES users(id);