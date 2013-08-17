package de.elicis.lom;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Config {
	private static Main plugin;
	FileConfiguration customConfig = null;
	File customConfigurationFile = null;

	public Config(Main t) {
		plugin = t;

	}

	public void reloadConfig(String str) {
		if (customConfigurationFile == null) {
			customConfigurationFile = new File(plugin.getDataFolder(), str);
		}
		customConfig = YamlConfiguration
				.loadConfiguration(customConfigurationFile);
		InputStream defConfigStream = plugin.getResource(str);
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration
					.loadConfiguration(defConfigStream);
			customConfig.setDefaults(defConfig);
		}
	}

	public FileConfiguration getConfig(String str) {
		if (customConfig == null) {
			reloadConfig(str);
		}
		return customConfig;
	}

	public void saveCustomConfig() {
		if (customConfig == null || customConfigurationFile == null) {
			return;
		}
		try {
			customConfig.save(customConfigurationFile);
		} catch (IOException ex) {
			Logger.getLogger(JavaPlugin.class.getName()).log(Level.SEVERE,
					"Could not write " + customConfigurationFile, ex);
		}
	}
}
