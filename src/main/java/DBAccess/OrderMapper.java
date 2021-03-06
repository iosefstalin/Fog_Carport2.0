package DBAccess;

import Exceptions.OrderException;
import Model.Order;

import java.sql.*;
import java.util.ArrayList;

public class OrderMapper {
    public static ArrayList<Order> getOrderList() {

        ArrayList<Order> orderList = new ArrayList<>();

        Connection connection;
        PreparedStatement ps;
        ResultSet resultSet;
        String sqlQuery = "SELECT * FROM orders";

        try {
            connection = Connector.connection();
            ps = connection.prepareStatement(sqlQuery);
            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                int order_id = resultSet.getInt("order_id");
                int customer_id = resultSet.getInt("customer_id");
                int size = resultSet.getInt("size");
                int length = resultSet.getInt("length");
                int width = resultSet.getInt("width");
                int height = resultSet.getInt("height");
                int roof_type = resultSet.getInt("roof_type");
                int roof_sort = resultSet.getInt("roof_sort");
                int shed = resultSet.getInt("shed");
                int shedtype = resultSet.getInt("shedtype");
                int shed_length = resultSet.getInt("shed_length");
                int shed_width = resultSet.getInt("shed_width");
                int order_status = resultSet.getInt("order_status");
                String date = resultSet.getString("date");

                Order order = new Order(order_id, customer_id, size, length, width, height, roof_type, roof_sort, shed ,shedtype, shed_length, shed_width, order_status, date);
                orderList.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderList;
    }

    public static ArrayList<Order> getOrderListForCustomer(int customer_id) {

        ArrayList<Order> orderList = new ArrayList<>();

        Connection connection;
        PreparedStatement ps;
        ResultSet resultSet;
        String sqlQuery = "SELECT * FROM orders WHERE customer_id = ?";

        try {
            connection = Connector.connection();
            ps = connection.prepareStatement(sqlQuery);
            ps.setInt(1, customer_id);
            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                int order_id = resultSet.getInt("order_id");
                int size = resultSet.getInt("size");
                int length = resultSet.getInt("length");
                int width = resultSet.getInt("width");
                int height = resultSet.getInt("height");
                int roof_type = resultSet.getInt("roof_type");
                int roof_sort = resultSet.getInt("roof_sort");
                int shed = resultSet.getInt("shed");
                int shedtype = resultSet.getInt("shedtype");
                int shed_length = resultSet.getInt("shed_length");
                int shed_width = resultSet.getInt("shed_width");
                int order_status = resultSet.getInt("order_status");
                String date = resultSet.getString("date");

                Order order = new Order(order_id, customer_id, size, length, width, height, roof_type, roof_sort, shed ,shedtype, shed_length, shed_width, order_status, date);
                orderList.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderList;
    }

    public static void createOrder( Order order ) throws OrderException {
        try {
            Connection con = Connector.connection();
            String SQL = "INSERT INTO orders (customer_id, size, length, width, height, roof_type, roof_sort, shed, shedtype, shed_length, shed_width, order_status, date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement( SQL, Statement.RETURN_GENERATED_KEYS );
            ps.setInt( 1, order.getCustomer_id());
            ps.setInt( 2, order.getSize());
            ps.setInt( 3, order.getLength());
            ps.setInt( 4, order.getWidth());
            ps.setInt( 5, order.getHeight());
            ps.setInt(6, order.getRoof_type());
            ps.setInt(7, order.getRoof_sort() );
            ps.setInt(8, order.getShed());
            ps.setInt(9, order.getShedtype());
            ps.setInt(10, order.getShed_length());
            ps.setInt(11, order.getShed_width());
            ps.setInt(12, order.getOrder_status());
            ps.setString(13, order.getDate());
            ps.executeUpdate();
            ResultSet ids = ps.getGeneratedKeys();
            ids.next();
            int id = ids.getInt( 1 );
            order.setOrder_id(id);
        } catch ( SQLException | ClassNotFoundException ex ) {
            throw new OrderException( ex.getMessage() );
        }
    }

    public static Order getOrder(int id) {

        Order order = null;
        String idString = "" + id;

        Connection connection;
        PreparedStatement ps;
        ResultSet resultSet;
        String sqlQuery = "SELECT * FROM orders WHERE order_id = ?";

        try {
            connection = Connector.connection();
            ps = connection.prepareStatement(sqlQuery);
            ps.setString(1, idString);
            resultSet = ps.executeQuery();

            if (resultSet.next()) {
                int order_id = resultSet.getInt("order_id");
                int customer_id = resultSet.getInt("customer_id");
                int size = resultSet.getInt("size");
                int length = resultSet.getInt("length");
                int width = resultSet.getInt("width");
                int height = resultSet.getInt("height");
                int roof_type = resultSet.getInt("roof_type");
                int roof_sort = resultSet.getInt("roof_sort");
                int shed = resultSet.getInt("shed");
                int shedtype = resultSet.getInt("shedtype");
                int shed_length = resultSet.getInt("shed_length");
                int shed_width = resultSet.getInt("shed_width");
                int order_status = resultSet.getInt("order_status");
                String date = resultSet.getString("date");

                order = new Order(order_id, customer_id, size, length, width, height, roof_type, roof_sort, shed, shedtype, shed_length, shed_width, order_status, date);
                return order;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return order;
    }

    public static String changeStatus(String id, int status) {
        Connection connection;
        PreparedStatement ps;
        String sqlQuery = "UPDATE orders SET order_status = ? WHERE order_id = ?";

        try {
            connection = Connector.connection();
            ps = connection.prepareStatement(sqlQuery);
            ps.setInt(1, status);
            ps.setInt(2, Integer.parseInt(id));
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();

            return "failed";
        }
        return "done";
    }

    public static Order updateOrder(Order order) {
        Connection connection;
        PreparedStatement ps;
        String sqlQuery = "UPDATE orders SET customer_id = ?, size = ?, length = ?, width = ?, length = ?, roof_type = ?, roof_sort = ?, shed = ?, shedtype = ?, shed_length = ?, shed_width = ?, order_status = ?, date = ? WHERE order_id = ?";

        try {
            connection = Connector.connection();
            ps = connection.prepareStatement(sqlQuery);
            ps.setInt(1, order.getCustomer_id());
            ps.setInt(2, order.getSize());
            ps.setInt(3, order.getLength());
            ps.setInt(4, order.getWidth());
            ps.setInt(5, order.getHeight());
            ps.setInt(6, order.getRoof_type());
            ps.setInt(7, order.getRoof_sort());
            ps.setInt(8, order.getShed());
            ps.setInt(9, order.getShedtype());
            ps.setInt(10, order.getShed_length());
            ps.setInt(11, order.getShed_width());
            ps.setInt(12, order.getOrder_status());
            ps.setString(13, order.getDate());
            ps.setInt(14, order.getOrder_id());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return order;
    }

    public static String deleteOrder(Order order) {
        Connection connection;
        PreparedStatement ps;
        String sqlQuery = "DELETE FROM orders WHERE order_id = ?";

        try {
            connection = Connector.connection();
            ps = connection.prepareStatement(sqlQuery);
            ps.setInt(1, order.getOrder_id());
            ps.executeUpdate();

            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }
}
