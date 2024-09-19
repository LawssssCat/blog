DELETE FROM employees;

INSERT INTO employees (id, first_name, last_name, age, email_address) VALUES
    (1, 'Jone', 'han', 18, 'test1@baomidou.com'),
    (2, 'Jack', 'han', 18, 'test2@baomidou.com'),
    (3, 'Tom', 'han', 18, 'test3@baomidou.com'),
    (4, 'Sandy', 'han', 18, 'test4@baomidou.com'),
    (5, 'Billie', 'han', 18, 'test5@baomidou.com');

insert into events (id, param) values
    (1, JSON '{"name":"alpha", "type": ["a1", "a2"]}'),
    (2, JSON '{"name":"beta", "type": ["b1", "b2"]}');
