Restaurant Order System (Java + MySQL)
📌 Project Overview

The Restaurant Order System is a console-based Java application designed to automate restaurant order management. It replaces manual paper-based processes with a digital system that handles menu display, order placement, order cancellation, and bill generation efficiently.

The system is integrated with a MySQL database using JDBC, ensuring reliable data storage and real-time operations.

🎯 Features
📋 View Menu (dynamic from database)
🧾 Place Order
❌ Cancel Order
💵 Generate Bill
👤 Automatic Customer Registration
📊 Real-time Bill Calculation
🔄 Database-driven operations
🛠️ Tech Stack
Language: Java (JDK 17+)
Database: MySQL 8.0
Connectivity: JDBC (MySQL Connector/J)
IDE: IntelliJ / Eclipse / VS Code
🗄️ Database Structure

Database: restaurant_db

Tables:
menu
item_id (PK)
item_name
price
customers
customer_id (PK)
customer_name
orders
order_id (PK)
customer_id (FK)
order_date
order_items
order_item_id (PK)
order_id (FK)
item_id (FK)
quantity
total_price
⚙️ System Workflow
Start Application
Display Menu Options
User selects operation:
View Menu
Place Order
Cancel Order
Generate Bill
Perform database operations
Display results


▶️ How to Run
1. Setup Database
Install MySQL
Create database:
CREATE DATABASE restaurant_db;
Create tables (use SQL from project)

2. Configure JDBC
Add MySQL Connector JAR to project
Update DB credentials in DBConnection.java:
DriverManager.getConnection(
    "jdbc:mysql://localhost:3306/restaurant_db",
    "root",
    "root");

3. Compile & Run
javac *.java
java RestaurantOrderSystem
📷 Sample Output


--- Restaurant Order System ---
1 View Menu
2 Place Order
3 Cancel Order
4 Generate Bill
5 Exit

✅ Advantages
Reduces manual errors
Faster order processing
Maintains customer records
Easy to update menu
Zero cost (open-source tools)

⚠️ Limitations
Console-based (no GUI)
Single item per order
No authentication system
No payment integration
No inventory management

🚀 Future Enhancements
Multi-item orders
GUI (JavaFX / Web App)
User authentication system
Online payment integration
Reporting & analytics dashboard
Table management system

📚 Learning Outcomes
JDBC connectivity
SQL operations (SELECT, INSERT, DELETE, JOIN)
Database design & normalization
Java console application development
Exception handling

👨‍💻 Team Members
Swathi P R
Subramanian V
Varun
K M Pooja
