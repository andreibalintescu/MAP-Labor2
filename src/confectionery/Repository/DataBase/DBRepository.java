package confectionery.Repository.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
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
}
