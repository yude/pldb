package jp.yude.pldb;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;

public class Config {
    private final Plugin plugin;

    private FileConfiguration config = null;
    private String db_host;
    private Integer db_port;
    private String db_name;
    private String db_user;
    private String db_password;

    public Config(Plugin plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    private void loadConfig() {
        // Save config to the file.
        plugin.saveDefaultConfig();
        // Called when this plugin is loaded via reload.
        if (config != null) {
            plugin.reloadConfig();
        }

        config = plugin.getConfig();

        // Retrieve config values.
        db_host = config.getString("db_host");
        db_port = config.getInt("db_port");
        db_name = config.getString("db_name");
        db_user = config.getString("db_user");
        db_password = config.getString("db_password");
    }

    public String getDbHost() {
        return db_host;
    }
    public Integer getDbPort() {
        return db_port;
    }
    public String getDbName() {
        return db_name;
    }
    public String getDbUser() {
        return db_user;
    }
    public String getDbPassword() {
        return db_password;
    }
}
