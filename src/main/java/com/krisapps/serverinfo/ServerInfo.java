package com.krisapps.serverinfo;

import com.krisapps.serverinfo.BanHistory.GetRecords;
import com.krisapps.serverinfo.BanHistory.HistoryManager;
import com.krisapps.serverinfo.BanHistory.PurgeHistory;
import com.krisapps.serverinfo.BanHistory.ShowEntry;
import com.krisapps.serverinfo.EventHandlers.OnBanCommand;
import com.krisapps.serverinfo.EventHandlers.OnPlayerKickedHandler;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;

public final class ServerInfo extends JavaPlugin {


    public FileConfiguration configurationFile;
    public File conf = new File(this.getDataFolder(), "configuration.yml");

    public FileConfiguration banHistoryFile;
    public File banHistoryConf = new File(this.getDataFolder(), "ban-history.yml");


    @Override
    public void onEnable() {
        try {
            configure();
        } catch (IOException | InvalidConfigurationException e) {
            getLogger().severe("Could not generate a configuration file for the plugin. The error reads as follows:\n===========================================\n");
            e.printStackTrace();
            getLogger().info("\n===========================================");
        }

        register();

    }

    //Configure files.
    void configure() throws IOException, InvalidConfigurationException {
        getLogger().info("Configuring plugin files...");

        if (!conf.exists() || !conf.getParentFile().exists()){
            getLogger().warning("Plugin configuration file not found. Generating a new one for you :)");
            conf.getParentFile().mkdirs();
            saveResource("configuration.yml", true);
        }

        if (!banHistoryConf.exists() || !banHistoryConf.getParentFile().exists()){
            getLogger().warning("Ban History file not found. Generating a new one for you :)");
            banHistoryConf.getParentFile().mkdirs();
            banHistoryConf.createNewFile();
        }

        banHistoryFile = new YamlConfiguration();
        configurationFile = new YamlConfiguration();

        try{
            configurationFile.load(conf);
            banHistoryFile.load(banHistoryConf);
        }catch (FileNotFoundException e){
            getLogger().warning("Error configuring files.");
            e.printStackTrace();
        }
    }

    //Register commands and tab completion.
    void register() {
        getLogger().info("Registering plugin commands...");

        //Commands
        getCommand("players").setExecutor(new Players());
        getCommand("playerinfo").setExecutor(new PlayerInfo());
        getCommand("serverinfo").setExecutor(new ServerCommand());
        getCommand("baninfo").setExecutor(new BanInfo());
        getCommand("getbans").setExecutor(new GetRecords(this));
        getCommand("showbanentry").setExecutor(new ShowEntry(this));
        getCommand("purgehistory").setExecutor(new PurgeHistory(this));

        HistoryManager historyManager = new HistoryManager(this, this.getLogger());

        //EventListeners
        getServer().getPluginManager().registerEvents(new OnPlayerKickedHandler(this), this);
        getServer().getPluginManager().registerEvents(new OnBanCommand(this), this);

        //Tab completion
        getCommand("baninfo").setTabCompleter(new com.krisapps.serverinfo.TabCompletion.BanInfo());
        getCommand("showbanentry").setTabCompleter(new com.krisapps.serverinfo.TabCompletion.ShowEntry(this));
        getCommand("getbans").setTabCompleter(new com.krisapps.serverinfo.TabCompletion.GetRecords());
        getCommand("purgehistory").setTabCompleter(new com.krisapps.serverinfo.TabCompletion.PurgeHistory(this));

        getLogger().info("Plugin setup complete!");
    }
    @Override
    public void onDisable() {
        getLogger().info("Plugin successfully disabled.");
    }
}
