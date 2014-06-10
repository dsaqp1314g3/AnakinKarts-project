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


create table factura (
    
username varchar(40) not null,
precio int(10) not null,
eventoid int,
	foreign key(username) references users(username), 
	foreign key(eventoid) references evento(eventoid) 
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
