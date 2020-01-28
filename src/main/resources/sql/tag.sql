use blogdb ; 
create table if not exists b_tag (
	id int primary key auto_increment ,
    name varchar(200)  
)  charset utf8; 
desc b_tag ; 