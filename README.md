# Gestor de Productos de Electricidad

## Descripción
Este proyecto es un **Gestor de Productos de Electricidad** desarrollado con **Spring Boot**. Permite la gestión de artículos eléctricos y las fábricas proveedoras, con distintos niveles de acceso para los usuarios:

- **ADMIN**: Puede ingresar, modificar y gestionar artículos y fábricas proveedoras.
- **USER**: Puede realizar consultas sobre los productos disponibles.

El sistema implementa seguridad con **Spring Security**, gestionando el acceso según los roles asignados. La interfaz de usuario se desarrolla con **Thymeleaf** y **Bootstrap**.

---

## Tecnologías Utilizadas

- **Java 17**
- **Spring Boot**
- **Spring Security**
- **Spring Data JPA**
- **Thymeleaf & Thymeleaf Security**
- **Bootstrap**
- **MySQL**
- **Lombok**
- **Maven**

---

## Instalación y Configuración

1. **Clonar el repositorio:**
   ```bash
   git clone <URL_DEL_REPOSITORIO>
   ```
2. **Configurar la base de datos:**
   - Crear una base de datos llamada `electricity` en MySQL.
   - Ajustar las credenciales en `application.properties`.
3. **Compilar y ejecutar el proyecto:**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
4. **Acceder a la aplicación:**
   - Usuario: `http://localhost:8080/`
   - Administrador: `http://localhost:8080/admin`

---

## Persistencia de Datos

El proyecto utiliza **Spring Data JPA** con **Hibernate** para la persistencia de datos en una base de datos relacional **MySQL**. 
A continuación, se presenta la configuración del archivo `application.properties`:

```properties
spring.application.name=electricitystore

spring.datasource.url=jdbc:mysql://localhost:3307/electricity?allowPublicKeyRetrieval=true&useSSL=false&useTimezone=true&serverTimezone=GMT&characterEncoding=UTF-8
spring.datasource.username=tu_user
spring.datasource.password=tu_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

spring.thymeleaf.cache=false
```

Con esta configuración, la base de datos se actualizará automáticamente en función de las entidades definidas en el código.

---

## Seguridad y Roles

El proyecto utiliza **Spring Security** para la autenticación y autorización de usuarios. Los accesos están definidos de la siguiente manera:

- **Usuarios con rol ADMIN** pueden gestionar productos y proveedores.
- **Usuarios con rol USER** pueden realizar consultas sobre los productos.

Ejemplo de configuración en **Thymeleaf Security**:
```html
<th:block sec:authorize="hasRole('ADMIN')">
    <a href="/admin">Gestión de Productos</a>
</th:block>
```

---

## Dependencias Utilizadas

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.thymeleaf.extras</groupId>
        <artifactId>thymeleaf-extras-springsecurity6</artifactId>
    </dependency>
</dependencies>
```

---

## Contacto
Para consultas o mejoras en el proyecto, no dudes en contactarme.



