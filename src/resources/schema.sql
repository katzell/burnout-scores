CREATE TABLE score(
id integer(10) NOT NULL AUTO_INCREMENT PRIMARY KEY, 
level INTEGER(2) NOT NULL,
player INTEGER(1) NOT NULL,
value FLOAT(10,2) NOT NULL,
notes VARCHAR(1000),
date DATETIME NOT NULL
);