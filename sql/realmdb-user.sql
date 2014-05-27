drop user 'anakin'@'localhost';
create user 'anakin'@'localhost' identified by 'anakin';
grant all privileges on realmdb.* to 'anakin'@'localhost';
flush privileges;