CREATE TABLE MESSAGE (
	id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
    message VARCHAR(50),
    PRIMARY KEY (id)
);

CREATE TABLE employee
(
id int not null auto_increment,
first_Name varchar(30),
last_Name varchar(30),
salary int,
primary key(id)
);

CREATE TABLE phone
(
id int not null auto_increment,
phone_number varchar(12),
employee_id INT,
primary key(id),
foreign key(employee_id) references employee(id)
);