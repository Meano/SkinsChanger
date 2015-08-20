package skinschanger.bukkit;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import skinschanger.bukkit.listeners.LoginListener;
import skinschanger.bukkit.storage.SkinStorage;

public class Skins extends JavaPlugin implements Listener {
	private static Skins instance;
	private Logger log;
	SkinStorage storage = new SkinStorage();
	private FileConfiguration PluginConfig;
	public String[] Players;
	public List<String> PlayersAllow = new ArrayList<String>();

	public static Skins getInstance() {
		return instance;
	}

	public void logInfo(String message) {
		this.log.info(message);
	}

	public SkinStorage getSkinStorage() {
		return this.storage;
	}

	public void onEnable() {
		instance = this;
		File PluginConfigFile = new File(getDataFolder(), "config.yml");
		if (!PluginConfigFile.exists()) {
			saveDefaultConfig();
		}
		PluginConfig = getConfig();
		PluginConfig.getInt("Config.Version");
		Players = PluginConfig.getConfigurationSection("Players").getKeys(false).toArray(new String[0]);
		PlayersAllow.clear();
		for (String p : Players) {
			if (PluginConfig.getBoolean("Players." + p))
				PlayersAllow.add(p);
		}
		log = getLogger();
		getCommand("skin").setExecutor(new Commands());
		getCommand("skina").setExecutor(new AdminCommands());
		getServer().getPluginManager().registerEvents(new LoginListener(), this);
	}

	public void AddPlayer(String Name) {
		PluginConfig.set("Players." + Name, true);
		Players = PluginConfig.getConfigurationSection("Players").getKeys(false).toArray(new String[0]);
		PlayersAllow.clear();
		for (String p : Players) {
			if (PluginConfig.getBoolean("Players." + p))
				PlayersAllow.add(p);
		}
		saveConfig();
	}

	public void DuePlayer(String Name) {
		PluginConfig.set("Players." + Name, false);
		Players = PluginConfig.getConfigurationSection("Players").getKeys(false).toArray(new String[0]);
		PlayersAllow.clear();
		for (String p : Players) {
			if (PluginConfig.getBoolean("Players." + Name))
				PlayersAllow.add(p);
		}
		saveConfig();
	}

	public void onDisable() {
		instance = null;
	}
}