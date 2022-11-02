CREATE TABLE public."sys_authority"(
    id bigint,
    pid bigint NOT NULL DEFAULT '0',
    typeId bigint NOT NULL DEFAULT '0',
    authorityName varchar(100) NOT NULL,
    authorityValue varchar(100) NOT NULL,
    icon varchar(50) DEFAULT '',
    authorityType tinyint NOT NULL DEFAULT '0',
    webUri varchar(200) DEFAULT '',
    bgUri varchar(200) DEFAULT '',
    status boolean NOT NULL DEFAULT '1',
    sort tinyint NOT NULL DEFAULT '0',
    createTime date NOT NULL
)

CREATE TABLE public."sys_config"(
    id bigint,
    configName varchar(50) NOT NULL,
    configKey varchar(50) NOT NULL,
    configValue varchar(200) NOT NULL,
    configType boolean NOT NULL default '0'
)

CREATE TABLE public."sys_dept"(
    id bigint,
    pid bigint NOT NULL DEFAULT '0',
    deptName varchar(50) not null ,
    leader varchar(50) DEFAULT '',
    phone varchar(30) DEFAULT '',
    email varchar(50) DEFAULT '',
    status boolean NOT NULL DEFAULT '1',
    sort tinyint NOT NULL DEFAULT '0',
    deptType tinyint default '0',
    deleted boolean NOT NULL default '0'
)

CREATE TABLE public."sys_dict_data"(
    id bigint,
    typeId bigint not null DEFAULT '0',
    sort tinyint NOT NULL DEFAULT '0',
    label varchar(50),
    dataValue varchar(50),
    dataType varchar(50) DEFAULT '',
    isDefault boolean NOT NULL DEFAULT '0',
    status boolean NOT NULL default '1',
    deleted boolean NOT NULL default '0'
)

CREATE TABLE public."sys_dict_type"(
    id bigint,
    dictName varchar(50) NOT NULL,
    dictType varchar(50) DEFAULT '',
    status boolean not null DEFAULT '1',
    deleted boolean NOT NULL default '0'
)

CREATE TABLE public."sys_operate_log"(
    id bigint,
    title varchar(50) DEFAULT '',
    businessType tinyint DEFAULT '0',
    method varchar(100) DEFAULT '',
    requestMethod varchar(10) DEFAULT '',
    operatorType tinyint NOT NULL DEFAULT '1',
    operateName varchar(50)  DEFAULT '',
    deptName varchar(50) default '',
    url varchar(200) default '',
    ip varchar(50) default '',
    location varchar(50) default '',
    operateParam varchar(500) default '',
    jsonResult text default '',
    status boolean not null default '1',
    errorMsg boolean default '',
    operateTime date not null
)

CREATE TABLE public."sys_post"(
    id bigint,
    postCode varchar(50) NOT NULL,
    postName varchar(50) NOT NULL,
    postSort Integer not null DEFAULT '0',
    status boolean not null DEFAULT '1',
    deleted boolean NOT NULL default '0'
)

CREATE TABLE public."sys_role"(
    id bigint,
    roleName varchar(50) NOT NULL,
    description varchar(200) DEFAULT '',
    createTime date not null,
    status boolean not null DEFAULT '1',
    sort Integer NOT NULL DEFAULT '0',
)

CREATE TABLE public."sys_role_authority"(
    roleId bigint NOT NULL,
    authorityId bigint NOT NULL
)
CREATE TABLE public."sys_user"(
    id bigint,
    username varchar(50) NOT NULL,
    password varchar(100) NOT NULL,
    unionId varchar(50) DEFAULT '',
    mpOpenId varchar(50) DEFAULT '',
    miniOpenId varchar(50) DEFAULT '',
    icon boolean DEFAULT '',
    email boolean DEFAULT '',
    nickName varchar(50) DEFAULT '',
    note varchar(50) DEFAULT '',
    createTime date NOT NULL,
    status boolean NOT NULL DEFAULT '1',
    deleted boolean NOT NULL default '0'
)
CREATE TABLE public."sys_user_role"(
    userId bigint NOT NULL,
    roleId bigint NOT NULL
)
CREATE TABLE public."sys_user_token"(
    id bigint,
    userId bigint NOT NULL,
    tokenId varchar(50) NOT NULL,
    ip varchar(50) DEFAULT '',
    loginType varchar(50) DEFAULT '',
    os varchar(50) DEFAULT '',
    browser varchar(50) DEFAULT '',
    loginTime date NOT NULL,
    lastAccessTime date NOT NULL
)






