CREATE KEYSPACE migrator WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

USE migrator;

CREATE TABLE event (name varchar PRIMARY KEY, count_entries counter);