package confectionery.Repository.DataBase;



import confectionery.Model.Cake;
import confectionery.Model.ExpirationDate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CakeDBRepository extends DBRepository<Cake> {


    public CakeDBRepository(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
        createTableIfNotExists();

    }
    private void createTableIfNotExists() {
        String sql = """
            CREATE TABLE IF NOT EXISTS Cake (
                ID INT PRIMARY KEY,
                name VARCHAR(255) NOT NULL,
                price DOUBLE NOT NULL,
                weight DOUBLE NOT NULL,
                expirationDate VARCHAR(255) NOT NULL,
                points INT NOT NULL,
                calories INT NOT NULL
            );
        """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Error creating Cake table", e);
        }
    }
    @Override
    public void create(Cake obj) {
        String sql = "INSERT INTO Cake (ID, name, price, weight, expirationDate, points, calories) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, obj.getID());
            statement.setString(2, obj.getName());
            statement.setDouble(3, obj.getPrice());
            statement.setDouble(4, obj.getWeight());
            statement.setString(5, obj.getExpirationDate().toString());
            statement.setInt(6, obj.getPoints());
            statement.setInt(7, obj.getCalories());

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void update(Cake obj) {
        String sql = "UPDATE Cake SET name = ?, price = ?, weight = ?, expirationDate = ?, points = ?, calories = ? WHERE ID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, obj.getName());
            statement.setDouble(2, obj.getPrice());
            statement.setDouble(3, obj.getWeight());
            statement.setString(4, obj.getExpirationDate().toString());
            statement.setInt(5, obj.getPoints());
            statement.setInt(6, obj.getCalories());
            statement.setInt(7, obj.getID());

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM Cake WHERE ID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Cake> getAll() {
        String sql = "SELECT * FROM Cake";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            List<Cake> cakes = new ArrayList<>();

            while (resultSet.next()) {
                cakes.add(extractFromResultSet(resultSet));
            }

            return cakes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Cake get(Integer id) {
        String sql = "SELECT * FROM Cake WHERE ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return extractFromResultSet(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Extracts a Cake object from the result set.
     *
     * @param resultSet the result set containing the data
     * @return the Cake object
     * @throws SQLException if there is an issue with the result set
     */
    public static Cake extractFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("ID");
        String name = resultSet.getString("name");
        double price = resultSet.getDouble("price");
        double weight = resultSet.getDouble("weight");
        String expirationDateStr = resultSet.getString("expirationDate");
        ExpirationDate expirationDate = ExpirationDate.parse(expirationDateStr);
        int points = resultSet.getInt("points");
        int calories = resultSet.getInt("calories");

        return new Cake(id, name, price, weight, expirationDate, points, calories);
    }
}


