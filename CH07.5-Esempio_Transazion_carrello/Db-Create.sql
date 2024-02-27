
CREATE TABLE "order" (
                         id uuid NOT NULL,
                         status varchar NOT NULL,
                         username varchar NOT NULL,
                         CONSTRAINT order_pk PRIMARY KEY (id)
);


CREATE TABLE product (
                         id uuid NOT NULL,
                         "name" varchar NOT NULL,
                         price numeric NULL,
                         CONSTRAINT product_pk PRIMARY KEY (id)
);


CREATE TABLE cart (
                      seq int4 NOT NULL,
                      username varchar NOT NULL,
                      product_id uuid NULL,
                      quantity numeric NULL,
                      CONSTRAINT cart_pk PRIMARY KEY (seq, username),
                      CONSTRAINT cart_product_fk FOREIGN KEY (product_id) REFERENCES product(id)
);


CREATE TABLE order_info (
                            product_id uuid NOT NULL,
                            order_id uuid NOT NULL,
                            quantity numeric NOT NULL,
                            price numeric NOT NULL,
                            CONSTRAINT order_info_pk PRIMARY KEY (product_id, order_id),
                            CONSTRAINT order_info_order_fk FOREIGN KEY (order_id) REFERENCES "order"(id),
                            CONSTRAINT order_info_product_fk FOREIGN KEY (product_id) REFERENCES product(id)
);


CREATE SEQUENCE public.cart_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
	CACHE 1
	NO CYCLE;