package jp.yude.pldb;

import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static jp.yude.pldb.Pldb.config;

public class Database {
    private static Connection connection;
    private final Plugin plugin;

    public Database(Plugin plugin) {
        this.plugin = plugin;

        // MySQL configurations / variables
        String db_host = config.getDbHost();
        Integer db_port = config.getDbPort();
        String db_name = config.getDbName();
        String db_user = config.getDbUser();
        String db_password = config.getDbPassword();
        String db_uri = "jdbc:mysql://" + db_host + ":" + db_port + "/" + db_name + "?autoReconnect=true";

        try {
            connection = DriverManager.getConnection(db_uri, db_user, db_password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Initialize table
        String sql = "CREATE TABLE IF NOT EXISTS `players` (" +
                " `uuid` VARCHAR(50) COLLATE utf8mb4_unicode_ci," +
                " `name` VARCHAR(50) COLLATE utf8mb4_unicode_ci," +
                " `last_joined` BIGINT(50) COLLATE utf8mb4_unicode_ci," +
                " `first_joined` BIGINT(50) COLLATE utf8mb4_unicode_ci," +
                " UNIQUE (`uuid`)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;" ;
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }


}