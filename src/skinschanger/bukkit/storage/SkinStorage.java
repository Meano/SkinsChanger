package skinschanger.bukkit.storage;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import skinschanger.bukkit.Skins;
import skinschanger.shared.format.SkinProfile;
import skinschanger.shared.format.SkinProperty;

public class SkinStorage {
	private LinkedHashMap<String, SkinProfile> skins = new LinkedHashMap<String, SkinProfile>(150, 0.75F, true);

	public boolean hasLoadedSkinData(String name) {
		return this.skins.containsKey(name.toLowerCase());
	}

	public void addSkinData(String name, SkinProfile data) {
		this.skins.put(name.toLowerCase(), data);
	}

	public void removeSkinData(String name) {
		this.skins.remove(name.toLowerCase());
	}

	public SkinProfile getLoadedSkinData(String name) {
		return this.skins.get(name.toLowerCase());
	}

	public Map<String, SkinProfile> getSkinData() {
		return Collections.unmodifiableMap(this.skins);
	}

	public void loadData() {
		int loadedSkins = 0;
		File datafile = new File(Skins.getInstance().getDataFolder(), "playerdata.yml");
		FileConfiguration data = YamlConfiguration.loadConfiguration(datafile);
		ConfigurationSection cs = data.getConfigurationSection("");
		if (cs == null) {
			return;
		}
		for (String name : cs.getKeys(false)) {
			if (loadedSkins >= 10000L) {
				return;
			}
			long creationDate = cs.getLong(name + ".timestamp");
			String propertyname = cs.getString(name + ".propertyname");
			String propertyvalue = cs.getString(name + ".propertyvalue");
			String propertysignature = cs.getString(name + ".propertysignature");
			SkinProfile skinData = new SkinProfile(new SkinProperty(propertyname, propertyvalue, propertysignature), creationDate);
			addSkinData(name, skinData);
			loadedSkins++;
		}
	}

	public void saveData() {
		File datafile = new File(Skins.getInstance().getDataFolder(), "playerdata.yml");
		FileConfiguration data = new YamlConfiguration();
		for (Map.Entry<String, SkinProfile> entry : getSkinData().entrySet()) {
			data.set((String) entry.getKey() + ".timestamp", Long.valueOf(((SkinProfile) entry.getValue()).getCreationDate()));
			data.set((String) entry.getKey() + ".propertyname", ((SkinProfile) entry.getValue()).getPlayerSkinProperty().getName());
			data.set((String) entry.getKey() + ".propertyvalue", ((SkinProfile) entry.getValue()).getPlayerSkinProperty().getValue());
			data.set((String) entry.getKey() + ".propertysignature", ((SkinProfile) entry.getValue()).getPlayerSkinProperty().getSignature());
		}
		try {
			data.save(datafile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}