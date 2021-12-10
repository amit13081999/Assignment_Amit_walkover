drop database exampleapidb;
drop user exampleapi;
create user exampleapi with password 'password';
create database exampleapidb with template=template0 owner=exampleapi;
\connect exampleapidb;
alter default privileges grant all on tables to exampleapi;
alter default privileges grant all on sequences to exampleapi;

create table et_users(
user_id integer primary key not null,
email varchar(30)  not null,
password text not null
);

create sequence et_users_seq increment 1 start 1;