---
title: H2 使用
date: 2024-09-14
tag:
  - java
  - jdbc
  - h2
order: 66
---

参考：

- https://springdoc.cn/spring-boot-h2-database/
- http://www.h2database.com/html/main.html

参考：

- https://www.learnfk.com/h2/h2-database-introduction.html
- https://www.w3ccoo.com/h2_database/index.html
- https://www.bookstack.cn/read/h2-database-doc/README.md \
  ~~https://waylau.gitbooks.io/h2-database-doc/content/~~ \
  ~~https://github.com/waylau/h2-database-doc~~ \
  ~~https://github.com/waylau/h2-demos~~

行锁！！！！ http://www.h2database.com/html/features.html#multiple_connections

坑： JSON null —— 在 `Version 2.3.230 (2024-07-15)` 的 mr `#3966` 合入之前， `json null is null` 为 `false`，导致很多判断结果异常。下面为规避方法：（用 `case when ... then ... end`）

```sql
create table B (A int primary key , X json);

insert into B (A, X)
values (1, case when null is null then json '{}' else null end )
    on duplicate key update X = VALUES(X);


insert into B (A, X)
values (1,  '{"x":"b"}' format json)
    on duplicate key update X = case when VALUES(X) = null format json then X else VALUES(X) end;

select * from B;

-- 兼容前后
case when VALUES(X) = null format json or VALUES(x) is null then X else VALUES(X) end
```
