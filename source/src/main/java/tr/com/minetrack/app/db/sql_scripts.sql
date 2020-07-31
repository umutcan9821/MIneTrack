-- Database: minetrack---------------------------------------------

CREATE DATABASE minetrack
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.UTF-8'
    LC_CTYPE = 'en_US.UTF-8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;	

-- Table: account--------------------------------------------------

CREATE TABLE account
(
  user_id serial NOT NULL,
  username character varying(50) NOT NULL,
  password character varying(50) NOT NULL,
  CONSTRAINT account_pkey PRIMARY KEY (user_id),
  CONSTRAINT account_username_key UNIQUE (username)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE account
  OWNER TO postgres;

-- table: employee--------------------------------------------------
    
CREATE TABLE employee
(
  tcno numeric NOT NULL,
  fname character varying(30) NOT NULL,
  lname character varying(30) NOT NULL,
  role character varying(20),
  tagid numeric NOT NULL,
  CONSTRAINT employee_pkey PRIMARY KEY (tcno),
  CONSTRAINT employee_tagid_key UNIQUE (tagid)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE employee
  OWNER TO postgres;

-- Table: license-----------------------------------------------------

CREATE TABLE license
(
  licensekey character varying(12) NOT NULL,
  licensevalue character varying(24) NOT NULL
)
WITH (
  OIDS=FALSE
);
ALTER TABLE license
  OWNER TO postgres;

-- default licensekey = 'aP2dg/Hfp8g='
-- for creating new licensevalue use DesEncrypter class

-- table: machine-----------------------------------------------------

CREATE TABLE machine
(
  mno numeric NOT NULL,
  fname character varying(30) NOT NULL,
  lname character varying(30) NOT NULL,
  role character varying(20),
  tagid numeric NOT NULL,
  CONSTRAINT machine_pkey PRIMARY KEY (mno),
  CONSTRAINT machine_tagid_key UNIQUE (tagid)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE machine
  OWNER TO postgres;

-- Table: signal------------------------------------------------------

CREATE TABLE signal
(
  "time" timestamp without time zone,
  rid numeric,
  tid numeric,
  rssi numeric
)
WITH (
  OIDS=FALSE
);
ALTER TABLE signal
  OWNER TO postgres;

-- Table: signalmap----------------------------------------------------

CREATE TABLE signalmap
(
  pid numeric,
  rid numeric,
  minrssi numeric,
  maxrssi numeric
)
WITH (
  OIDS=FALSE
);
ALTER TABLE signalmap
  OWNER TO postgres;
------------------------------------------------------------------------