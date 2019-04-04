--
-- PostgreSQL database dump
--

-- Dumped from database version 11.2
-- Dumped by pg_dump version 11.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: addresses; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.addresses (
    address_id integer NOT NULL,
    city character varying NOT NULL,
    street character varying NOT NULL,
    build_number integer NOT NULL,
    customer_id integer
);


ALTER TABLE public.addresses OWNER TO postgres;

--
-- Name: addresses_address_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.addresses_address_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.addresses_address_id_seq OWNER TO postgres;

--
-- Name: addresses_address_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.addresses_address_id_seq OWNED BY public.addresses.address_id;


--
-- Name: customers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.customers (
    customer_id bigint NOT NULL,
    customer_nickname character varying NOT NULL,
    birthday_date date NOT NULL,
    address_id bigint NOT NULL
);


ALTER TABLE public.customers OWNER TO postgres;

--
-- Name: customers_customer_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.customers_customer_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.customers_customer_id_seq OWNER TO postgres;

--
-- Name: customers_customer_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.customers_customer_id_seq OWNED BY public.customers.customer_id;


--
-- Name: items; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.items (
    item_id bigint NOT NULL,
    item_name character varying NOT NULL,
    item_price real NOT NULL
);


ALTER TABLE public.items OWNER TO postgres;

--
-- Name: items_item_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.items_item_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.items_item_id_seq OWNER TO postgres;

--
-- Name: items_item_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.items_item_id_seq OWNED BY public.items.item_id;


--
-- Name: order_items_pending; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.order_items_pending (
    record_id bigint NOT NULL,
    order_id bigint NOT NULL,
    item_id bigint NOT NULL
);


ALTER TABLE public.order_items_pending OWNER TO postgres;

--
-- Name: order_items_pending_record_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.order_items_pending_record_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.order_items_pending_record_id_seq OWNER TO postgres;

--
-- Name: order_items_pending_record_id_seq1; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.order_items_pending_record_id_seq1
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.order_items_pending_record_id_seq1 OWNER TO postgres;

--
-- Name: order_items_pending_record_id_seq1; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.order_items_pending_record_id_seq1 OWNED BY public.order_items_pending.record_id;


--
-- Name: orders; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.orders (
    order_id bigint NOT NULL,
    customer_id bigint NOT NULL,
    item_count integer,
    total_price numeric,
    order_date date NOT NULL
);


ALTER TABLE public.orders OWNER TO postgres;

--
-- Name: orders_order_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.orders_order_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.orders_order_id_seq OWNER TO postgres;

--
-- Name: orders_order_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.orders_order_id_seq OWNED BY public.orders.order_id;


--
-- Name: addresses address_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.addresses ALTER COLUMN address_id SET DEFAULT nextval('public.addresses_address_id_seq'::regclass);


--
-- Name: customers customer_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customers ALTER COLUMN customer_id SET DEFAULT nextval('public.customers_customer_id_seq'::regclass);


--
-- Name: items item_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.items ALTER COLUMN item_id SET DEFAULT nextval('public.items_item_id_seq'::regclass);


--
-- Name: order_items_pending record_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_items_pending ALTER COLUMN record_id SET DEFAULT nextval('public.order_items_pending_record_id_seq1'::regclass);


--
-- Name: orders order_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders ALTER COLUMN order_id SET DEFAULT nextval('public.orders_order_id_seq'::regclass);


--
-- Data for Name: addresses; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.addresses (address_id, city, street, build_number, customer_id) FROM stdin;
20	Mohilew	Lenina	34	32
23	Minsk	Nezaleznasty 4	15	34
25	Brest	Berastyanskaya	9	36
22	Grodno	Serializable	100	33
26	Bobruisk	bi 2	15	37
15	Velo	Dorozka	25	30
13	Mohilew	Ordzenikidze	15	28
24	Minsk	Lopatina	12	35
\.


--
-- Data for Name: customers; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.customers (customer_id, customer_nickname, birthday_date, address_id) FROM stdin;
28	Joe Grooger	1990-03-12	13
30	Carlos Pampillows	1990-03-12	15
36	jajajaguar	1990-03-01	25
32	Jefrie Lebowsky	1988-03-27	20
35	Eroha	1995-10-13	24
37	Me Myself And I	1999-11-06	26
34	Jack Black	1978-03-28	23
33	Alexei Varabei	1975-02-14	22
\.


--
-- Data for Name: items; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.items (item_id, item_name, item_price) FROM stdin;
8	automaton	2000
9	galaxy s10	750
10	mi band 2 	19.2299995
1	kite	32.5
2	baseball ball s	25.4899998
3	baseball ball m	27.4899998
6	skateboard	512.5
7	baseball ball l	32.4899979
\.


--
-- Data for Name: order_items_pending; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.order_items_pending (record_id, order_id, item_id) FROM stdin;
19	3	3
22	3	2
20	3	1
23	6	1
24	6	2
25	6	3
26	6	7
27	6	6
28	7	1
29	7	2
30	7	3
31	7	7
32	7	6
33	8	1
35	8	3
36	8	7
37	8	6
38	8	1
39	8	1
40	8	1
44	3	1
45	3	1
42	3	9
43	3	10
41	8	8
34	8	8
46	3	1
47	3	10
48	3	7
125	8	7
126	8	6
127	8	1
63	3	2
64	3	1
65	3	8
83	8	3
84	8	2
85	8	9
102	3	6
103	3	3
104	3	10
\.


--
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.orders (order_id, customer_id, item_count, total_price, order_date) FROM stdin;
8	32	15	6082.95	2015-12-24
7	30	5	630.47	2019-04-04
6	34	5	630.47	2019-04-04
3	30	16	3621.14	2015-12-24
\.


--
-- Name: addresses_address_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.addresses_address_id_seq', 26, true);


--
-- Name: customers_customer_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.customers_customer_id_seq', 37, true);


--
-- Name: items_item_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.items_item_id_seq', 10, true);


--
-- Name: order_items_pending_record_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.order_items_pending_record_id_seq', 1, false);


--
-- Name: order_items_pending_record_id_seq1; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.order_items_pending_record_id_seq1', 146, true);


--
-- Name: orders_order_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.orders_order_id_seq', 13, true);


--
-- Name: addresses addresses_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.addresses
    ADD CONSTRAINT addresses_pk PRIMARY KEY (address_id);


--
-- Name: customers customers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customers
    ADD CONSTRAINT customers_pkey PRIMARY KEY (customer_id);


--
-- Name: items items_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.items
    ADD CONSTRAINT items_pk PRIMARY KEY (item_id);


--
-- Name: orders new_orders_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT new_orders_pk PRIMARY KEY (order_id);


--
-- Name: order_items_pending order_items_pending_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_items_pending
    ADD CONSTRAINT order_items_pending_pk PRIMARY KEY (record_id);


--
-- Name: addresses_address_id_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX addresses_address_id_uindex ON public.addresses USING btree (address_id);


--
-- Name: addresses_customer_id_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX addresses_customer_id_uindex ON public.addresses USING btree (customer_id);


--
-- Name: customers customers_addresses_address_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customers
    ADD CONSTRAINT customers_addresses_address_id_fk FOREIGN KEY (address_id) REFERENCES public.addresses(address_id);


--
-- Name: order_items_pending order_items_pending_items_item_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_items_pending
    ADD CONSTRAINT order_items_pending_items_item_id_fk FOREIGN KEY (item_id) REFERENCES public.items(item_id);


--
-- Name: order_items_pending order_items_pending_orders_order_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_items_pending
    ADD CONSTRAINT order_items_pending_orders_order_id_fk FOREIGN KEY (order_id) REFERENCES public.orders(order_id);


--
-- Name: orders orders_customers_customer_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_customers_customer_id_fk FOREIGN KEY (customer_id) REFERENCES public.customers(customer_id);


--
-- PostgreSQL database dump complete
--

