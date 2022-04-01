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
        // Initialize tables
        //// Table: `players`
        String sql_players = "CREATE TABLE IF NOT EXISTS `players` (" +
                " `uuid` VARCHAR(50) COLLATE utf8mb4_unicode_ci," +
                " `name` VARCHAR(50) COLLATE utf8mb4_unicode_ci," +
                " `last_joined` BIGINT(50)," +
                " `first_joined` BIGINT(50)," +
                " `playtime` BIGINT(50)," +
                " `op` BIT," +
                " UNIQUE (`uuid`)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;" ;
        try {
            PreparedStatement stmt = connection.prepareStatement(sql_players);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Table: `bio`
        String sql_bio = "CREATE TABLE IF NOT EXISTS `bio` (" +
                "  `uuid` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL UNIQUE," +
                "  `bio` varchar(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;" ;
        try {
            PreparedStatement stmt = connection.prepareStatement(sql_bio);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }


}
