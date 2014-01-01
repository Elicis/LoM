package de.elicis.lom.data;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Config {
	private FileConfiguration customConfig = null;
	File customConfigurationFile = null;

	public Config() {
	}

	public void reloadConfig(String str) {
		if (customConfigurationFile == null) {
			customConfigurationFile = new File(de.elicis.lom.Main.getPlugin().getDataFolder(), str);
		}
		setCustomConfig(YamlConfiguration
				.loadConfiguration(customConfigurationFile));
		InputStream defConfigStream = de.elicis.lom.Main.getPlugin().getResource(str);
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration
					.loadConfiguration(defConfigStream);
			getCustomConfig().setDefaults(defConfig);
		}
	}

	public FileConfiguration getConfig(String str) {
		if (getCustomConfig() == null) {
			reloadConfig(str);
		}
		return getCustomConfig();
	}

	public void saveCustomConfig() {
		if (getCustomConfig() == null || customConfigurationFile == null) {
			return;
		}
		try {
			getCustomConfig().save(customConfigurationFile);
		} catch (IOException ex) {
			Logger.getLogger(JavaPlugin.class.getName()).log(Level.SEVERE,
					"Could not write " + customConfigurationFile, ex);
		}
	}

	/**
	 * @return the customConfig
	 */
	public FileConfiguration getCustomConfig() {
		return customConfig;
	}

	/**
	 * @param customConfig the customConfig to set
	 */
	public void setCustomConfig(FileConfiguration customConfig) {
		this.customConfig = customConfig;
	}
}
