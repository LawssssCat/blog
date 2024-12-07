create table table_emp (
    emp_id     int          not null auto_increment,
    emp_name   varchar(500) null,
    emp_salary double       null,
    emp_age    int          null,
    primary key (emp_id)
);

insert into table_emp (emp_name, emp_salary, emp_age) values ('tom', 1234.56, 27);
insert into table_emp (emp_name, emp_salary, emp_age) values ('jerry', 5536.42, 69);
insert into table_emp (emp_name, emp_salary, emp_age) values ('bob', 4464.56, 22);
insert into table_emp (emp_name, emp_salary, emp_age) values ('justin', 1234.56, 18);
