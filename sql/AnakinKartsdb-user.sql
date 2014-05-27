drop user ''@'localhost';
create user ''@'localhost' identified by '';
grant all privileges on anakinkartsdb.* to ''@'localhost';
flush privileges;