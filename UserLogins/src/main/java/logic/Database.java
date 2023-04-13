package logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private Connection conn = null;

    public Database() {
        this.conn = this.openDb();
    }

    private Connection openDb() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:database\\users.db");
            return conn;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
