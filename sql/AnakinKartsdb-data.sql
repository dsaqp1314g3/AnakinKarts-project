
insert into users (userpass,email, username, name, phone, ciudad, calle, numero, piso,puerta, cp) values ('david', 'david@anakinkarts.com','david', 'david raja','664567234','Barcelona','Muntaner','170','1','2','08021');
insert into users (userpass,email, username, name, phone, ciudad, calle, numero, piso,puerta, cp) values ('ivan','ivan@anakinkarts.com','ivan', 'ivan frago','648967732','Barcelona','Urgell','55','3','1','08034');
insert into users (userpass,email, username, name, phone, ciudad, calle, numero, piso,puerta, cp) values ('edith','edith@anakinkarts.com','edith', 'edith galmes','602484218','Barcelona','Provença','270','2','2','08032');
<<<<<<< HEAD
insert into users (userpass,email, username, name, phone, ciudad, calle, numero, piso,puerta, cp) values ('pepito','pepito@anakinkarts.com','benito', 'benito camelas','648456432','Castelldefels','diagonal','12','5','1','08860');
insert into users (userpass,email, username, name, phone, ciudad, calle, numero, piso,puerta, cp) values ('admin', 'admin@anakinkarts.com','admin', 'administrador','664567234','Barcelona','Muntaner','170','1','2','08021');
=======
insert into users (userpass,email, username, name, phone, ciudad, calle, numero, piso,puerta, cp) values ('pepito','pepito@anakinkarts.com','benito', 'benito camelas','648456432','Castelldefels','diagonal','12','5','1','08860');
>>>>>>> refs/remotes/origin/dsadevelopers

<<<<<<< HEAD
insert into user_roles values ('david', 'registered');
insert into user_roles values ('ivan', 'registered');
insert into user_roles values ('edith', 'registered');
insert into user_roles values ('admin', 'admin');



=======
>>>>>>> refs/remotes/origin/dsadevelopers
insert into evento (organizador,participantes,fecha,pista,ganador,mejorvuelta,privacidad,fotos) values ('edith', 3,'2014-05-02', 1,'david','134','privado','http://10.189.135.123/imgs/weah');
insert into evento (organizador,participantes,fecha,pista,ganador,mejorvuelta,privacidad,fotos) values ('ivan', 2,'2014-01-12', 1,'ivan','135','privado','http://10.189.135.123/imgs/weah2');
insert into evento (organizador,participantes,fecha,pista,ganador,mejorvuelta,privacidad,fotos) values ('ivan', 2,'2014-03-29', 2,'edith','164','publico','http://10.189.135.123/imgs/weah3');
insert into evento (organizador,participantes,fecha,pista,privacidad, ganador) values ('david', 3,'2014-03-29', 2,'publico','david');

insert into factura (username,precio, eventoid) values ('david',50, '1');
insert into factura (username,precio, eventoid) values ('ivan',55, '2');
insert into factura (username,precio, eventoid) values ('edith',60, '3');

insert into relacion(username, eventoid,invitacion) values ('david', 1,'aceptada');
insert into relacion(username, eventoid,invitacion) values ('edith', 1,'aceptada');
insert into relacion(username, eventoid,invitacion) values ('ivan', 1,'aceptada');
insert into relacion(username, eventoid,invitacion) values ('edith', 2,'aceptada');
insert into relacion(username, eventoid,invitacion) values ('ivan', 2,'aceptada');
insert into relacion(username, eventoid,invitacion) values ('edith', 3,'aceptada');
insert into relacion(username, eventoid,invitacion) values ('ivan', 3,'aceptada');
insert into relacion(username, eventoid,invitacion) values ('david', 4,'aceptada');
insert into relacion(username, eventoid,invitacion) values ('edith', 4,'aceptada');
<<<<<<< HEAD
insert into relacion(username, eventoid,invitacion) values ('benito', 4,'esperando');
=======
insert into relacion(username, eventoid,invitacion) values ('ivan', 4,'esperando');
>>>>>>> refs/remotes/origin/dsadevelopers
