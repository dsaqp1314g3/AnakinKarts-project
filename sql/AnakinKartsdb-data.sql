insert into users (email, username, name, phone, ciudad, calle, numero, piso,puerta, cp) values ('david@anakinkarts.com','david', 'david','664567234','Barcelona','Muntaner','170','1','2','08021');
insert into users (email, username, name, phone, ciudad, calle, numero, piso,puerta, cp) values ('ivan@anakinkarts.com','ivan', 'ivan','648967732','Barcelona','Urgell','55','3','1','08034');
insert into users (email, username, name, phone, ciudad, calle, numero, piso,puerta, cp) values ('edith@anakinkarts.com','edith', 'edith','602484218','Barcelona','Provença','270','2','2','08032');
insert into users (email, username, name, phone, ciudad, calle, numero, piso,puerta, cp) values ('pepito@anakinkarts.com','pepito', 'pepito','648456432','Castelldefels','diagonal','12','5','1','08860');

insert into evento (eventoid,participantes,fecha,pista,ganador,mejorvuelta,fotos) values (1, 3,'2014-05-02', 1,'david','134','http://10.189.135.123/imgs/weah');
insert into evento (eventoid,participantes,fecha,pista,ganador,mejorvuelta,fotos) values (2, 2,'2014-01-12', 1,'ivan','135','http://10.189.135.123/imgs/weah2');
insert into evento (eventoid,participantes,fecha,pista,ganador,mejorvuelta,fotos) values (3, 2,'2014-03-29', 2,'edith','164','http://10.189.135.123/imgs/weah3');

insert into factura (username,precio, eventoid) values ('david',50, '1');
insert into factura (username,precio, eventoid) values ('ivan',55, '2');
insert into factura (username,precio, eventoid) values ('edith',60, '3');

insert into relacion(username, eventoid) values ('david', 1);
insert into relacion(username, eventoid) values ('edith', 1);
insert into relacion(username, eventoid) values ('ivan', 1);
insert into relacion(username, eventoid) values ('edith', 2);
insert into relacion(username, eventoid) values ('ivan', 2);
insert into relacion(username, eventoid) values ('edith', 3);
insert into relacion(username, eventoid) values ('ivan', 3);