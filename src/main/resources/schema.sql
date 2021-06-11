create sequence hibernate_sequence start with 1 increment by 1;
create table account (id bigint not null, birthday date, email varchar(255), firstName varchar(255), lastName varchar(255), login varchar(255), password varchar(255), roleId bigint, primary key (id));
create table Role (id bigint not null, name varchar(255), primary key (id));
alter table account add constraint UKfxcescr6gx8xcswyyswffgvyq unique (id, login, email);
alter table account add constraint FKhuklnn4jnyg12mm82dw0k34gw foreign key (roleId) references Role;

INSERT INTO ROLE (ID, NAME) VALUES (1, 'User');
INSERT INTO ROLE (ID, NAME) VALUES (2, 'Admin');

INSERT INTO ACCOUNT (ID, BIRTHDAY, EMAIL, FIRSTNAME, LASTNAME, LOGIN, PASSWORD, ROLEID) VALUES (1, '2013-06-15', 'user@ad.com', 'user', 'user', 'user', 'user', 1);
INSERT INTO ACCOUNT (ID, BIRTHDAY, EMAIL, FIRSTNAME, LASTNAME, LOGIN, PASSWORD, ROLEID) VALUES (2, '2009-06-13', 'admin@fl.com', 'admin', 'admin', 'admin', 'admin', 2)