CREATE TABLE public."sys_authority"(
   id bigint,
   pid bigint NOT NULL DEFAULT '0',
   authority_name varchar(100) NOT NULL DEFAULT '',
   authority_value varchar(100) NOT NULL DEFAULT '',
   icon varchar(250),
   authority_type tinyint NOT NULL DEFAULT '0',
   web_uri varchar(200),
   bg_uri varchar(200),
   status boolean NOT NULL DEFAULT '1',
   sort tinyint NOT NULL DEFAULT '0',
   create_time date NOT NULL)
TABLESPACE pg_default;

CREATE TABLE public."sys_config"(
   id bigint,
   config_name varchar(200) NOT NULL DEFAULT '',
   config_key varchar(200) NOT NULL DEFAULT '',
   config_value varchar(200) NOT NULL DEFAULT '',
   config_type boolean default '0')
TABLESPACE pg_default;

CREATE TABLE public."sys_dept"(
    id bigint,
    pid bigint NOT NULL DEFAULT '0',
    dept_name varchar(50) DEFAULT '',
    leader varchar(20) DEFAULT '',
    phone varchar(50) DEFAULT '',
    email varchar(50) DEFAULT '',
    status boolean NOT NULL DEFAULT '1',
    sort tinyint NOT NULL DEFAULT '0',
    config_type boolean default '0')
TABLESPACE pg_default;



