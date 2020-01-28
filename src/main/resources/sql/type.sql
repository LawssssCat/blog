use blogdb ; 
create table if not exists b_type (
	id int primary key auto_increment ,
    name varchar(200)  
)  charset utf8; 
desc b_type ; 