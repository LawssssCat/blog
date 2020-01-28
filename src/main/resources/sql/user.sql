use blogdb ; 
create table if not exists b_user (
	id int primary key auto_increment ,
    username varchar(60) ,
    password varchar(100) ,
    salt varchar(60) ,
    nickname varchar(60) ,
    mobile char(13) , 
    qq char(11) , 
    email varchar(30) , 
    avatar_url text ,  
    vaild tinyint(4) , 
    created_time datetime , 
    modified_time datetime 
)  charset utf8; 
desc b_user ; 