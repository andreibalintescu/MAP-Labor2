package confectionery.Repository.DataBase;


import confectionery.Model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderDBRepository extends DBRepository<Order> {

     CakeDBRepository cakeRepo;
     DrinkDBRepository drinkRepo;

    public OrderDBRepository(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
        cakeRepo = new CakeDBRepository(dbUrl, dbUser, dbPassword);
        drinkRepo = new DrinkDBRepository(dbUrl, dbUser, dbPassword);
        createTableIfNotExists();
        createOrderProducts();
    }

    private void createTableIfNotExists() {
        String sql = """
        CREATE TABLE IF NOT EXISTS Orders (
            orderID INT PRIMARY KEY,
            date DATE
        );
    """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Error creating Orders table", e);
        }
    }

    private void createOrderProducts() {
        String sql = """
        CREATE TABLE IF NOT EXISTS OrderProducts (
            orderID INT,
            productID INT,
            productType VARCHAR(255),
            PRIMARY KEY (orderID, productID),
            FOREIGN KEY (orderID) REFERENCES Orders(orderID) ON DELETE CASCADE
        );
    """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Error creating OrderProducts table", e);
        }
    }
    @Override
    public void create(Order order) {
        String sql = "INSERT INTO Orders (orderID, date) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, order.getID());
            statement.setDate(2, Date.valueOf(order.getDate()));

            statement.executeUpdate();


            String insertProductSql = "INSERT INTO OrderProducts (orderID, productID, productType) VALUES (?, ?, ?)";
            try (PreparedStatement productStatement = connection.prepareStatement(insertProductSql)) {
                for (Product product : order.getProducts()) {
                    productStatement.setInt(1, order.getID());
                    productStatement.setInt(2, product.getID());
                    productStatement.setString(3, product.getClass().getSimpleName());
                    productStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Order order) {

    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM Orders WHERE orderID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();


            String deleteProductsSql = "DELETE FROM OrderProducts WHERE orderID = ?";
            try (PreparedStatement deleteStatement = connection.prepareStatement(deleteProductsSql)) {
                deleteStatement.setInt(1, id);
                deleteStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Order> getAll() {
        String sql = "SELECT * FROM Orders";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            List<Order> orders = new ArrayList<>();
            while (resultSet.next()) {
                int orderID = resultSet.getInt("orderID");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                List<Product> products = getProductsForOrder(orderID);
                orders.add(new Order(products, orderID, date));
            }
            return orders;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Product> getProductsForOrder(int orderID) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM OrderProducts WHERE orderID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, orderID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int productID = resultSet.getInt("productID");
                String productType = resultSet.getString("productType");

                if ("Cake".equalsIgnoreCase(productType)) {
                    products.add(cakeRepo.get(productID));
                } else if ("Drink".equalsIgnoreCase(productType)) {
                    products.add(drinkRepo.get(productID));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    @Override
    public Order get(Integer id) {
        String sql = "SELECT * FROM Orders WHERE orderID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                LocalDate date = resultSet.getDate("date").toLocalDate();
                List<Product> products = getProductsForOrder(id);
                return new Order(products, id, date);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
