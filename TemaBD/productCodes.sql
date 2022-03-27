drop table productcodes;
create table productcodes(
	name varchar2(30) not null,
	code varchar2(30) null
);

insert into productcodes values('antibalans', '11');
insert into productcodes values('semering', '22');
insert into productcodes values('anvelope iarna', '33');
insert into productcodes values('anvelope vara', '44');
insert into productcodes values('senzor', '55');

commit;
select * from productcodes;