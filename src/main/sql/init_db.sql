
DROP TABLE IF EXISTS public.products;
CREATE TABLE products (
    PRIMARY KEY id serial NOT NULL,
    name text,
    description text,
    currency text,
    default_price float,
    FOREIGN KEY (category_id) REFERENCES categories(id),
    FOREIGN KEY (supplier_id) REFERENCES suppliers(id)
);

DROP TABLE IF EXISTS public.categories;
CREATE TABLE categories (
    PRIMARY KEY id serial NOT NULL,
    name text,
    description text,
    department text
);

DROP TABLE IF EXISTS public.cart;
CREATE TABLE cart (

)


DROP TABLE IF EXISTS public.suppliers;
CREATE TABLE suppliers (
    PRIMARY KEY id serial NOT NULL,
    name text,
    description text
);

DROP TABLE IF EXISTS public.users;
CREATE TABLE users (
    PRIMARY KEY id serial NOT NULL,
    name text,
    email text,
    pw_hash text
);

DROP TABLE IF EXISTS public.orders;
CREATE TABLE orders (
    PRIMARY KEY id serial NOT NULL,
    date date,
    paid boolean,
    products text
);


