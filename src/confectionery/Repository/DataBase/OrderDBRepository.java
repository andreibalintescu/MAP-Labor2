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
        createOrderedDrinks();
        createOrderedCakes();
        createClientOrdersTable();
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

    private void createOrderedDrinks() {
        String sql = """
        CREATE TABLE IF NOT EXISTS OrderedDrinks (
            orderID INT,
            drinkID INT,
            PRIMARY KEY (orderID, drinkID),
            FOREIGN KEY (orderID) REFERENCES Orders(orderID) ON DELETE CASCADE,
            FOREIGN KEY (drinkID) REFERENCES Drinks(drinkID) ON DELETE CASCADE
        );
    """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Error creating OrderedDrinks table", e);
        }
    }

    private void createOrderedCakes() {
        String sql = """
        CREATE TABLE IF NOT EXISTS OrderedCakes (
            orderID INT,
            cakeID INT,
            PRIMARY KEY (orderID, cakeID),
            FOREIGN KEY (orderID) REFERENCES Orders(orderID) ON DELETE CASCADE,
            FOREIGN KEY (cakeID) REFERENCES Cakes(cakeID) ON DELETE CASCADE
        );
    """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Error creating OrderedCakes table", e);
        }
    }

    private void createClientOrdersTable() {
        String sql = """
        CREATE TABLE IF NOT EXISTS ClientOrders (
            clientID INT,
            orderID INT,
            PRIMARY KEY (clientID, orderID),
            FOREIGN KEY (clientID) REFERENCES Client(ID) ON DELETE CASCADE,
            FOREIGN KEY (orderID) REFERENCES Orders(orderID) ON DELETE CASCADE
        );
    """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Error creating ClientOrders table", e);
        }
    }

    public void addClientOrder(int clientID, int orderID) {
        String sql = "INSERT INTO ClientOrders (clientID, orderID) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, clientID);
            statement.setInt(2, orderID);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding client-order relationship", e);
        }
    }

    @Override
    public void create(Order order) {
        String sql = "INSERT INTO Orders (orderID, date) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, order.getID());
            statement.setDate(2, Date.valueOf(order.getDate()));

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Order order) {

    }

    @Override
    public void delete(Integer id) {
        // Delete from ClientOrders
        String deleteClientOrderSql = "DELETE FROM ClientOrders WHERE orderID = ?";
        try (PreparedStatement statement = connection.prepareStatement(deleteClientOrderSql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting client-order relationship", e);
        }

        // Delete from OrderedDrinks
        String deleteOrderedDrinksSql = "DELETE FROM OrderedDrinks WHERE orderID = ?";
        try (PreparedStatement statement = connection.prepareStatement(deleteOrderedDrinksSql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting ordered drinks", e);
        }

        // Delete from OrderedCakes
        String deleteOrderedCakesSql = "DELETE FROM OrderedCakes WHERE orderID = ?";
        try (PreparedStatement statement = connection.prepareStatement(deleteOrderedCakesSql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting ordered cakes", e);
        }

        // Finally, delete from Orders
        String deleteOrderSql = "DELETE FROM Orders WHERE orderID = ?";
        try (PreparedStatement statement = connection.prepareStatement(deleteOrderSql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting order", e);
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

        // Query OrderedCakes
        String sqlCakes = "SELECT c.* FROM OrderedCakes oc INNER JOIN Cakes c ON oc.cakeID = c.cakeID WHERE oc.orderID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sqlCakes)) {
            statement.setInt(1, orderID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                products.add(cakeRepo.get(resultSet.getInt("cakeID")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Query OrderedDrinks
        String sqlDrinks = "SELECT d.* FROM OrderedDrinks od INNER JOIN Drinks d ON od.drinkID = d.drinkID WHERE od.orderID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sqlDrinks)) {
            statement.setInt(1, orderID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                products.add(drinkRepo.get(resultSet.getInt("drinkID")));
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
