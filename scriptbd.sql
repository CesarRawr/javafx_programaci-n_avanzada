drop database if exists tienda;
create database tienda;
use tienda;
create table producto(
	id int auto_increment primary key,
	nombre varchar(80) not null,
	precio int not null,
	stock int not null,
	imagen varchar(200),
	type varchar(60)
);

insert into producto values(null, 'Jabon el minino', 15, 10, 'undefined', 'abarrotes');
insert into producto values(null, 'Shampoo pelusa', 24, 16,'undefined', 'abarrotes');
insert into producto values(null, 'Salsa anvorguesita', 29, 15,'undefined', 'abarrotes');
insert into producto values(null, 'Kat', 9999, 0,'undefined', 'abarrotes');

insert into producto values(null, 'Grape Rush', 18, 10, 'undefined', 'bebidas');
insert into producto values(null, 'Pink Lemon', 15, 11, 'undefined', 'bebidas');
insert into producto values(null, 'Soy drink', 1, 12, 'undefined', 'bebidas');
insert into producto values(null, 'Lime Explosion', 21, 13, 'undefined', 'bebidas');

insert into producto values(null, 'Takis Manzana', 10, 5, 'undefined', 'botanas');
insert into producto values(null, 'Rufles Croquetas', 11, 6,'undefined', 'botanas');
insert into producto values(null, 'Mani', 16, 7,'undefined', 'botanas');
insert into producto values(null, 'Cheetos nocheese', 9, 8, 'undefined', 'botanas');

select * from producto;

create table invoice(
	id int auto_increment primary key,
	hora varchar(80) not null
);

create table line (
	id int auto_increment primary key,
	cant int not null,
	total int not null,
	invoice int not null,
	producto int not null,
	FOREIGN KEY (invoice) REFERENCES invoice(id),
	FOREIGN KEY (producto) REFERENCES producto(id)
);

/*
source C:\Users\Cesar\Downloads\pos-master\bd.sql
*/