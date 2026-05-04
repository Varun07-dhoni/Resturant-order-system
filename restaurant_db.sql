CREATE DATABASE restaurant_db;
USE restaurant_db;
CREATE TABLE menu(item_id INT PRIMARY KEY,item_name VARCHAR(50),price DOUBLE);
INSERT INTO menu VALUES
(1,'Pizza',250),
(2,'Burger',150),
(3,'Pasta',200),
(4,'Sandwich',120),
(5,'Coffee',80),
(6,'Tea',40),
(7,'Cold Coffee',120),
(8,'French Fries',110),
(9,'Veg Noodles',180),
(10,'Fried Rice',170),
(11,'Veg Manchurian',190),
(12,'Paneer Butter Masala',240),
(13,'Butter Naan',50),
(14,'Veg Biryani',220),
(15,'Masala Dosa',90),
(16,'Idli',60),
(17,'Vada',50),
(18,'Gobi Manchurian',180),
(19,'Chocolate Cake',140),
(20,'Ice Cream',100);
CREATE TABLE customers(customer_id INT PRIMARY KEY AUTO_INCREMENT,customer_name VARCHAR(50));
INSERT INTO customers(customer_name) VALUES
('Rahul'),
('Ananya'),
('Rohit'),
('Sneha'),
('Arjun');
CREATE TABLE orders(order_id INT PRIMARY KEY AUTO_INCREMENT,customer_id INT,order_date DATE,FOREIGN KEY(customer_id) REFERENCES customers(customer_id));
CREATE TABLE order_items(order_item_id INT PRIMARY KEY AUTO_INCREMENT,order_id INT,item_id INT,quantity INT,total_price DOUBLE,FOREIGN KEY(order_id) REFERENCES orders(order_id),FOREIGN KEY(item_id) REFERENCES menu(item_id));
select * from menu;