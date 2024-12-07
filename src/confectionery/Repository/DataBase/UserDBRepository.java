package confectionery.Repository.DataBase;

import confectionery.Model.Admin;
import confectionery.Model.Client;
import confectionery.Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDBRepository extends DBRepository<User> {

    public UserDBRepository(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
        Client();
        Admin();
    }

    private void Client() {
        String sql = """
            CREATE TABLE IF NOT EXISTS Client (
                ID INT PRIMARY KEY,
                name VARCHAR(255) NOT NULL,
                adress VARCHAR(255) NOT NULL
            );
        """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Error creating Client table", e);
        }
    }
    private void Admin() {
        String sql = """
            CREATE TABLE IF NOT EXISTS Admin (
                ID INT PRIMARY KEY,
                name VARCHAR(255) NOT NULL,
                adress VARCHAR(255) NOT NULL,
                username VARCHAR(255) NOT NULL,
                password VARCHAR(255) NOT NULL
            );
        """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Error creating Admin table", e);
        }
    }


    @Override
    public void create(User user) {
        String sql;
        if (user instanceof Admin) {
            sql = "INSERT INTO Admin (ID, name, adress, password, username) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                Admin admin = (Admin) user;
                statement.setInt(1, admin.getID());
                statement.setString(2, admin.getName());
                statement.setString(3, admin.getAddress());
                statement.setString(4, admin.getPassword());
                statement.setString(5, admin.getEmail());
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if (user instanceof Client) {
            sql = "INSERT INTO Client (ID, name, adress) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                Client client = (Client) user;
                statement.setInt(1, client.getID());
                statement.setString(2, client.getName());
                statement.setString(3, client.getAddress());
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void update(User user) {
        String sql;
        if (user instanceof Admin) {
            sql = "UPDATE Admin SET name = ?, adress = ?, password = ?, username = ? WHERE ID = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                Admin admin = (Admin) user;
                statement.setString(1, admin.getName());
                statement.setString(2, admin.getAddress());
                statement.setString(3, admin.getPassword());
                statement.setString(4, admin.getEmail());
                statement.setInt(5, admin.getID());
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if (user instanceof Client) {
            sql = "UPDATE Client SET name = ?, adress = ? WHERE ID = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                Client client = (Client) user;
                statement.setString(1, client.getName());
                statement.setString(2, client.getAddress());
                statement.setInt(3, client.getID());
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void delete(Integer id) {
        String sqlAdmin = "DELETE FROM Admin WHERE ID =?";
        String sqlClient = "DELETE FROM Client WHERE ID=?";
        try (PreparedStatement statement = connection.prepareStatement(sqlAdmin)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {

            try (PreparedStatement statement = connection.prepareStatement(sqlClient)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }


    @Override
    public User get(Integer id) {
        String sqlAdmin = "SELECT * FROM Admin WHERE ID=?";
        String sqlClient = "SELECT * FROM Client WHERE ID=?";

        try (PreparedStatement statement = connection.prepareStatement(sqlAdmin)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return extractAdminFromResultSet(resultSet);
            }
        } catch (SQLException e) {

            try (PreparedStatement statement = connection.prepareStatement(sqlClient)) {
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return extractClientFromResultSet(resultSet);
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();


        String sqlAdmin = "SELECT * FROM Admin";
        try (PreparedStatement statement = connection.prepareStatement(sqlAdmin)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(extractAdminFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        String sqlClient = "SELECT * FROM Client";
        try (PreparedStatement statement = connection.prepareStatement(sqlClient)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(extractClientFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    private Admin extractAdminFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("ID");
        String name = resultSet.getString("name");
        String address = resultSet.getString("adress");
        String password = resultSet.getString("password");
        String email = resultSet.getString("username");

        return new Admin(password, email, id, name, address);
    }

    private Client extractClientFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("ID");
        String name = resultSet.getString("name");
        String address = resultSet.getString("adress");

        return new Client(name, address, id);
    }
}
