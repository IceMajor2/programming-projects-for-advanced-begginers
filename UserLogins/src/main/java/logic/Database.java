package logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.File;
import static userlogins.UserLogins.PATH_TO_DB;

public class Database {

    private Connection conn = null;

    public Database() {
        this.conn = this.setup();
    }

    public boolean exists() {
        return new File(PATH_TO_DB).exists();
    }

    public String[] tableNames() {
        int tablesCount = this.tablesCount();
        String[] tableNames = new String[tablesCount];

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT name FROM sqlite_schema "
                    + "WHERE type = 'table' ");
            int i = 0;
            while (rs.next()) {
                tableNames[i] = rs.getString("name");
                i++;
            }
            return tableNames;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int tablesCount() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT count(*) FROM sqlite_master "
                    + "WHERE type = 'table'");
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int rowCount(String table) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(String.format(
                    "SELECT count(1) FROM %s", table));
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int allRows() {
        String[] tableNames = this.tableNames();
        int totalRows = 0;
        for (String table : tableNames()) {
            totalRows += this.rowCount(table);
        }
        return totalRows;
    }
    
    public void addUser(String username, String passwordHash) {
        try {
            Statement stmt = conn.createStatement();
            String query = String.format("INSERT INTO users(username, password_hash) "
                    + "VALUES ('%s', '%s')", username, passwordHash);
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public boolean isUsernameTaken(String username) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(String.format(
                    "SELECT COUNT(username) FROM users "
                    + "WHERE username = '%s'", username));
            return rs.getInt(1) != 0;
        } catch(SQLException e) {
            return true;
        }
    }

    public boolean recreateDatabase() {
        try {
            conn.close();
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
        boolean beenDeleted = new File(PATH_TO_DB).delete();
        this.setup();
        return beenDeleted;
    }

    private Connection setup() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:database\\users.db");
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users ("
                    + "username VARCHAR UNIQUE NOT NULL,"
                    + "password_hash VARCHAR UNIQUE NOT NULL"
                    + ")");
            stmt.close();
            return conn;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
