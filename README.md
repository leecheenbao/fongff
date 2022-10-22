# Fongff backend

## 概要

* 使用 **Spring boot + Spring Security + JWT** 實作登入功能
* 區分 **ROLE_ADMIN / ROLE_USER** 兩種權限
* 提供使用者從後台編輯前台所要呈現的內容 **圖片、標題、連結、內文等訊息**
* 以JPA實作資料庫ORM

## 使用工具
* Java 8
* Docker
* Docker-compose
* MySQL
* Nginx
* Tomcat

### Build

    mvn clean install

### 部署方式
產出的war檔放至tomcat

Building the project
--------------------

Clone the repository:

    git clone https://github.com/kolorobot/spring-boot-thymeleaf

Navigate to the newly created folder:

    cd fongff

Run the project with:

    ./mvnw clean spring-boot:run




