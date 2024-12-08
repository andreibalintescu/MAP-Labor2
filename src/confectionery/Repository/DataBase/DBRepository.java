package confectionery.Repository.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import confectionery.Model.HasID;
import confectionery.Repository.IRepository;


public abstract class DBRepository<T extends HasID> implements IRepository<T>, AutoCloseable {

    protected final Connection connection;

    public DBRepository(String dbUrl, String dbUser, String dbPassword) {
        try {

            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }

    @Override
    public void associateOrderWithClient(int orderId, int clientId) {
        String sql = "UPDATE Orders SET clientID = ? WHERE orderID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, clientId);
            stmt.setInt(2, orderId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error associating order with client", e);
        }
    }

}
