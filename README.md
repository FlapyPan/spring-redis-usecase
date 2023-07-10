# spring-redis-usecase

Spring Boot 结合 Redis 的一些案例

> 除特殊说明外，所有项目的数据库使用 H2 内存数据库并自动创建表和数据，方便启动和测试
>
> 可以访问`http://localhost:<端口号>/h2-console`来管理对应的数据库

## visit-count

使用 redis 统计总体访问数量，并定时持久化到数据库中

- Redis
- Mybatis
- AspectJ
