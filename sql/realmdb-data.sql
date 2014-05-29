source realmdb-schema.sql;

insert into users values('david', MD5('david'), 'David', 'david@anakinkarts.com');
insert into user_roles values ('david', 'registered');

insert into users values('ivan', MD5('ivan'), 'Ivan', 'ivan@anakinkarts.com');
insert into user_roles values ('ivan', 'registered');

insert into users values('edith', MD5('edith'), 'Edith', 'edith@anakinkarts.com');
insert into user_roles values ('edith', 'registered');

insert into users values('admin', MD5('admin'), 'Administrador', 'admin@anakinkarts.com');
insert into user_roles values ('admin', 'admin');