CREATE TABLE sys_authority(
    id bigint,
    pid bigint NOT NULL DEFAULT '0',
    type_id bigint NOT NULL DEFAULT '0',
    authority_name varchar(100) NOT NULL,
    authority_value varchar(100) NOT NULL,
    icon varchar(50) DEFAULT '',
    authority_type tinyint NOT NULL DEFAULT '0',
    web_uri varchar(200) DEFAULT '',
    bg_uri varchar(200) DEFAULT '',
    status boolean NOT NULL DEFAULT '1',
    sort tinyint NOT NULL DEFAULT '0',
    create_time date NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE sys_config(
    id bigint,
    config_name varchar(50) NOT NULL,
    config_key varchar(50) NOT NULL UNIQUE,
    config_value varchar(200) NOT NULL,
    config_type boolean NOT NULL default '0',
    PRIMARY KEY (id)
);

CREATE TABLE sys_dept(
    id bigint,
    pid bigint NOT NULL DEFAULT '0',
    dept_ode varchar(50) not null UNIQUE,
    dept_name varchar(50) not null ,
    leader varchar(50) DEFAULT '',
    phone varchar(30) DEFAULT '',
    email varchar(50) DEFAULT '',
    status boolean NOT NULL DEFAULT '1',
    sort tinyint NOT NULL DEFAULT '0',
    dept_type tinyint default '0',
    deleted boolean NOT NULL default '0',
    PRIMARY KEY (id)
);

CREATE TABLE sys_dict_data(
    id bigint,
    type_id bigint not null DEFAULT '0',
    sort tinyint NOT NULL DEFAULT '0',
    label varchar(50),
    data_value varchar(50),
    data_type varchar(50) DEFAULT '',
    is_default boolean NOT NULL DEFAULT '0',
    status boolean NOT NULL default '1',
    deleted boolean NOT NULL default '0',
    PRIMARY KEY (id)
);

CREATE TABLE sys_dict_type(
    id bigint,
    dict_name varchar(50) NOT NULL UNIQUE,
    dict_type varchar(50) DEFAULT '',
    status boolean not null DEFAULT '1',
    deleted boolean NOT NULL default '0',
    PRIMARY KEY (id)
);

CREATE TABLE sys_operate_log(
    id bigint,
    title varchar(50) DEFAULT '',
    business_type tinyint DEFAULT '0',
    method varchar(100) DEFAULT '',
    request_method varchar(10) DEFAULT '',
    operate_type tinyint NOT NULL DEFAULT '1',
    operate_name varchar(50)  DEFAULT '',
    dept_name varchar(50) default '',
    url varchar(200) default '',
    ip varchar(50) default '',
    location varchar(50) default '',
    operate_param varchar(500) default '',
    json_result text default '',
    status boolean not null default '1',
    error_msg boolean default '',
    operate_time date not null,
    PRIMARY KEY (id)
);

CREATE TABLE sys_post(
    id bigint,
    post_code varchar(50) NOT NULL UNIQUE,
    post_name varchar(50) NOT NULL,
    post_sort Integer not null DEFAULT '0',
    status boolean not null DEFAULT '1',
    deleted boolean NOT NULL default '0',
    PRIMARY KEY (id)
);

CREATE TABLE sys_role(
    id bigint,
    role_name varchar(50) NOT NULL UNIQUE,
    description varchar(200) DEFAULT '',
    create_time date not null,
    status boolean not null DEFAULT '1',
    sort Integer NOT NULL DEFAULT '0',
    PRIMARY KEY (id)
);

CREATE TABLE sys_role_authority(
    role_id bigint NOT NULL,
    authority_id bigint NOT NULL,
    PRIMARY KEY (role_id,authority_id)
);

CREATE TABLE sys_user(
    id bigint,
    username varchar(50) NOT NULL UNIQUE,
    password varchar(100) NOT NULL,
    union_id varchar(50) DEFAULT '',
    mp_open_id varchar(50) DEFAULT '',
    mini_open_id varchar(50) DEFAULT '',
    nick_name varchar(50) DEFAULT '',
    icon varchar(200) DEFAULT '',
    email varchar(50) DEFAULT '',
    note varchar(50) DEFAULT '',
    create_time date NOT NULL,
    status boolean NOT NULL DEFAULT '1',
    deleted boolean NOT NULL default '0',
    PRIMARY KEY (id)
);

CREATE TABLE sys_user_role(
    user_id bigint NOT NULL,
    role_id bigint NOT NULL,
    PRIMARY KEY (user_id,role_id)
);

CREATE TABLE sys_user_token(
    id bigint,
    user_id bigint NOT NULL,
    token_id varchar(50) NOT NULL,
    ip varchar(50) DEFAULT '',
    login_type varchar(50) DEFAULT '',
    os varchar(50) DEFAULT '',
    browser varchar(50) DEFAULT '',
    login_time date NOT NULL,
    last_access_time date NOT NULL,
    PRIMARY KEY (id)
);