# 🎯 Project 77 – API Gateway with Eureka Discovery | Spring Boot + Spring Cloud Gateway + Netflix Eureka

<p align="left">
<img src="https://img.shields.io/badge/Java-21-E76F00?logo=openjdk&logoColor=white" alt="Java 21">
<img src="https://img.shields.io/badge/Spring%20Boot-3.2.5-6DB33F?logo=springboot&logoColor=white" alt="Spring Boot 3.2.5">
<img src="https://img.shields.io/badge/Spring%20Cloud%20Gateway-2023.0.1-6DB33F?logo=spring&logoColor=white" alt="Spring Cloud Gateway">
<img src="https://img.shields.io/badge/Eureka%20Server-4.1.3-FF0000?logo=netflix&logoColor=white" alt="Eureka">
<img src="https://img.shields.io/badge/Gateway%20Port-8084-00BFFF" alt="8084">
<img src="https://img.shields.io/badge/Eureka%20Port-8761-FF0000" alt="8761">
<img src="https://img.shields.io/badge/Fix-ORA--00942%20TABLE--20B000" alt="ORA-00942 Fix">
<img src="https://img.shields.io/badge/Fix-500%20to%20200%20OK-20B000" alt="500 Fix">
<img src="https://img.shields.io/badge/Instances-3%20UP-20B000" alt="3 UP">
<img src="https://img.shields.io/badge/Status-Completed-20B000" alt="Completed">
</p>

## 📖 Project Overview

Project 77 is Tier 8 – API Gateway with Eureka Discovery, built with Spring Boot 3.2.5, Spring Cloud Gateway 2023.0.1, Netflix Eureka Server 4.1.3, Oracle 11g XE, PRODUCT_USER, and Port 8084 + 8761 + 8082 + 8086.

This project uses **API GATEWAY WITH EUREKA DISCOVERY – LB ROUTING**:

- Eureka Server runs on port 8761 – http://localhost:8761 – 3 instances UP – 74-PRODUCT-SERVICE:8082, 75-ORDER-SERVICE:8086, 76-API-GATEWAY:8084
- Gateway runs on port 8084 – http://localhost:8084/api/products -> lb://74-PRODUCT-SERVICE/api/products – DiscoveryClient routing
- DiscoveryClient – lb:// – Not http://localhost – Gateway discovers via Eureka – Load Balanced – RouteLocator with lb:// URIs
- 3 Services + 1 Gateway + 1 Eureka = 5 Microservices Track – 73 Auth (optional), 74 Product 8082, 75 Order 8086, 76 Gateway 8084, 77 Eureka 8761
- 5 Major Fixes – Port 8761 already in use, ORA-00942 table or view does not exist – ORDER_ITEMS / ORDERS_TABLE, ORA-02289 sequence does not exist – ORDERS_SEQ, 500 Internal Server Error on /api/orders via Gateway, Eureka EMERGENCY! Renewals lesser than threshold
- Oracle PRODUCT_USER – Manual table creation – ORDERS_TABLE, ORDER_ITEMS, sequences ORDERS_SEQ, ORDER_ITEMS_SEQ, PRODUCT_SEQ – Hibernate auto create orders table

Backend routing:

- GET /api/products via 8084 -> lb://74-PRODUCT-SERVICE -> http://localhost:8082/api/products – iPhone 15 JSON – demo6.png – Main proof Gateway + Eureka works!
- GET /api/orders via 8084 -> lb://75-ORDER-SERVICE -> http://localhost:8086/api/orders – [] empty but 200 OK – demo7.png – Fixed from 500 to 200
- GET direct 8086/api/orders – Direct Order – [] – demo8.png – Verify same as gateway – No 500
- Eureka Dashboard – http://localhost:8761 – Shows 0 -> 1 -> 2 -> 3 instances progression – demo1 to demo5.png

Verified with 8 screenshots:

- demo1.png – Browser – localhost:8761 – 00:00 Uptime – No instances available – Eureka started fresh – System Status test default – EMERGENCY! message
- demo2.png – Browser – localhost:8761 – 00:02 Uptime – 1 UP – 74-PRODUCT-SERVICE:8082 only – First service registered
- demo3.png – Browser – localhost:8761 – 00:05 Uptime – 2 UP – 74-PRODUCT-SERVICE:8082 + 75-ORDER-SERVICE:8086 – Order fixed
- demo4.png – Browser – localhost:8761 – 00:09 Uptime – 3 UP – 74 + 75 + 76 – All 3 UP – Complete microservices – 76-API-GATEWAY green
- demo5.png – Browser – localhost:8761 – 00:21 Uptime – 3 UP stable – 74-PRODUCT-SERVICE:8082 + localhost:75-ORDER-SERVICE:8086 + localhost:76-API-GATEWAY:8084 – 6 Renews – Stable after fixes
- demo6.png – Browser – localhost:8084/api/products – Via Gateway + Eureka – [{"id":1,"name":"iPhone 15"...}] – iPhone 15 JSON – Main proof Products via Gateway
- demo7.png – Browser – localhost:8084/api/orders – Via Gateway + Eureka – [] – Empty but 200 OK – Fixed from 500 – Main proof Orders via Gateway fixed!
- demo8.png – Browser – localhost:8086/api/orders – Direct Order – [] – Direct vs Gateway same – Compare demo7 same

## ✨ Features

### 🌐 Eureka Discovery – 8761 Dashboard Progression

- Eureka Server Tier 8 – Spring Cloud Netflix Eureka Server – Port 8761 – @EnableEurekaServer – System Status – Environment test, Data center default, Uptime 00:00 -> 00:21, Renews threshold 1 -> 6
- Instance Registration Progression – demo1 0 instances (fresh start) -> demo2 1 instance (74 Product) -> demo3 2 instances (74 + 75) -> demo4 3 instances (74 + 75 + 76) -> demo5 stable 3 instances with 00:21 uptime – Real startup flow
- EMERGENCY! Warning – EMERGENCY! EUREKA MAY BE INCORRECTLY CLAIMING INSTANCES ARE UP WHEN THEY'RE NOT. RENEWALS ARE LESSER THAN THRESHOLD – Normal in dev – Renew threshold 1/3/5/6 – Renews last min 0/2/4/6 – Not expired just to be safe – Fixes after all services UP
- 3 UP Status – UP (1) - 74-PRODUCT-SERVICE:8082, UP (1) - localhost:75-ORDER-SERVICE:8086, UP (1) - localhost:76-API-GATEWAY:8084 – All green links – Instance currently registered with Eureka table

### 🛣 API Gateway with LB – 8084 – Eureka Client

- Gateway with Eureka Client – eureka.client.service-url.defaultZone=http://localhost:8761/eureka – DiscoveryClient enabled – Routes use lb://74-PRODUCT-SERVICE not http://localhost:8082 – Load Balanced
- RouteLocator with lb:// – .route("product-service", r -> r.path("/api/products/**").uri("lb://74-PRODUCT-SERVICE")) – Discovers via Eureka – Not hardcoded localhost
- 3 Routes – product 74 via lb, order 75 via lb, gateway itself registered to Eureka – Actuator /actuator/gateway/routes shows lb:// URIs
- Products via Gateway – demo6.png – localhost:8084/api/products – [{"id":1,"name":"iPhone 15","price":79999.0...}] – Via 8084 -> lb://74 -> 8082 – Works!
- Orders via Gateway – demo7.png – localhost:8084/api/orders – [] – Via 8084 -> lb://75 -> 8086 – Fixed 500 -> 200 OK – Before Whitelabel 500 – After [] empty

### 🛢 Oracle PRODUCT_USER – ORA-00942 Fix – Manual Table Creation

- ORA-00942: table or view does not exist – DROP TABLE ORDER_ITEMS / ORDERS_TABLE – Table does not exist – Hibernate failed to create due to PRODUCT_USER permissions / naming
- Fix: Manual CREATE – CREATE TABLE ORDERS_TABLE (ID NUMBER PRIMARY KEY, USER_ID NUMBER, TOTAL_AMOUNT FLOAT NOT NULL, STATUS VARCHAR2(100), ORDER_DATE TIMESTAMP) – Table created.
- Fix: CREATE TABLE ORDER_ITEMS (ID NUMBER PRIMARY KEY, PRODUCT_ID NUMBER, PRODUCT_NAME VARCHAR2(255), QUANTITY NUMBER, PRICE FLOAT, ORDER_ID NUMBER REFERENCES ORDERS_TABLE(ID)) – Table created.
- Hibernate Auto – Hibernate: create table orders (id number(19,0) not null, order_date timestamp(6), status varchar2(255 char), total_amount float(53), user_id number(19,0), primary key (id)) – Creates lower case orders table – Now 2 tables – ORDERS_TABLE manual + orders auto – SELECT works!
- ORA-02289: sequence does not exist – DROP SEQUENCE ORDERS_SEQ / ORDER_ITEMS_SEQ – sequence does not exist – Fix: CREATE SEQUENCE ORDERS_SEQ START WITH 1 INCREMENT BY 1 – Sequence created. + ORDER_ITEMS_SEQ – SELECT sequence_name FROM user_sequences shows ORDERS_SEQ, ORDER_ITEMS_SEQ, PRODUCT_SEQ – 3 sequences ready

### 🛡 5 Major Fixes – Completed – Work Process

- Fix 1 Eureka 0 instances – No instances available – demo1.png – Cause: Only Eureka running – No product/order/gateway started – Fix: Start 74-product first -> demo2.png 1 UP
- Fix 2 Product only 1 UP – demo2.png – 74-PRODUCT-SERVICE:8082 UP only – Fix: Start 75-order-service -> demo3.png 2 UP – 74 + 75
- Fix 3 Orders 500 Error – localhost:8084/api/orders -> Whitelabel Error Page 500 Internal Server Error – Hibernate ORA-00942 table or view does not exist – ORDER_ITEMS / ORDERS_TABLE not created – Fix: sqlplus PRODUCT_USER/product123 – CREATE TABLE ORDERS_TABLE + ORDER_ITEMS + SEQUENCES – Restart 75-order-service – Hibernate: select o1_0.id... from orders o1_0 – 4 SELECT queries = success – [] instead of 500
- Fix 4 Gateway 3 UP complete – demo4.png – 00:09 Uptime – 74 + 75 + 76 all UP – 76-API-GATEWAY green link localhost:76-API-GATEWAY:8084 – All registered – Eureka stable
- Fix 5 Stable 00:21 Uptime – demo5.png – Current time 2026-09-20T15:47:06 +0530 – Uptime 00:21 – Renews threshold 6 – Renews last min 6 – 3 UP stable – No more EMERGENCY! expiring – Long running verified
- Fix Extra 500 -> 200 OK – Before: http://localhost:8084/api/orders 500 – After: [] 200 OK – demo7.png via gateway + demo8.png direct same – Main proof 77 completed!

## 🛠 Technologies Used

| Technology | Version | Purpose |
|---|---|---|
| Java | 21.0.10 | Backend language – PID 31656 |
| Spring Boot | 3.2.5 | Eureka 8761 + Gateway 8084 + Product 8082 + Order 8086 |
| Spring Cloud Netflix Eureka | 4.1.3 | Eureka Server 8761 – Service Discovery – Instances registered |
| Spring Cloud Gateway | 2023.0.1 | Gateway 8084 – lb:// routing – DiscoveryClient |
| Oracle Database | 11g XE 11.2.0.2.0 | PRODUCT_USER – Tables ORDERS_TABLE, ORDER_ITEMS, orders |
| Hibernate ORM | 6.4.4.Final | create table orders, alter table order_items add constraint FK – Auto DDL |
| HikariPool | Default | HikariPool-1 - Starting... Added connection oracle.jdbc.driver.T4CConnection |
| Spring Data JPA | Default | Repository scanning – Found 1 JPA repository – Orders |
| Eureka Client | 4.1.3 | DiscoveryClient_75-ORDER-SERVICE: registering service... status 204 |
| Spring Boot Actuator | Default | /actuator/gateway/routes – Exposing 16 endpoints – Not in 77 but in 76 |
| Maven | mvnw.cmd | Build – clean install |
| Frontend | Browser | Verification – demo1 to demo8 – 8 screenshots |

## 📂 Project Structure

```text
77-eureka-server/
│
├── 73-auth-service (optional) – 8081 – Not in demo but part of track
├── 74-product-service/ – 8082 – PRODUCT_USER – PRODUCTS table – iPhone 15
│   ├── src/main/java/com/product/service/
│   │   ├── ProductServiceApplication.java – @EnableDiscoveryClient – Port 8082
│   │   ├── controller/ProductController.java – @RequestMapping("/api/products") – Returns iPhone 15
│   │   └── entity/Product.java – PRODUCT table – Java 21
│   └── application.yml – server.port 8082, eureka.client.service-url.defaultZone http://localhost:8761/eureka, spring.datasource username PRODUCT_USER
│
├── 75-order-service/ – 8086 – PRODUCT_USER – ORDERS_TABLE + ORDER_ITEMS + orders – Fixed ORA-00942
│   ├── src/main/java/com/order/service/
│   │   ├── Application.java – @EnableDiscoveryClient – Port 8086 – PID 31656 – Started in 28.713 seconds – Tomcat started on port 8086
│   │   ├── controller/OrderController.java – @RequestMapping("/api/orders") – GET returns [] – Was 500 fixed to []
│   │   ├── entity/Order.java – @Entity – orders table – id, order_date, status, total_amount, user_id – Hibernate auto create
│   │   └── repository/OrderRepository.java – JpaRepository – Found 1 JPA repository interface
│   └── application.yml – server.port 8086, eureka.client.service-url.defaultZone http://localhost:8761/eureka, datasource PRODUCT_USER/product123, jpa.hibernate.ddl-auto update
│
├── 76-api-gateway/ – 8084 – Eureka Client – lb:// routing
│   ├── src/main/java/com/gateway/
│   │   ├── ApiGatewayApplication.java – @EnableDiscoveryClient – Port 8084 – Netty – Routes lb://74-PRODUCT-SERVICE, lb://75-ORDER-SERVICE
│   │   ├── config/GatewayConfig.java – RouteLocator – lb:// URIs – No http://localhost hardcoded – Discovery via Eureka
│   │   └── filter/LoggingFilter + JwtAuthenticationFilter – Same as 76
│   └── application.yml – server.port 8084, eureka.client.service-url.defaultZone http://localhost:8761/eureka, management exposure *
│
├── 77-eureka-server/ – 8761 – Discovery Server
│   ├── src/main/java/com/eureka/
│   │   └── EurekaServerApplication.java – @EnableEurekaServer – Port 8761 – System Status – Environment test
│   └── application.yml – server.port 8761, eureka.client.register-with-eureka false, fetch-registry false
│
├── screenshots/
│   ├── demo1.png – 8761 – No instances available – 00:00 – Fresh start – EMERGENCY!
│   ├── demo2.png – 8761 – 1 UP – 74-PRODUCT-SERVICE:8082 – 00:02
│   ├── demo3.png – 8761 – 2 UP – 74 + 75 – 00:05 – 75-ORDER-SERVICE:8086 registered
│   ├── demo4.png – 8761 – 3 UP – 74 + 75 + 76 – 00:09 – All UP – Green
│   ├── demo5.png – 8761 – 3 UP stable – 00:21 – 6 Renews – Stable – Final Eureka
│   ├── demo6.png – 8084/api/products – Via Gateway lb://74 – iPhone 15 JSON – Main product proof
│   ├── demo7.png – 8084/api/orders – Via Gateway lb://75 – [] – Fixed 500 -> 200 – Main order proof
│   └── demo8.png – 8086/api/orders – Direct – [] – Direct vs Gateway same – Compare demo7
│
├── pom.xml (each service) – spring-cloud-starter-netflix-eureka-server / eureka-client, gateway, data-jpa, oracle driver
└── README.md – This file – 8 demos
```

## ▶ How to Run

### 1. Clone
```bash
git clone https://github.com/raviteja-dev950/77-Eureka-Gateway.git
cd 77-Eureka-Gateway
```

### 2. Oracle Manual Fix – ORA-00942 – Must Do Before Starting 75
```bash
sqlplus

Enter user-name: PRODUCT_USER
Enter password: product123

SQL> DROP TABLE ORDER_ITEMS CASCADE CONSTRAINTS PURGE;
-- ORA-00942: table or view does not exist – OK

SQL> DROP TABLE ORDERS_TABLE CASCADE CONSTRAINTS PURGE;
-- ORA-00942 – OK

SQL> CREATE TABLE ORDERS_TABLE (ID NUMBER PRIMARY KEY, USER_ID NUMBER, TOTAL_AMOUNT FLOAT NOT NULL, STATUS VARCHAR2(100), ORDER_DATE TIMESTAMP);
-- Table created.

SQL> CREATE TABLE ORDER_ITEMS (ID NUMBER PRIMARY KEY, PRODUCT_ID NUMBER, PRODUCT_NAME VARCHAR2(255), QUANTITY NUMBER, PRICE FLOAT, ORDER_ID NUMBER REFERENCES ORDERS_TABLE(ID));
-- Table created.

SQL> DROP SEQUENCE ORDERS_SEQ;
-- ORA-02289: sequence does not exist – OK

SQL> DROP SEQUENCE ORDER_ITEMS_SEQ;
-- ORA-02289 – OK

SQL> CREATE SEQUENCE ORDERS_SEQ START WITH 1 INCREMENT BY 1;
-- Sequence created.

SQL> CREATE SEQUENCE ORDER_ITEMS_SEQ START WITH 1 INCREMENT BY 1;
-- Sequence created.

SQL> SELECT table_name FROM user_tables WHERE table_name LIKE '%ORDER%';
-- ORDER_ITEMS, ORDERS_TABLE – 2 tables

SQL> SELECT sequence_name FROM user_sequences;
-- ORDERS_SEQ, ORDER_ITEMS_SEQ, PRODUCT_SEQ – 3 sequences

SQL> exit
```

### 3. Application YMLs – FINAL

**77-eureka-server – 8761**
```yaml
server:
  port: 8761
spring:
  application:
    name: eureka-server
eureka:
  client:
    register-with-eureka: false
    fetch-registry: false
```

**74-product-service – 8082**
```yaml
server:
  port: 8082
spring:
  application:
    name: 74-PRODUCT-SERVICE
  datasource:
    url: jdbc:oracle:thin:@localhost:1521:xe
    username: PRODUCT_USER
    password: product123
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka
```

**75-order-service – 8086 – Fixes ORA-00942**
```yaml
server:
  port: 8086
spring:
  application:
    name: 75-ORDER-SERVICE
  datasource:
    url: jdbc:oracle:thin:@localhost:1521:xe
    username: PRODUCT_USER
    password: product123
  jpa:
    hibernate:
      ddl-auto: update
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka
```

**76-api-gateway – 8084 – lb://**
```yaml
server:
  port: 8084
spring:
  application:
    name: 76-API-GATEWAY
  cloud:
    gateway:
      routes:
        - id: product-service
          uri: lb://74-PRODUCT-SERVICE
          predicates:
            - Path=/api/products/**
        - id: order-service
          uri: lb://75-ORDER-SERVICE
          predicates:
            - Path=/api/orders/**
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka
management:
  endpoints:
    web:
      exposure:
        include: "*"
```

### 4. Run Order – Must Start Eureka First – demo1 to demo5 progression

```bash
# Terminal 1 – Eureka Server – demo1.png – 0 instances
cd 77-eureka-server
mvnw.cmd spring-boot:run
# Wait: Tomcat started on port 8761 – System Status Environment test – No instances available – demo1.png – 00:00

# Terminal 2 – Product Service – demo2.png – 1 UP
cd 74-product-service
mvnw.cmd spring-boot:run
# Wait: DiscoveryClient registration status: 204 – 74-PRODUCT-SERVICE:8082 – demo2.png – 1 UP – 00:02

# Terminal 3 – Order Service – demo3.png – 2 UP – Fixes ORA-00942 – Was 500 now []
cd 75-order-service
mvnw.cmd spring-boot:run
# Wait: HikariPool-1 - Starting... Added connection oracle... HHH000412: Hibernate ORM 6.4.4.Final
# Hibernate: create table orders (id number...) – Table created!
# Tomcat started on port 8086 – DiscoveryClient registration status: 204 – demo3.png – 2 UP – 00:05
# Test: http://localhost:8086/api/orders -> [] – Fixed! Before 500

# Terminal 4 – Gateway – demo4.png – 3 UP – All complete
cd 76-api-gateway
mvnw.cmd spring-boot:run
# Wait: Netty started on port 8084 – DiscoveryClient registration status: 204 – demo4.png – 3 UP – 00:09 – All UP

# Wait 12 min more – demo5.png – Stable 00:21 – 3 UP – 6 Renews – Final
```

Open in order – demo1 to demo8 progression:

- http://localhost:8761 – demo1.png 0 instances -> demo2 1 UP -> demo3 2 UP -> demo4 3 UP -> demo5 stable 00:21 – 3 UP – Main Eureka proof
- http://localhost:8082/api/products – Direct Product – iPhone 15
- http://localhost:8086/api/orders – demo8.png – Direct Order – [] – Fixed 500 -> []
- http://localhost:8084/api/products – demo6.png – Via Gateway lb://74 – Same iPhone 15 JSON – Gateway + Eureka works!
- http://localhost:8084/api/orders – demo7.png – Via Gateway lb://75 – [] – Same as direct – Main fix proof!

### 5. Backend Logic – 500 -> 200 Fix

```java
// Before Fix – 500 Error – ORA-00942
GET http://localhost:8084/api/orders
-> Gateway -> lb://75-ORDER-SERVICE -> http://localhost:8086/api/orders
-> OrderService -> orderRepository.findAll() -> SELECT * FROM ORDERS_TABLE
-> ORA-00942: table or view does not exist – ORDER_ITEMS / ORDERS_TABLE not found
-> Whitelabel Error Page 500 Internal Server Error – Type=Internal Server Error

// Manual SQL Fix
sqlplus PRODUCT_USER/product123
CREATE TABLE ORDERS_TABLE (...) – Table created.
CREATE TABLE ORDER_ITEMS (...) – Table created.
CREATE SEQUENCE ORDERS_SEQ – Sequence created.
CREATE SEQUENCE ORDER_ITEMS_SEQ – Sequence created.

// After Fix – 200 OK – []
GET http://localhost:8086/api/orders
Hibernate: select o1_0.id,o1_0.order_date,o1_0.status,o1_0.total_amount,o1_0.user_id from orders o1_0
-> [] – 4 SELECT queries in console = success – demo8.png

GET http://localhost:8084/api/orders – Via Gateway
-> Same [] – demo7.png – Fixed!

// Eureka Registration Logs – demo2 to demo5
com.netflix.discovery.DiscoveryClient – Getting all instance registry info – response status 200
DiscoveryClient_75-ORDER-SERVICE/localhost:75-ORDER-SERVICE:8086: registering service...
DiscoveryClient_75-ORDER-SERVICE – registration status: 204 – UP
Started Application in 28.713 seconds – Tomcat started on port 8086
```

## 🔄 Application Flow – Work Process Fixed – demo1 to demo8

```text
Start Eureka – demo1.png – 00:00 – No instances
 │
 ├── System Status – Environment test – Data center default – Current time 2026-09-20T15:26:36 +0530
 ├── Uptime 00:00 – Lease expiration false – Renews threshold 1 – Renews last min 0
 ├── EMERGENCY! EUREKA MAY BE INCORRECTLY CLAIMING INSTANCES ARE UP – Renewals lesser than threshold – Normal fresh start
 └── Instances currently registered – No instances available – 0 UP – Fresh

Start Product 74 – demo2.png – 00:02 – 1 UP
 │
 ├── 74-PRODUCT-SERVICE:8082 registers – n/a (1) – (1) – UP (1) - 74-PRODUCT-SERVICE:8082
 └── 1 instance only – Product ready – iPhone 15 in DB

Start Order 75 – demo3.png – 00:05 – 2 UP – ORA-00942 Fix
 │
 ├── Before: ORA-00942: table or view does not exist – DROP fails – Table not there
 ├── Fix: CREATE TABLE ORDERS_TABLE – Table created. – CREATE TABLE ORDER_ITEMS – Table created.
 ├── Fix: CREATE SEQUENCE ORDERS_SEQ + ORDER_ITEMS_SEQ – Sequence created.
 ├── Start 75 – HikariPool-1 Starting – Added connection – Hibernate Version 6.4.4.Final
 ├── Hibernate: create table orders (...) – Auto create lower case orders – Now ORDERS_TABLE + orders both exist
 ├── Hibernate: alter table order_items add constraint FK... foreign key (order_id) references orders
 ├── Tomcat started on port 8086 – DiscoveryClient registration status 204 – Started in 28.713 sec
 ├── demo3.png – 2 UP – 74-PRODUCT-SERVICE:8082 + 75-ORDER-SERVICE:8086 – Order fixed
 └── Test direct 8086/api/orders – Hibernate: select o1_0.id... from orders o1_0 x4 – [] – Not 500 – demo8.png

Start Gateway 76 – demo4.png – 00:09 – 3 UP – All Complete
 │
 ├── Netty started on port 8084 – DiscoveryClient registration status 204
 ├── demo4.png – 3 UP – 74-PRODUCT-SERVICE:8082, 75-ORDER-SERVICE:8086, 76-API-GATEWAY:8084 green link
 ├── Uptime 00:09 – Renews threshold 6 – Renews last min 4 – EMERGENCY! still shows but 3 UP
 └── Test gateway 8084/api/products – demo6.png – iPhone 15 JSON – Via lb://74 – Works!
     Test gateway 8084/api/orders – demo7.png – [] – Via lb://75 – Fixed 500 -> 200 OK!

Stable – demo5.png – 00:21 – 3 UP – Final Proof
 │
 ├── Current time 2026-09-20T15:47:06 +0530 – Uptime 00:21 – Long running stable
 ├── Lease expiration false – Renews threshold 6 – Renews last min 6 – Threshold met – Stable!
 ├── EMERGENCY! message still in red but instances not expired just to be safe – Normal dev
 ├── Instances: 74-PRODUCT-SERVICE n/a (1) UP (1) - 74-PRODUCT-SERVICE:8082
 │           75-ORDER-SERVICE n/a (1) UP (1) - localhost:75-ORDER-SERVICE:8086
 │           76-API-GATEWAY n/a (1) UP (1) - localhost:76-API-GATEWAY:8084
 └── 3 UP stable – 77 COMPLETE – 8 screenshots!

Gateway Routing – Final Verified
 │
 ├── Browser demo6 – localhost:8084/api/products – [{"id":1,"name":"iPhone 15"...price:79999.0}] – Via Gateway lb://74 – Main product proof
 ├── Browser demo7 – localhost:8084/api/orders – [] – Via Gateway lb://75 – Main order proof – 500 fixed to []
 ├── Browser demo8 – localhost:8086/api/orders – [] – Direct – Same as demo7 – Compare
 └── All 3 services UP – Eureka 8761 dashboard – No DOWN – Tier 8 Complete
```

## 🧪 API Testing – Work Process Proofs – 8 Demos

```bash
# Eureka progression – demo1 to demo5
curl http://localhost:8761
# demo1: No instances available – 00:00 – Fresh
# demo2: 1 UP – 74-PRODUCT-SERVICE:8082 – 00:02
# demo3: 2 UP – 74 + 75 – 00:05 – Order fixed
# demo4: 3 UP – 74 + 75 + 76 – 00:09 – All UP
# demo5: 3 UP stable – 00:21 – Renews 6 – Final

# Direct Product – 8082
curl http://localhost:8082/api/products
# [{"id":1,"name":"iPhone 15","description":"Latest Apple iPhone","price":79999.0,"category":"Electronics","stock":50,"imageUrl":"iphone15.jpg"}]

# Direct Order – demo8.png – Fixed from 500 to []
curl http://localhost:8086/api/orders
# [] – Hibernate: select o1_0.id,o1_0.order_date... from orders o1_0 – 4 SELECT = success – demo8.png

# Via Gateway – Products – demo6.png – Main proof Gateway + Eureka
curl http://localhost:8084/api/products
# [{"id":1,"name":"iPhone 15","description":"Latest Apple iPhone","price":79999.0,"category":"Electronics","stock":50,"imageUrl":"iphone15.jpg"}]
# Same as direct 8082 – Gateway lb://74-PRODUCT-SERVICE works!

# Via Gateway – Orders – demo7.png – Main proof Fixed 500 -> 200
curl http://localhost:8084/api/orders
# [] – Via Gateway lb://75-ORDER-SERVICE – Same as direct 8086 – Main proof 77 completed! – Before 500 Now []

# Health
curl http://localhost:8761/actuator/health
# {"status":"UP"} – Eureka UP
curl http://localhost:8084/actuator/health
# {"status":"UP"} – Gateway UP
```

Browser + Console Proofs – 8 Demos:

```text
Console – Order Start – 28.713 seconds – Fixes ORA-00942
[2026-09-20T15:44:12] Starting Application using Java 21.0.10 PID 31656 – 75-ORDER-SERVICE
Bootstrapping Spring Data JPA repositories – Found 1 JPA repository interface
HikariPool-1 - Starting... HikariPool-1 - Added connection oracle.jdbc.driver.T4CConnection@24954e82 – DB connected PRODUCT_USER
HHH000511: Oracle 11.2.0 version no longer supported – Warning harmless
Hibernate: create table orders (id number(19,0) not null...) – Auto created – Fixes ORA-00942 – Now orders table exists!
Hibernate: alter table order_items add constraint FK... foreign key (order_id) references orders – FK created
Initialized JPA EntityManagerFactory – Open-in-view warning harmless
Cannot determine local hostname – InetUtils warning harmless
DiscoveryClientOptionalArgsConfiguration – Eureka HTTP Client uses RestTemplate
Exposing 1 endpoint(s) beneath base path '/actuator' – Actuator
Setting initial instance status as: STARTING – Eureka
Initializing Eureka in region us-east-1 – Resolving eureka endpoints via config
Discovery Client initialized at timestamp 1789899278084 with initial instances count: 3 – Got 3 from Eureka
Registering application 75-ORDER-SERVICE with eureka with status UP – Registering...
DiscoveryClient_75-ORDER-SERVICE/localhost:75-ORDER-SERVICE:8086: registering service... – Registering
DiscoveryClient_75-ORDER-SERVICE – registration status: 204 – Registered! – demo3.png 2 UP
Tomcat started on port 8086 (http) with context path '' – 8086 ready
Started Application in 28.713 seconds – 75-ORDER-SERVICE ready!

Console – Order Queries – 500 -> [] Fixed – demo7 + demo8
Hibernate: select o1_0.id,o1_0.order_date,o1_0.status,o1_0.total_amount,o1_0.user_id from orders o1_0
Hibernate: select o1_0.id... – x4 – 4 requests – Via direct 8086 and via gateway 8084 – All SELECT success – No 500!
-> [] – Returns empty list – Not Whitelabel 500 – Fixed!

Console – Eureka Dashboard – demo1 to demo5 progression
demo1: Environment test – Data center default – Current time 2026-09-20T15:26:36 +0530 – Uptime 00:00 – No instances available – 0 UP
demo2: Uptime 00:02 – Renews threshold 3 – Renews last min 0 – 1 UP – 74-PRODUCT-SERVICE:8082
demo3: Uptime 00:05 – Renews threshold 5 – Renews last min 2 – 2 UP – 74 + 75
demo4: Uptime 00:09 – Renews threshold 6 – Renews last min 4 – 3 UP – 74 + 75 + 76 – All UP
demo5: Current time 2026-09-20T15:47:06 +0530 – Uptime 00:21 – Renews threshold 6 – Renews last min 6 – 3 UP stable – Final!
EMERGENCY! EUREKA MAY BE INCORRECTLY CLAIMING INSTANCES ARE UP WHEN THEY'RE NOT. – Normal in dev – Renewals lesser than threshold – Not expiring just to be safe

Browser – demo1 to demo8 – 8 screenshots – Real progression
demo1 03:27 PM – 8761 – No instances – 0 UP – Start
demo2 03:28 PM – 8761 – 1 UP – 74 Product
demo3 03:31 PM – 8761 – 2 UP – 74 + 75 Order fixed
demo4 03:35 PM – 8761 – 3 UP – 74 + 75 + 76 Gateway – All complete – 00:09
demo5 03:48 PM – 8761 – 3 UP stable – 00:21 – 6 Renews – Final stable – Time progression 03:27 -> 03:48 – 21 min
demo6 8084/api/products – [{"id":1,"name":"iPhone 15"...}] – Via Gateway lb:// – Main product
demo7 8084/api/orders – [] – Via Gateway lb:// – Main order – 500 -> 200 fix
demo8 8086/api/orders – [] – Direct – Same as demo7 – Compare proof
```

## 📡 API Endpoints – Eureka + Gateway Routes – 8 Demos

| Method | Endpoint | Purpose | Via | Demo | Status |
|---|---|---|---|---|---|
| GET | `http://localhost:8761` | Eureka Dashboard – 0 -> 1 -> 2 -> 3 UP progression | Eureka 8761 | demo1 0 UP, demo2 1 UP, demo3 2 UP, demo4 3 UP, demo5 stable 00:21 | ✅ |
| GET | `http://localhost:8082/api/products` | Direct Product – iPhone 15 | Product 8082 direct | - | ✅ |
| GET | `http://localhost:8086/api/orders` | Direct Order – [] – Fixed 500 -> 200 | Order 8086 direct | demo8.png – [] – Direct proof | ✅ |
| GET | `/api/products` via 8084 | Products via Gateway + Eureka lb://74 – iPhone 15 JSON | Gateway 8084 -> lb://74 -> 8082 | demo6.png – Main product via gateway | ✅ Main |
| GET | `/api/orders` via 8084 | Orders via Gateway + Eureka lb://75 – [] – Fixed 500 | Gateway 8084 -> lb://75 -> 8086 | demo7.png – Main order via gateway – 500->200 fix | ✅ Main |
| GET | `/actuator/gateway/routes` | Gateway routes lb:// URIs | Gateway 8084 | - | - |
| GET | `/actuator/health` | Health UP | All services | - | - |

## 🗄 Gateway + Eureka + Oracle Note – ORA-00942 + lb:// Logic

Eureka + Gateway + Oracle PRODUCT_USER – Manual table creation fixes Hibernate auto DDL failure.

```text
Before Fix – ORA-00942 + 500 – demo1 + demo2 only 1 UP
SQL> DROP TABLE ORDER_ITEMS CASCADE CONSTRAINTS PURGE;
-> ORA-00942: table or view does not exist – No table – Hibernate failed

Browser GET /api/orders via 8084 -> lb://75-ORDER-SERVICE -> 8086
-> OrderService -> findAll() -> SELECT * FROM ORDER_ITEMS / ORDERS_TABLE
-> ORA-00942: table or view does not exist – Table not created by Hibernate
-> Whitelabel Error Page 500 Internal Server Error – Internal Server Error
-> Console: No table – 500

Manual Fix – SQLPLUS PRODUCT_USER
SQL> CREATE TABLE ORDERS_TABLE (ID NUMBER PRIMARY KEY...) – Table created.
SQL> CREATE TABLE ORDER_ITEMS (ID NUMBER PRIMARY KEY...) – Table created.
SQL> CREATE SEQUENCE ORDERS_SEQ – Sequence created.
SQL> CREATE SEQUENCE ORDER_ITEMS_SEQ – Sequence created.
SQL> SELECT table_name FROM user_tables WHERE table_name LIKE '%ORDER%';
-> ORDER_ITEMS, ORDERS_TABLE – 2 tables ready

After Fix – Hibernate auto + Manual – [] 200 OK – demo3 to demo8
Start 75-order-service – Hibernate: create table orders (id number...) – Auto creates orders (lower) + manual ORDERS_TABLE exists
-> Hibernate: select o1_0.id... from orders o1_0 – SELECT success – 4 times – [] not 500
-> Browser demo8.png – localhost:8086/api/orders – [] – Direct works – 500 fixed!
-> Browser demo7.png – localhost:8084/api/orders – [] – Via Gateway lb://75 – Same as direct – Main fix proof!

Eureka Progression – 0 -> 3 UP – demo1 to demo5
demo1 03:27 PM – No instances – Fresh Eureka – 00:00 – EMERGENCY! Renewals 0 < threshold 1
demo2 03:28 PM – 1 UP – 74 Product registers – 00:02 – Renew threshold 3 – Renews 0 – Still EMERGENCY!
demo3 03:31 PM – 2 UP – 75 Order registers – After ORA-00942 fix – 00:05 – Renew threshold 5 – Renews 2 – 2 UP
demo4 03:35 PM – 3 UP – 76 Gateway registers – All services – 00:09 – Renew threshold 6 – Renews 4 – 3 UP – All complete
demo5 03:48 PM – 3 UP stable – 00:21 – Renew threshold 6 – Renews 6 – Threshold met! – Stable – No expiring – Final proof 21 min uptime

lb:// Routing – Eureka Discovery – Not hardcoded
Before 76 (without Eureka): GatewayConfig uri("http://localhost:8086") – Hardcoded localhost
After 77 (with Eureka): GatewayConfig uri("lb://75-ORDER-SERVICE") – Discovery via Eureka – Load Balanced – No localhost hardcoded
- Gateway asks Eureka where is 75-ORDER-SERVICE? – Eureka returns localhost:75-ORDER-SERVICE:8086 – Gateway routes lb://
- demo6.png – 8084/api/products -> lb://74-PRODUCT-SERVICE -> 8082 – iPhone 15 JSON – Discovery works!
- demo7.png – 8084/api/orders -> lb://75-ORDER-SERVICE -> 8086 – [] – Discovery works! – Fixed!
```

### Verified – Work Process – 8 Demos

- demo1 03:27 PM – 8761 – No instances available – 00:00 – Fresh Eureka – Environment test – Data center default – EMERGENCY! – Start of journey
- demo2 03:28 PM – 8761 – 1 UP – 74-PRODUCT-SERVICE:8082 only – 00:02 – Product registered first – n/a (1) – (1) – UP
- demo3 03:31 PM – 8761 – 2 UP – 74 + 75 – 00:05 – Order registered after ORA-00942 fix – localhost:75-ORDER-SERVICE:8086 – 2 UP – 4 min after demo2
- demo4 03:35 PM – 8761 – 3 UP – 74 + 75 + 76 – 00:09 – Gateway registers – localhost:76-API-GATEWAY:8084 green – All 3 UP – 4 min after demo3 – Complete microservices
- demo5 03:48 PM – 8761 – 3 UP stable – 00:21 – 6 Renews threshold 6 last min 6 – Stable 13 min after demo4 – Final Eureka stable – Time 03:35 -> 03:48
- demo6 – 8084/api/products – [{"id":1,"name":"iPhone 15","price":79999.0,"category":"Electronics","stock":50}] – Via Gateway lb://74-PRODUCT-SERVICE – Main product proof – Gateway + Eureka works!
- demo7 – 8084/api/orders – [] – Via Gateway lb://75-ORDER-SERVICE – Via 8084 -> 8086 – Fixed 500 -> 200 OK – Main order proof – Before Whitelabel 500 After []
- demo8 – 8086/api/orders – [] – Direct Order – Same as demo7 – Direct vs Gateway same JSON – Compare proof – 500 fixed
- Fix 1 ORA-00942 table or view does not exist -> Manual CREATE TABLE ORDERS_TABLE + ORDER_ITEMS – Table created. – demo3 2 UP
- Fix 2 ORA-02289 sequence does not exist -> Manual CREATE SEQUENCE ORDERS_SEQ + ORDER_ITEMS_SEQ – Sequence created. – 3 sequences
- Fix 3 500 Internal Server Error on /api/orders -> [] 200 OK – Hibernate SELECT x4 – 4 queries success – demo7 + demo8 both []
- Fix 4 Eureka 0 -> 3 UP progression – demo1 to demo5 – 00:00 -> 00:21 – 21 min uptime – Stable
- Fix 5 Products via Gateway – iPhone 15 via 8084 same as 8082 – demo6.png – lb:// routing works
- Oracle PRODUCT_USER – sqlplus PRODUCT_USER/product123 – Connected to Oracle Database 11g Express Edition 11.2.0.2.0 – 64bit Production
- Hibernate 6.4.4.Final – create table orders – auto DDL – HHH000204 Processing PersistenceUnitInfo – HHH000412 Hibernate ORM core version
- HikariPool-1 Starting – Added connection oracle.jdbc.driver.T4CConnection – DB connected

## 📸 Screenshots – 8 Demos – Completed – Work Process – 03:27 PM to 03:48 PM

### 1. Eureka Fresh – Browser – localhost:8761 – No instances available – 00:00 – EMERGENCY! – Start

[demo1](screenshots/demo1.png)

---

### 2. Eureka 1 UP – Browser – localhost:8761 – 1 UP – 74-PRODUCT-SERVICE:8082 – 00:02 – First service

[demo2](screenshots/demo2.png)

---

### 3. Eureka 2 UP – Browser – localhost:8761 – 2 UP – 74 + 75-ORDER-SERVICE:8086 – 00:05 – Order fixed ORA-00942

[demo3](screenshots/demo3.png)

---

### 4. Eureka 3 UP – Browser – localhost:8761 – 3 UP – 74 + 75 + 76-API-GATEWAY:8084 – 00:09 – All UP – Complete!

[demo4](screenshots/demo4.png)

---

### 5. Eureka Stable – Browser – localhost:8761 – 3 UP stable – 00:21 – Renews 6 – 6 Renews – Final stable – 21 min uptime

[demo5](screenshots/demo5.png)

---

### 6. Products via Gateway – Browser – localhost:8084/api/products – Via Gateway lb://74 – iPhone 15 JSON – Main product proof

[demo6](screenshots/demo6.png)

---

### 7. Orders via Gateway – Browser – localhost:8084/api/orders – Via Gateway lb://75 – [] – Fixed 500 -> 200 OK – Main order proof!

[demo7](screenshots/demo7.png)

---

### 8. Orders Direct – Browser – localhost:8086/api/orders – Direct – [] – Same as via Gateway – Compare demo7 – 500 fixed!

[demo8](screenshots/demo8.png)

---

## 🎯 Learning Outcomes – Work Process Included – 8 Demos

- Eureka Server Tier 8 – First Discovery Server – Port 8761 – @EnableEurekaServer – System Status – Environment test default – Uptime 00:00 -> 00:21 – 21 min stable – Renews threshold 1 -> 6 – Renews last min 0 -> 6 – EMERGENCY! warning normal in dev
- 0 -> 3 UP Progression – demo1 No instances -> demo2 1 UP Product -> demo3 2 UP Product+Order -> demo4 3 UP Product+Order+Gateway -> demo5 stable 3 UP 00:21 – Real startup flow – 03:27 PM to 03:48 PM – 21 min journey – Screenshots time proof
- ORA-00942 Fix – table or view does not exist – Oracle PRODUCT_USER permissions – DROP fails ORA-00942 – Fix: Manual CREATE TABLE ORDERS_TABLE + ORDER_ITEMS + CREATE SEQUENCE ORDERS_SEQ + ORDER_ITEMS_SEQ – Table created. Sequence created. – SELECT table_name FROM user_tables shows ORDER_ITEMS, ORDERS_TABLE – SELECT sequence_name shows 3 sequences
- ORA-02289 Fix – sequence does not exist – DROP SEQUENCE fails ORA-02289 – Fix: CREATE SEQUENCE – Sequence created. – 3 sequences ready – PRODUCT_SEQ + ORDERS_SEQ + ORDER_ITEMS_SEQ
- 500 -> 200 OK Fix – Before: Whitelabel Error Page 500 Internal Server Error on /api/orders via Gateway – Hibernate ORA-00942 – After: Hibernate: select o1_0.id... from orders o1_0 x4 – 4 SELECT queries success – [] empty list 200 OK – demo7 via gateway + demo8 direct same [] – Main fix!
- lb:// Routing – Eureka Discovery – Before 76 hardcoded http://localhost:8086 – After 77 lb://75-ORDER-SERVICE – Gateway asks Eureka where is service – Eureka returns localhost:75-ORDER-SERVICE:8086 – Discovery + Load Balanced – Not hardcoded – demo6 iPhone 15 via lb://74 + demo7 [] via lb://75
- Hibernate Auto DDL – Hibernate: create table orders (id number(19,0) not null...) – Auto creates lower case orders table – Manual ORDERS_TABLE also exists – 2 tables – SELECT works – HHH000204 Processing PersistenceUnitInfo, HHH000412 Hibernate ORM 6.4.4.Final, HHH000026 Second-level cache disabled, HHH000511 Oracle 11.2.0 version no longer supported warning harmless
- HikariPool – HikariPool-1 - Starting... Added connection oracle.jdbc.driver.T4CConnection@24954e82 – DB connected PRODUCT_USER – Start completed – JPA EntityManagerFactory initialized
- Eureka Client Registration – DiscoveryClientOptionalArgsConfiguration – Eureka HTTP Client uses RestTemplate – InstanceInfoFactory Setting initial instance status STARTING – Initializing Eureka in region us-east-1 – Resolving eureka endpoints via configuration – Discovery Client initialized with initial instances count: 3 – Registering application 75-ORDER-SERVICE with status UP – Saw local status change STARTING -> UP – DiscoveryClient registering service... registration status: 204 – UP – Started Application in 28.713 seconds – Tomcat started on port 8086
- Gateway + Eureka – 76-API-GATEWAY UP (1) - localhost:76-API-GATEWAY:8084 green link – 3 UP complete – Products via gateway iPhone 15 + Orders via gateway [] – Both work – 77 COMPLETE – 8 screenshots proof
- Oracle 11g XE – sqlplus Release 11.2.0.2.0 – Connected to Oracle Database 11g Express Edition 11.2.0.2.0 64bit – PRODUCT_USER/product123 – Manual DDL – Enterprise DB handling

## 🚀 Future Enhancements – Next Projects

- Add 78 Config Server – Centralize application.yml for 74,75,76,77 – Spring Cloud Config – Git backend
- Add 79 Circuit Breaker – Resilience4j – Fallback when 8082 down – Gateway fallback – Not 500
- Add 80 Kafka – Order events – Product stock decrease via Kafka – Async microservices
- Add 81 Zipkin Tracing – Distributed tracing – Trace request 8084 -> 8082 + 8086 via Eureka
- Add JWT Strict – 73-auth-service 8081 – Validate token in Gateway JwtAuthenticationFilter – Currently dev allow – Production block
- Add Load Balancer – 2 instances of Product Service 8082 + 8083 – Eureka registers 2 – Gateway lb:// load balances – 2 UP for product
- Add Docker – 77-eureka, 76-gateway, 74-product, 75-order, Oracle XE – docker-compose up – All 5 containers
- Add React Frontend – All requests via Gateway 8084 – http://localhost:8084/api/products + /api/orders – Single entry – No direct 8082/8086 from frontend
- Add Swagger Aggregation – Gateway aggregates swagger from 74 + 75 – http://localhost:8084/swagger-ui.html – Single UI
- Deploy to Render/Railway – Environment variables – EUREKA_SERVER http://eureka:8761/eureka – PORT 8761/8084/8082/8086
- Add 82 Monitoring – Prometheus + Grafana – Monitor Eureka renews, Gateway routes, Order SELECT queries

## 👨💻 Author

### Vemula Leela Venkata Ravi Teja

Java Full Stack Developer

100 Java Full Stack Projects Challenge

Project 77 / 100 – Microservices Track – Eureka + Gateway + Product + Order – 8761 + 8084 + 8082 + 8086 – 3 UP – ORA-00942 + 500 Fix – 5 Fixes Completed – 8 Demos

Tier 8 – Microservices – Eureka Discovery – Fifth of 5 – Port 8761 + 8084 – Discovery + Routing – Entry + Registry

### Test Eureka + Gateway – Products + Orders via Gateway

- `localhost:8761` / `Eureka` / `3 UP – 74:8082 + 75:8086 + 76:8084 – 00:21 stable – 6 Renews` – demo5.png – Final stable
- `localhost:8084/api/products` / `Via Gateway lb://74` / `iPhone 15 JSON – price 79999.0 – Same as direct 8082` – demo6.png – Main product
- `localhost:8084/api/orders` / `Via Gateway lb://75` / `[] – Empty but 200 OK – Fixed from 500 – Same as direct 8086` – demo7.png – Main order fix!
- `localhost:8086/api/orders` / `Direct 8086` / `[] – Same as via gateway demo7 – Compare – 500 fixed` – demo8.png – Direct

## ⭐ Support

If you found this project helpful fixing ORA-00942 table or view does not exist / ORA-02289 sequence does not exist / 500 Internal Server Error -> [] 200 OK / Eureka 0 -> 3 UP progression / lb:// routing via Eureka / EMERGENCY! Renewals threshold, give it a ⭐ Star!

### Repo

https://github.com/raviteja-dev950/77-Eureka-Gateway

### Run Order – Must Start Eureka First – demo1 to demo5

```bash
# Terminal 1 – Eureka – demo1.png 0 instances – 00:00 – 03:27 PM
cd 77-eureka-server
mvnw.cmd spring-boot:run
# http://localhost:8761 – No instances available – Fresh – EMERGENCY!

# Terminal 2 – Product – demo2.png 1 UP – 00:02 – 03:28 PM
cd 74-product-service
mvnw.cmd spring-boot:run
# http://localhost:8761 – 1 UP – 74-PRODUCT-SERVICE:8082

# Terminal 3 – Order – demo3.png 2 UP – 00:05 – 03:31 PM – Fixes ORA-00942
# First: sqlplus PRODUCT_USER/product123 – CREATE TABLE + SEQUENCE – See section 2
cd 75-order-service
mvnw.cmd spring-boot:run
# http://localhost:8761 – 2 UP – 74 + 75 – http://localhost:8086/api/orders -> [] – demo8.png – Fixed!

# Terminal 4 – Gateway – demo4.png 3 UP – 00:09 – 03:35 PM – All UP
cd 76-api-gateway
mvnw.cmd spring-boot:run
# http://localhost:8761 – 3 UP – 74 + 75 + 76 – All UP – 76 green
# http://localhost:8084/api/products – demo6.png – iPhone 15 – Via lb://74 – Works!
# http://localhost:8084/api/orders – demo7.png – [] – Via lb://75 – Fixed 500 -> 200!

# Wait 13 min – demo5.png stable – 00:21 – 03:48 PM – 3 UP stable – 6 Renews – Final
# http://localhost:8761 – 3 UP stable – 00:21 – Final proof – 21 min uptime
```

Open:

```text
http://localhost:8761 – Eureka – demo1 0 UP 00:00 03:27 PM -> demo2 1 UP 00:02 03:28 PM -> demo3 2 UP 00:05 03:31 PM -> demo4 3 UP 00:09 03:35 PM -> demo5 stable 00:21 03:48 PM – 3 UP – Final
http://localhost:8082/api/products – Direct Product – iPhone 15 JSON
http://localhost:8086/api/orders – demo8.png – Direct Order – [] – Fixed 500
http://localhost:8084/api/products – demo6.png – Via Gateway lb://74 – iPhone 15 – Same as direct – Main product
http://localhost:8084/api/orders – demo7.png – Via Gateway lb://75 – [] – Same as direct – Main order fix – 500->200
```

### Work Process Summary – 8 Demos – 03:27 PM to 03:48 PM

```text
1. demo1 03:27 PM – Eureka 8761 – No instances available – 00:00 – Fresh start – EMERGENCY! Renew 0 < threshold 1
2. demo2 03:28 PM – Eureka 8761 – 1 UP – 74-PRODUCT-SERVICE:8082 – 00:02 – Product registers – 1 min after demo1
3. demo3 03:31 PM – Eureka 8761 – 2 UP – 74 + 75-ORDER-SERVICE:8086 – 00:05 – Order registers after ORA-00942 fix – 3 min after demo2 – Manual CREATE TABLE ORDERS_TABLE + ORDER_ITEMS + SEQUENCE
4. demo4 03:35 PM – Eureka 8761 – 3 UP – 74 + 75 + 76-API-GATEWAY:8084 – 00:09 – Gateway registers – All UP – 4 min after demo3 – Complete microservices
5. demo5 03:48 PM – Eureka 8761 – 3 UP stable – 00:21 – 6 Renews threshold 6 last min 6 – Stable – 13 min after demo4 – Final stable – 21 min total uptime – 03:27 to 03:48
6. demo6 – 8084/api/products – [{"id":1,"name":"iPhone 15","price":79999.0}] – Via Gateway lb://74-PRODUCT-SERVICE – iPhone 15 JSON – Gateway + Eureka works!
7. demo7 – 8084/api/orders – [] – Via Gateway lb://75-ORDER-SERVICE – Via 8084 -> 8086 – Fixed 500 Internal Server Error -> 200 OK [] – Main order fix proof!
8. demo8 – 8086/api/orders – [] – Direct – Same as demo7 via gateway – Compare – Direct vs Gateway same – 500 fixed – Hibernate SELECT x4 success

Fixes:
- ORA-00942: table or view does not exist – DROP TABLE fails – No table – Fix: CREATE TABLE ORDERS_TABLE + ORDER_ITEMS – Table created.
- ORA-02289: sequence does not exist – DROP SEQUENCE fails – No sequence – Fix: CREATE SEQUENCE ORDERS_SEQ + ORDER_ITEMS_SEQ – Sequence created.
- 500 Internal Server Error on /api/orders -> [] 200 OK – Whitelabel Error Page 500 -> Hibernate SELECT success [] – demo7 + demo8 both [] – Fixed!
- Eureka 0 -> 3 UP – demo1 0 UP 00:00 -> demo5 3 UP 00:21 stable – 21 min – 03:27 PM to 03:48 PM – Time progression proof
- Products via Gateway iPhone 15 via 8084 same as 8082 – demo6 – lb:// routing works
```

### Logs Proof – 28.713 seconds + 4 SELECT

```text
Starting Application using Java 21.0.10 with PID 31656 – E:\Java-100-Projects\75-order-service – 75-ORDER-SERVICE main
No active profile set, falling back to 1 default profile: "default"
Bootstrapping Spring Data JPA repositories in DEFAULT mode – Finished in 232 ms – Found 1 JPA repository interface
BeanFactory id=d80d0695-b85e-31b9-af3e-6ea750dd6323 – GenericScope
Tomcat initialized with port 8086 (http) – Starting service Tomcat – Starting Servlet engine Apache Tomcat/10.1.20
Initializing Spring embedded WebApplicationContext – Root WebApplicationContext: initialization completed in 3878 ms
HHH000204: Processing PersistenceUnitInfo [name: default] – HHH000412: Hibernate ORM core version 6.4.4.Final – HHH000026: Second-level cache disabled
No LoadTimeWeaver setup: ignoring JPA class transformer
HikariPool-1 - Starting... – HikariPool-1 - Added connection oracle.jdbc.driver.T4CConnection@24954e82 – Start completed – DB connected
HHH000511: The 11.2.0 version for [org.hibernate.dialect.OracleDialect] is no longer supported – Warning harmless – Minimum supported 19.0.0
HHH000489: No JTA platform available – No JTA
Hibernate: create table orders (id number(19,0) not null, order_date timestamp(6), status varchar2(255 char), total_amount float(53), user_id number(19,0), primary key (id)) – Auto created! – Fixes ORA-00942
Hibernate: alter table order_items add constraint FKbioxgbv59vetrxe0ejfubep1w foreign key (order_id) references orders – FK created
Initialized JPA EntityManagerFactory for persistence unit 'default' – JPA ready
spring.jpa.open-in-view is enabled by default – Warning harmless
Cannot determine local hostname – InetUtils warning harmless – 2 times
Eureka HTTP Client uses RestTemplate – DiscoveryClientOptionalArgsConfiguration
Spring Cloud LoadBalancer is currently working with default cache – Caffeine warning harmless
Exposing 1 endpoint(s) beneath base path '/actuator' – Actuator
Setting initial instance status as: STARTING – InstanceInfoFactory
Initializing Eureka in region us-east-1 – Resolving eureka endpoints via configuration – Disable delta false – Single vip null – Force full fetch false
Getting all instance registry info from the eureka server – The response status is 200 – Got 3 instances
Starting heartbeat executor: renew interval is: 30 – InstanceInfoReplicator onDemand update rate per min 4
Discovery Client initialized at timestamp 1789899278084 with initial instances count: 3 – 3 from Eureka
Registering application 75-ORDER-SERVICE with eureka with status UP – Saw local status change STARTING -> UP
DiscoveryClient_75-ORDER-SERVICE/localhost:75-ORDER-SERVICE:8086: registering service... – Registering
DiscoveryClient_75-ORDER-SERVICE – registration status: 204 – Registered! – UP
Tomcat started on port 8086 (http) with context path '' – 8086 ready – Updating port to 8086
Started Application in 28.713 seconds (process running for 29.951) – 75-ORDER-SERVICE ready! – demo3 2 UP
Initializing Spring DispatcherServlet 'dispatcherServlet' – Completed initialization in 1 ms

After Fix – 4 SELECT Queries – 500 -> []
Hibernate: select o1_0.id,o1_0.order_date,o1_0.status,o1_0.total_amount,o1_0.user_id from orders o1_0
Hibernate: select o1_0.id... – x4 – 4 requests – Via 8086 direct and via 8084 gateway – All success – [] not 500 – demo7 + demo8
```
