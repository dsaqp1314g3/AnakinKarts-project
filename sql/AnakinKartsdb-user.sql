drop user 'anakin'@'localhost';
create user 'anakin'@'localhost' identified by 'anakin';
grant all privileges on anakinkartsdb.* to ''@'localhost';
flush privileges;