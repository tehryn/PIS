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

### Java

Make sure java.exe and javac.exe are in %PATH%.

### Eclipse

?#!*$ - to hell with this