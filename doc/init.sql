CREATE TABLE public."sys_authority"(
                               id bigint,
                               pid bigint NOT NULL DEFAULT '0',
                               name varchar(100) NOT NULL DEFAULT '',
                               value varchar(100) NOT NULL DEFAULT '',
                               icon varchar(250),
                               type tinyint NOT NULL DEFAULT '0',
                               webUri varchar(200),
                               bgUri varchar(200),
                               status boolean NOT NULL DEFAULT '1',
                               sort tinyint NOT NULL DEFAULT '0',
                               create_time date NOT NULL)
TABLESPACE pg_default;

CREATE TABLE public."sys_config"(
                               id bigint,
                               name varchar(200) NOT NULL DEFAULT '',
                               key varchar(200) NOT NULL DEFAULT '',
                               value varchar(200) NOT NULL DEFAULT '',
                               type boolean default '0')
TABLESPACE pg_default;

CREATE TABLE public."sys_dept"(
                                id bigint,
                                pid bigint NOT NULL DEFAULT '0',
                                name varchar(50) DEFAULT '',
                                leader varchar(20) DEFAULT '',
                                phone varchar(50) DEFAULT '',
                                email varchar(50) DEFAULT '',
                                status boolean NOT NULL DEFAULT '1',
                                sort tinyint NOT NULL DEFAULT '0',
                                config_type boolean default '0')
TABLESPACE pg_default;

CREATE TABLE public."sys_dict_data"(
                                  id bigint,
                                  sort bigint NOT NULL DEFAULT '0',
                                  label varchar(50) DEFAULT '',
                                  value varchar(50) DEFAULT '',
                                  type varchar(50) DEFAULT '',
                                  isDefault boolean NOT NULL DEFAULT '0',
                                  status boolean default '0'
                                  deleted boolean default '0'
                              )
    TABLESPACE pg_default;

CREATE TABLE public."sys_dict_type"(
                                       id bigint,
                                       sort bigint NOT NULL DEFAULT '0',
                                       label varchar(50) DEFAULT '',
                                       value varchar(50) DEFAULT '',
                                       type varchar(50) DEFAULT '',
                                       cssClass varchar(50) DEFAULT '',
                                       listClass varchar(50) NOT NULL DEFAULT '1',
                                       isDefault boolean NOT NULL DEFAULT '0',
                                       status boolean default '0'
                                           deleted boolean default '0'
)
    TABLESPACE pg_default;







