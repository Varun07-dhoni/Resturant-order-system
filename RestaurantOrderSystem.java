package pract.prgs;

import java.sql.*;
import java.util.Scanner;

public class RestaurantOrderSystem {

    static Scanner sc = new Scanner(System.in);

    public static void showMenu() {

        try {

            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM menu";

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(query);

            System.out.println("\n------ MENU ------");

            while (rs.next()) {

                System.out.println(
                        rs.getInt("item_id") + " "
                                + rs.getString("item_name") + " ₹"
                                + rs.getDouble("price"));
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static int getCustomerId(String name) {

        int id = -1;

        try {

            Connection con = DBConnection.getConnection();

            String check = "SELECT customer_id FROM customers WHERE customer_name=?";

            PreparedStatement ps = con.prepareStatement(check);
            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                id = rs.getInt("customer_id");
            } else {

                String insert = "INSERT INTO customers(customer_name) VALUES(?)";

                PreparedStatement ps2 = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
                ps2.setString(1, name);
                ps2.executeUpdate();

                ResultSet rs2 = ps2.getGeneratedKeys();
                rs2.next();

                id = rs2.getInt(1);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return id;
    }

    // ✅ MODIFIED METHOD (MULTIPLE ITEMS IN ONE ORDER)
    public static void placeOrder() {

        try {

            Connection con = DBConnection.getConnection();

            System.out.println("Enter Customer Name:");
            sc.nextLine();
            String name = sc.nextLine();

            int customerId = getCustomerId(name);

            // Create order
            String orderQuery = "INSERT INTO orders(customer_id,order_date) VALUES(?,CURDATE())";

            PreparedStatement ps = con.prepareStatement(orderQuery, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, customerId);
            ps.executeUpdate();

            ResultSet rsKey = ps.getGeneratedKeys();
            rsKey.next();

            int orderId = rsKey.getInt(1);

            System.out.println("Order ID: " + orderId);

            double grandTotal = 0;

            // 🔥 LOOP FOR MULTIPLE ITEMS
            while (true) {

                showMenu();

                System.out.println("\nEnter Item ID:");
                int itemId = sc.nextInt();

                System.out.println("Enter Quantity:");
                int qty = sc.nextInt();

                String query = "SELECT item_name,price FROM menu WHERE item_id=?";

                PreparedStatement ps2 = con.prepareStatement(query);
                ps2.setInt(1, itemId);

                ResultSet rs = ps2.executeQuery();

                if (rs.next()) {

                    String item = rs.getString("item_name");
                    double price = rs.getDouble("price");

                    double total = price * qty;
                    grandTotal += total;

                    System.out.println("\nItem: " + item);
                    System.out.println("Price: " + price);
                    System.out.println("Quantity: " + qty);
                    System.out.println("Total: ₹" + total);

                    String insert = "INSERT INTO order_items(order_id,item_id,quantity,total_price) VALUES(?,?,?,?)";

                    PreparedStatement ps3 = con.prepareStatement(insert);
                    ps3.setInt(1, orderId);
                    ps3.setInt(2, itemId);
                    ps3.setInt(3, qty);
                    ps3.setDouble(4, total);

                    ps3.executeUpdate();

                    System.out.println("Item Added!");

                }

                System.out.println("\nAdd more items? (yes/no)");
                String choice = sc.next();

                if (choice.equalsIgnoreCase("no")) {
                    break;
                }
            }

            System.out.println("\nORDER COMPLETED!");
            System.out.println("Total Bill: ₹" + grandTotal);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void cancelOrder() {

        try {

            Connection con = DBConnection.getConnection();

            System.out.println("Enter Order ID:");
            int id = sc.nextInt();

            String deleteItems = "DELETE FROM order_items WHERE order_id=?";

            PreparedStatement ps1 = con.prepareStatement(deleteItems);
            ps1.setInt(1, id);
            ps1.executeUpdate();

            String deleteOrder = "DELETE FROM orders WHERE order_id=?";

            PreparedStatement ps2 = con.prepareStatement(deleteOrder);
            ps2.setInt(1, id);
            ps2.executeUpdate();

            System.out.println("Order Cancelled Successfully");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // ✅ BILL FOR ALL ITEMS IN ONE ORDER
    public static void generateBill() {

        try {

            Connection con = DBConnection.getConnection();

            System.out.println("Enter Order ID:");
            int id = sc.nextInt();

            String query =
                    "SELECT m.item_name, oi.quantity, m.price, oi.total_price " +
                            "FROM order_items oi JOIN menu m " +
                            "ON oi.item_id=m.item_id " +
                            "WHERE oi.order_id=?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            double grandTotal = 0;

            System.out.println("\n------ BILL ------");

            while (rs.next()) {

                String name = rs.getString("item_name");
                int qty = rs.getInt("quantity");
                double price = rs.getDouble("price");
                double total = rs.getDouble("total_price");

                grandTotal += total;

                System.out.println(name + " | " + qty + " x " + price + " = ₹" + total);
            }

            System.out.println("----------------------");
            System.out.println("GRAND TOTAL = ₹" + grandTotal);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {

        int choice;

        do {

            System.out.println("\n--- Restaurant Order System ---");
            System.out.println("1 View Menu");
            System.out.println("2 Place Order");
            System.out.println("3 Cancel Order");
            System.out.println("4 Generate Bill");
            System.out.println("5 Exit");

            System.out.println("Enter Choice:");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    showMenu();
                    break;

                case 2:
                    placeOrder();
                    break;

                case 3:
                    cancelOrder();
                    break;

                case 4:
                    generateBill();
                    break;

                case 5:
                    System.out.println("Exit");
                    break;

                default:
                    System.out.println("Invalid Choice");
            }

        } while (choice != 5);
    }
}