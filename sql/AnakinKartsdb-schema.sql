drop database if exists anakinkartsdb;
create database anakinkartsdb;

use anakinkartsdb;

create table users (
	userpass varchar(20) not null,
	email	varchar(40) not null ,
	username varchar(40) not null primary key,
	name	varchar(20) not null,
	phone	int,
	ciudad	varchar(30),
	calle	varchar(70),
	numero	int,
	piso	int,
	puerta 	int,
	cp int

);
 

create table evento (
	nombre varchar(40) not null,
	eventoid int not null auto_increment primary key,
	organizador varchar(40) not null,
	participantes int not null,
	fecha varchar(40) not null,
	pista int not null,
	ganador varchar(40),
	mejorvuelta int,
	privacidad varchar(40) not null,
	fotos 	varchar(200),
	foreign key(organizador) references users(username) 

);

create table relacion (
    
username varchar(40) not null,
eventoid int,
invitacion varchar(20),
foreign key(username) references users(username), 
foreign key(eventoid) references evento(eventoid) 
);

create table user_roles (
	username			varchar(20) not null,
	rolename 			varchar(20) not null,
	foreign key(username) references users(username) on delete cascade,
	primary key (username, rolename)
);

create table alquiler (
	alquilerid int not null auto_increment primary key,
	organizador	varchar(40) not null,
	fecha varchar(40) not null,
	pista int not null,
	nplayers int not null
);

create table factura ( 
facturaid int not null  auto_increment,
precio double not null,
alquilerid int not null,
	primary key (facturaid,alquilerid),
	foreign key(alquilerid) references alquiler(alquilerid) 
);

