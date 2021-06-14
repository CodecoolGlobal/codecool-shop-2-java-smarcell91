
DROP TABLE IF EXISTS public.products;
CREATE TABLE products (
    id serial NOT NULL,
    name text,
    description text,
    default_price float,
    category_id int,
    supplier_id int
);

DROP TABLE IF EXISTS public.categories;
CREATE TABLE categories (
    id serial NOT NULL,
    name text,
    description text,
    department text
);

DROP TABLE IF EXISTS public.suppliers;
CREATE TABLE suppliers (
    id serial NOT NULL,
    name text,
    description text
);

