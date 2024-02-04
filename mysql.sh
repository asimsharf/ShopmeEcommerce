
ALTER USER 'root'@'localhost' IDENTIFIED WITH 'mysql_native_password' BY 'new_password';
mysqldump -u username -p database_name > backup.sql
mysql -u username -p database_name < backup.sql