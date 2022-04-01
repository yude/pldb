package jp.yude.pldb;

import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Event implements Listener {
    @EventHandler
    private void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        Connection connection = Pldb.database.getConnection();
        // Save player's data to db
        try {
            String sql_search = "SELECT * FROM `players` WHERE `uuid` = ?;";
            PreparedStatement stmt_search = connection.prepareStatement(sql_search);
            stmt_search.setString(1, player.getUniqueId().toString());
            ResultSet results_search = stmt_search.executeQuery();

            // Check if the player has already joined to the server
            if (results_search.next()) { // Known player
                String query = "UPDATE `players` SET `last_joined` = ?, `name` = ?, `op` = ?, `playtime` = ? WHERE `uuid` = ?;";
                PreparedStatement stmt = connection.prepareStatement(query);

                // Insert values to use in query.
                // Player's the last joined timestamp
                stmt.setLong(1, System.currentTimeMillis() / 1000L);
                // Player's in-game name
                stmt.setString(2, player.getDisplayName());
                //// Check if player is a server operator or not.
                if (player.isOp()) {
                    stmt.setBoolean(3, true);
                } else {
                    stmt.setBoolean(3, false);
                }
                //// Player's total playtime. PLAY_ONE_MINUTE actually returns in ticks, so it also converts to second.
                //// 20 ticks = 1 seconds
                stmt.setLong(4, player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20);
                // Player's UUID (the record destination)
                stmt.setString(5, player.getUniqueId().toString());

                // Execute query.
                stmt.executeUpdate();
            } else { // Newly joined player
                String query = "INSERT INTO `players` (uuid, name, op, last_joined, first_joined, playtime) VALUES (?, ?, ?, ?, ?, ?);";
                PreparedStatement stmt = connection.prepareStatement(query);

                // Insert values to use in query.
                //// Player's UUID
                stmt.setString(1, player.getUniqueId().toString());
                //// Player's in-game name
                stmt.setString(2, player.getDisplayName());
                //// Check if player is a server operator or not.
                if (player.isOp()) {
                    stmt.setBoolean(3, true);
                } else {
                    stmt.setBoolean(3, false);
                }
                //// Player's the last joined timestamp (unix time, sec)
                stmt.setLong(4, System.currentTimeMillis() / 1000L);
                //// Player's the first joined timestamp  (unix time, sec)
                stmt.setLong(5, System.currentTimeMillis() / 1000L);
                //// Player's total playtime. PLAY_ONE_MINUTE actually returns in ticks, so it also converts to second.
                //// 20 ticks = 1 seconds
                stmt.setLong(6, player.getStatistic(Statistic.PLAY_ONE_MINUTE));

                // Execute query.
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // In case MCWebIntegration is loaded after server starts
    @EventHandler
    private void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Connection connection = Pldb.database.getConnection();
        // Save player's data to db
        String sql_search = "SELECT * FROM `players` WHERE uuid = '" + player.getUniqueId() +"';";
        try {
            PreparedStatement stmt_search = connection.prepareStatement(sql_search);
            ResultSet results_search = stmt_search.executeQuery();
            if (results_search.next()) { // Known player
                String query = "UPDATE `players` SET `last_joined` = ?, `name` = ?, `op` = ?, `playtime` = ? WHERE `uuid` = ?;";
                PreparedStatement stmt = connection.prepareStatement(query);

                // Insert values to use in query.
                // Player's the last joined timestamp
                stmt.setLong(1, System.currentTimeMillis() / 1000L);
                // Player's in-game name
                stmt.setString(2, player.getDisplayName());
                //// Check if player is a server operator or not.
                if (player.isOp()) {
                    stmt.setBoolean(3, true);
                } else {
                    stmt.setBoolean(3, false);
                }
                //// Player's total playtime. PLAY_ONE_MINUTE actually returns in ticks, so it also converts to second.
                //// 20 ticks = 1 seconds
                stmt.setLong(4, player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20);
                // Player's UUID (the record destination)
                stmt.setString(5, player.getUniqueId().toString());

                // Execute query.
                stmt.executeUpdate();
            } else { // Newly joined player
                String query = "INSERT INTO `players` (uuid, name, op, last_joined, first_joined, playtime) VALUES (?, ?, ?, ?, ?, ?);";
                PreparedStatement stmt = connection.prepareStatement(query);

                // Insert values to use in query.
                //// Player's UUID
                stmt.setString(1, player.getUniqueId().toString());
                //// Player's in-game name
                stmt.setString(2, player.getDisplayName());
                //// Check if player is a server operator or not.
                if (player.isOp()) {
                    stmt.setBoolean(3, true);
                } else {
                    stmt.setBoolean(3, false);
                }
                //// Player's the last joined timestamp (unix time, sec)
                stmt.setLong(4, System.currentTimeMillis() / 1000L);
                //// Player's the first joined timestamp  (unix time, sec)
                stmt.setLong(5, System.currentTimeMillis() / 1000L);
                //// Player's total playtime. PLAY_ONE_MINUTE actually returns in ticks, so it also converts to second.
                //// 20 ticks = 1 seconds
                stmt.setLong(6, player.getStatistic(Statistic.PLAY_ONE_MINUTE));

                // Execute query.
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
