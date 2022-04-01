package jp.yude.pldb;

import org.bukkit.plugin.java.JavaPlugin;

public final class Pldb extends JavaPlugin {

    public static Pldb instance;
    public static Config config;
    public static Database database;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        // Load configuration
        config = new Config(this);

        // Establish connection to the database server.
        database = new Database(this);

        // Register events
        getServer().getPluginManager().registerEvents(new Event(), this);
        this.getCommand("bio").setExecutor(new CommandBio());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
