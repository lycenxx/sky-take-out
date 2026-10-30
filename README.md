# 苍穹外卖 · 后端服务

基于 Spring Boot 的外卖业务后端，提供管理端与用户端接口，覆盖员工、菜品、套餐、购物车和订单等流程。项目采用 Maven 多模块结构，适合学习接口分层、MyBatis 数据访问与订单业务。

> 当前支付流程包含模拟逻辑，支付回调和 WebSocket 的安全措施尚不完整。本仓库适合本地学习，不能直接用于真实交易或公开部署。

## 功能概览

| 业务 | 当前代码包含的能力 |
| --- | --- |
| 管理端 | 员工、分类、菜品、套餐、店铺营业状态和订单管理 |
| 用户端 | 微信登录、分类及商品浏览、地址簿、购物车、下单和订单查询 |
| 订单处理 | 订单状态更新、超时订单定时处理、催单与 WebSocket 通知 |
| 基础能力 | JWT 身份校验、公共字段填充、Redis 缓存、阿里云 OSS 文件上传、Knife4j 接口文档 |

接口以控制器和服务实现为准；不同业务路径和异常处理可能仍需完善。

## 项目结构

```text
sky-take-out/
├── pom.xml                 # 父工程、模块与依赖版本管理
├── sky-common/             # 通用常量、异常、配置属性、工具和统一响应
├── sky-pojo/               # DTO、实体和 VO
└── sky-server/
    ├── src/main/java/com/sky/
    │   ├── controller/     # admin、user 和支付回调接口
    │   ├── service/        # 业务接口及实现
    │   ├── mapper/         # MyBatis Mapper
    │   ├── interceptor/    # 管理端和用户端 JWT 校验
    │   ├── aspect/         # 数据公共字段填充
    │   ├── task/           # 订单定时任务
    │   └── websocket/      # 订单通知
    ├── src/main/resources/ # 配置与 Mapper XML
    └── src/test/           # 自动化测试
```

典型请求路径为 `Controller → Service → Mapper → MySQL`。认证通过后，拦截器将当前身份写入线程上下文；请求完成时清理。Redis 用于部分缓存与店铺状态，图片上传使用 OSS。

## 技术与环境

- Java 8 及以上；本机验证使用 JDK 17。构建工具为 Maven。
- Spring Boot 2.7.3、Spring MVC、MyBatis、PageHelper、Redis、MySQL。
- Knife4j、Lombok、JWT、WebSocket、阿里云 OSS 和微信相关 SDK。
- 项目仅包含后端代码；管理端页面、小程序和数据库初始化 SQL 不在当前仓库中。

## 本地准备

1. 安装 JDK、Maven、MySQL 和 Redis，并准备与 Mapper 匹配的数据库结构及必要数据。**仓库未提供数据库建表脚本**，仅创建空数据库不能完成启动和业务验证。
2. 检查 `sky-server/src/main/resources/application.yml` 和 `application-dev.yml`。默认启用 `dev` 配置，服务端口为 `8080`。根据本地环境配置 MySQL、Redis；文件上传和微信功能还需要对应的 OSS、微信及支付配置。
3. 将敏感配置放在仓库外的环境变量或本机配置文件中，并确保不会提交。当前仓库的配置中已有密钥字段及字面值，应检查其有效性；若曾使用真实凭据，应在服务商处撤销或轮换。Git 历史中的内容不会因为修改当前文件而消失。
4. 在仓库根目录执行：

```bash
mvn test
mvn package
java -jar sky-server/target/sky-server-1.0-SNAPSHOT.jar
```

也可在 IDEA 中选择 `sky-server` 模块并运行 `com.sky.SkyApplication`。打包和启动需要 Maven 依赖、匹配的数据库结构与相应配置；微信、支付、OSS 等功能还依赖外部服务及有效凭证。

启动后访问 `http://localhost:8080/doc.html` 查看 Knife4j 接口文档。管理端接口通常位于 `/admin/**`，用户端位于 `/user/**`，支付回调位于 `/notify/**`。管理端登录接口为 `POST /admin/employee/login`；用户微信登录接口为 `POST /user/user/login`。

默认配置中，管理端请求通过 `token` 请求头携带 JWT，用户端使用 `authentication` 请求头；登录接口和 `/user/shop/status` 不经过相应登录拦截。请求与响应字段以接口文档和 DTO 为准。

## 测试与注意事项

- `mvn test` 执行 Maven 测试。当前自动化测试覆盖 JWT 拦截器的身份设置、拒绝无效令牌和线程上下文清理；不等于完整业务联调。
- `application-dev.yml` 中的本机路径、地址和第三方配置需要根据实际环境调整。不要将真实密码、密钥或支付证书提交到仓库。
- 现有支付方法直接将订单标记为支付成功；微信支付调用代码处于注释状态。支付回调也未见完整签名验证，不可用于真实支付。
- 订单提交中的事务注解目前被注释；订单一致性、退款、权限边界、并发以及定时任务需要进一步验证。
- WebSocket 当前使用 `/ws/{sid}`，未见连接身份校验；公网部署前需要补充认证、授权和连接管理。
- 默认 JWT 密钥和有效期适用于示例配置，部署前应更换并评估。项目未提供许可证文件，使用和分发前应确认授权。
