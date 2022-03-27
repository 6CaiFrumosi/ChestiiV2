drop table orders_lvl_2;
create table orders_lvl_2
(
	madeById number(5, 0) not NULL,
	onName varchar(30) not NULL,
	commandDate date null,
	partCode varchar(30) not NULL,
	quantity integer not NULL,
	phoneNr varchar2(10) NULL,
	email varchar(30) null
);

insert into orders_lvl_2 values(1, 'Alex', (select sysdate from dual), '1', 1, '1', '1');
commit;
select * from orders_lvl_2;