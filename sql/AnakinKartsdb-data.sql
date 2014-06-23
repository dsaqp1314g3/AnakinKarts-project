
insert into users (userpass,email, username, name, phone, ciudad, calle, numero, piso,puerta, cp) values ('david', 'david@anakinkarts.com','david', 'david raja','664567234','Barcelona','Muntaner','170','1','2','08021');
insert into users (userpass,email, username, name, phone, ciudad, calle, numero, piso,puerta, cp) values ('ivan','ivan@anakinkarts.com','ivan', 'ivan frago','648967732','Barcelona','Urgell','55','3','1','08034');
insert into users (userpass,email, username, name, phone, ciudad, calle, numero, piso,puerta, cp) values ('edith','edith@anakinkarts.com','edith', 'edith galmes','602484218','Barcelona','Provença','270','2','2','08032');
insert into users (userpass,email, username, name, phone, ciudad, calle, numero, piso,puerta, cp) values ('pepito','pepito@anakinkarts.com','benito', 'benito camelas','648456432','Castelldefels','diagonal','12','5','1','08860');
insert into users (userpass,email, username, name, phone, ciudad, calle, numero, piso,puerta, cp) values ('admin', 'admin@anakinkarts.com','admin', 'administrador','664567234','Barcelona','Muntaner','170','1','2','08021');

insert into user_roles values ('david', 'registered');
insert into user_roles values ('ivan', 'registered');
insert into user_roles values ('edith', 'registered');
insert into user_roles values ('admin', 'admin');






insert into evento (nombre, organizador,participantes,fecha,pista,ganador,mejorvuelta,privacidad,fotos) values ('Cumple Ana','edith', 3,'2014-05-02', 1,'david','134','privado','http://10.189.135.123/imgs/weah');
insert into evento (nombre, organizador,participantes,fecha,pista,ganador,mejorvuelta,privacidad,fotos) values ('Racing','ivan', 2,'2014-01-12', 1,'ivan','135','privado','http://10.189.135.123/imgs/weah2');
insert into evento (nombre, organizador,participantes,fecha,pista,ganador,mejorvuelta,privacidad,fotos) values ('Despedida Ivan','ivan', 2,'2014-03-29', 2,'edith','164','publico','http://10.189.135.123/imgs/weah3');
insert into evento (nombre, organizador,participantes,fecha,pista,privacidad, ganador) values ('David Wise DJ','david', 3,'2014-03-29', 2,'publico','david');

insert into relacion(username, eventoid,invitacion) values ('david', 1,'aceptada');
insert into relacion(username, eventoid,invitacion) values ('edith', 1,'aceptada');
insert into relacion(username, eventoid,invitacion) values ('ivan', 1,'aceptada');
insert into relacion(username, eventoid,invitacion) values ('edith', 2,'aceptada');
insert into relacion(username, eventoid,invitacion) values ('ivan', 2,'aceptada');
insert into relacion(username, eventoid,invitacion) values ('edith', 3,'aceptada');
insert into relacion(username, eventoid,invitacion) values ('ivan', 3,'aceptada');
insert into relacion(username, eventoid,invitacion) values ('david', 4,'aceptada');
insert into relacion(username, eventoid,invitacion) values ('edith', 4,'aceptada');
insert into relacion(username, eventoid,invitacion) values ('benito', 4,'esperando');


insert into alquiler (organizador,fecha,pista, nplayers) values ('edith','2014-06-09',2,'3');
insert into alquiler (organizador,fecha,pista, nplayers) values ('Tony','2014-06-10',1, '5');

insert into factura (precio,alquilerid) values (50.00,1);
insert into factura (precio,alquilerid) values (45.00,2);


