use blogdb ; 
create table if not exists b_comment (
	id int primary key auto_increment ,
    parent_id int ,
    created_user_id int , 
    created_time datetime , 
    content text
)  charset utf8; 
desc b_comment ; 