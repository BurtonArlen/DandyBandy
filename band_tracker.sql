--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.2
-- Dumped by pg_dump version 9.5.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: bands; Type: TABLE; Schema: public; Owner: arlen
--

CREATE TABLE bands (
    id integer NOT NULL,
    band_name character varying
);


ALTER TABLE bands OWNER TO arlen;

--
-- Name: bands_id_seq; Type: SEQUENCE; Schema: public; Owner: arlen
--

CREATE SEQUENCE bands_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bands_id_seq OWNER TO arlen;

--
-- Name: bands_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: arlen
--

ALTER SEQUENCE bands_id_seq OWNED BY bands.id;


--
-- Name: venues; Type: TABLE; Schema: public; Owner: arlen
--

CREATE TABLE venues (
    id integer NOT NULL,
    venue_name character varying
);


ALTER TABLE venues OWNER TO arlen;

--
-- Name: venues_bands; Type: TABLE; Schema: public; Owner: arlen
--

CREATE TABLE venues_bands (
    id integer NOT NULL,
    venue_id integer,
    band_id integer
);


ALTER TABLE venues_bands OWNER TO arlen;

--
-- Name: venues_bands_id_seq; Type: SEQUENCE; Schema: public; Owner: arlen
--

CREATE SEQUENCE venues_bands_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE venues_bands_id_seq OWNER TO arlen;

--
-- Name: venues_bands_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: arlen
--

ALTER SEQUENCE venues_bands_id_seq OWNED BY venues_bands.id;


--
-- Name: venues_id_seq; Type: SEQUENCE; Schema: public; Owner: arlen
--

CREATE SEQUENCE venues_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE venues_id_seq OWNER TO arlen;

--
-- Name: venues_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: arlen
--

ALTER SEQUENCE venues_id_seq OWNED BY venues.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: arlen
--

ALTER TABLE ONLY bands ALTER COLUMN id SET DEFAULT nextval('bands_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: arlen
--

ALTER TABLE ONLY venues ALTER COLUMN id SET DEFAULT nextval('venues_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: arlen
--

ALTER TABLE ONLY venues_bands ALTER COLUMN id SET DEFAULT nextval('venues_bands_id_seq'::regclass);


--
-- Data for Name: bands; Type: TABLE DATA; Schema: public; Owner: arlen
--

COPY bands (id, band_name) FROM stdin;
8	1970's weedwhacker Metal
9	
7	Mongolian Lute CrustStep
\.


--
-- Name: bands_id_seq; Type: SEQUENCE SET; Schema: public; Owner: arlen
--

SELECT pg_catalog.setval('bands_id_seq', 9, true);


--
-- Data for Name: venues; Type: TABLE DATA; Schema: public; Owner: arlen
--

COPY venues (id, venue_name) FROM stdin;
6	WesternDump
7	trash compactor
8	7-11
\.


--
-- Data for Name: venues_bands; Type: TABLE DATA; Schema: public; Owner: arlen
--

COPY venues_bands (id, venue_id, band_id) FROM stdin;
33	6	7
34	6	8
35	7	8
36	8	7
37	8	8
\.


--
-- Name: venues_bands_id_seq; Type: SEQUENCE SET; Schema: public; Owner: arlen
--

SELECT pg_catalog.setval('venues_bands_id_seq', 37, true);


--
-- Name: venues_id_seq; Type: SEQUENCE SET; Schema: public; Owner: arlen
--

SELECT pg_catalog.setval('venues_id_seq', 8, true);


--
-- Name: bands_pkey; Type: CONSTRAINT; Schema: public; Owner: arlen
--

ALTER TABLE ONLY bands
    ADD CONSTRAINT bands_pkey PRIMARY KEY (id);


--
-- Name: venues_bands_pkey; Type: CONSTRAINT; Schema: public; Owner: arlen
--

ALTER TABLE ONLY venues_bands
    ADD CONSTRAINT venues_bands_pkey PRIMARY KEY (id);


--
-- Name: venues_pkey; Type: CONSTRAINT; Schema: public; Owner: arlen
--

ALTER TABLE ONLY venues
    ADD CONSTRAINT venues_pkey PRIMARY KEY (id);


--
-- Name: public; Type: ACL; Schema: -; Owner: arlen
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM arlen;
GRANT ALL ON SCHEMA public TO arlen;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

