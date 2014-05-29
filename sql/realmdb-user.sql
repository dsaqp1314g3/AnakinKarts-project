drop user 'realmanakin'@'localhost';
create user 'realmanakin'@'localhost' identified by 'anakin';
grant all privileges on realmdb.* to 'anakin'@'localhost';
flush privileges;