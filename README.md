# README - PIS

## Dependencies

 * http, mysql, tomcat - Present in [XAMPP](https://www.apachefriends.org/index.html) install everything it offers
 * [jdk8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
 * [eclipse IDE](https://www.eclipse.org/downloads/packages/)
 * [mysql-connector-java-8.0.15](https://dev.mysql.com/downloads/connector/j/8.0.html) - copy the jar file into PIS/lib

### Xampp

Install XAMPP with all modules, launch control panel after installation. Click mysql->Config->my.ini.

Search for section [mysqld] and add following key=value pair:

```
lower_case_table_names = 1
```

[What it does?](https://dba.stackexchange.com/questions/59407/how-to-make-mysql-table-name-case-insensitive-in-ubuntu/69330)

Start apache, mysql and tomcat. When these are running, click on Admin button of mysql. It will open browser with phpMyAdmin. Create new database called pis, then click import, browse for sql/init.sql and execute. Some tables should be created.

#### Accounts

Create following user with proper privileges on pis database:

 * *USER:* pis
 * *PSWD:* p1sh3sl0

### Java

Make sure java.exe and javac.exe are in %PATH%.

### Eclipse

?#!*$ - to hell with this

## Project Import
1. In Eclipse go to File
2. Select import
3. Select Git
4. Select Clone URI
5. Insert https://github.com/tehryn/PIS into URI and fill User and Password fields
6. Select all branches
7. Choose Directory and Initial branch
8. Import using the New Project wizard then finish
9. Select JPA Project
10. Set project as:
    1. Project name: PIS
    2. Target runtime Apache Tomcat v9.0
    3. JPA version 2.1
    4. Basic JPA Configuration
11. Add Folder, then insert "examples" into field.
12. Set JPA as:
    1. Platform Generic 2.1
    2. JPA implementation User Library and download and chose EclipseLink 2.5.2 (download icon is right next to the field)
13. Add Connection
    1. Chose MySQL
    2. New Driver Definition
    3. MySQL JDBC Driver 5.1
    4. Go to JAR List and Add driver located in lib folder, then remove previos driver, if there was any
    5. Go to Properties and set
        1. URL: jdbc:mysql://localhost:3306/pis
        2. Database: pis
        3. Username and Password (pis/p1sh3sl0)
    6. Finish
18. Finish


Troubleshooting:
Configuration error.  Class [com.mysql.jdbc.Driver] not found:
https://javabeat.net/eclipselink-mysql-driver-persistence-exception/

MySQL has problem with timezones:
https://stackoverflow.com/questions/19023978/should-mysql-have-its-timezone-set-to-utc