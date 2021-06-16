
DROP TABLE IF EXISTS public.products;
CREATE TABLE products (
    id serial NOT NULL PRIMARY KEY,
    name text,
    description text,
    currency text,
    default_price float,
    FOREIGN KEY (category_id) REFERENCES categories(id),
    FOREIGN KEY (supplier_id) REFERENCES suppliers(id)
);

DROP TABLE IF EXISTS public.categories;
CREATE TABLE categories (
    id serial NOT NULL PRIMARY KEY,
    name text,
    description text,
    department text
);

DROP TABLE IF EXISTS public.cart;
CREATE TABLE cart (
    id serial NOT NULL PRIMARY KEY,
    FOREIGN KEY (user_id) REFERENCES users(id),
    product_id int[]
)


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
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (cart_id) REFERENCES cart(id)
);


