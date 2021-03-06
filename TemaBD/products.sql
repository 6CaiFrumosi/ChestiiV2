drop table emp;
create table emp(
	id number(5, 0) GENERATED by default on null as IDENTITY,
	first_name varchar2(30) not null,
	last_name varchar2(30) not null,
	username varchar2(30) not null
);



alter table emp
add constraint id_pk2 primary key (id);

insert into emp values(1, 'C1', 'Ana', 'Ana');
insert into emp values(2, 'C2', 'Sergiu', 'Sergiu');
insert into emp values(3, 'C3', 'Marius', 'Marius');

commit;

select * from emp;
