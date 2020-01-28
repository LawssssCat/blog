use blogdb ; 
create table if not exists b_blog (
	id int primary key auto_increment ,
    title varchar(200)  , 
    content text , 
    img_url text , 
    flag char(2) , 
    views int  , 
    is_appreciation boolean , 
    is_statement_share boolean , 
    is_commentable boolean , 
    is_recommend boolean , 
    created_user varchar(60) , 
    created_time datetime  , 
    modified_user varchar(60) , 
    modified_time datetime  
)  charset utf8; 
desc b_blog ; 