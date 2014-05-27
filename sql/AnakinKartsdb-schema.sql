drop database if exists anakinkartsdb;
create database anakinkartsdb;

use anakinkartsdb;

create table users (
	userpass varchar(20) not null,
	email	varchar(40) not null ,
	username varchar(40) not null primary key,
	name	varchar(20) not null,
	phone	int,
	ciudad	varchar(30) not null,
	calle	varchar(70) not null,
	numero	int not null,
	piso	int,
	puerta 	int,
	cp int
	
);
 

create table evento (
	eventoid int not null auto_increment primary key,
	participantes int not null,
	fecha DATE not null,
	pista int,
	ganador varchar(40) not null,
	mejorvuelta int,
	fotos 	varchar(200),
	foreign key(ganador) references users(username) 
	
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

	foreign key(username) references users(username), 
	foreign key(eventoid) references evento(eventoid) 
);

