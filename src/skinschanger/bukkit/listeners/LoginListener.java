package skinschanger.bukkit.listeners;

import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedSignedProperty;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import skinschanger.bukkit.Skins;
import skinschanger.bukkit.storage.SkinStorage;
import skinschanger.shared.format.SkinProfile;
import skinschanger.shared.format.SkinProperty;
import skinschanger.shared.utils.SkinGetUtils;

public class LoginListener implements Listener {
	SkinStorage storage = new SkinStorage();

	public SkinStorage getSkinStorage() {
		return this.storage;
	}

	@EventHandler
	public void onPreLoginEvent(AsyncPlayerPreLoginEvent event) {
		String name = event.getName();
		boolean isAllowSkin = false;
		for (String p : Skins.getInstance().PlayersAllow) {
			if (p.equalsIgnoreCase(name))
				isAllowSkin = true;
		}
		if (!isAllowSkin) {
			// Skins.getInstance().logInfo("��Ҳ���Ƥ�������б��С�");
			return;
		}
		Skins.getInstance().getSkinStorage().loadData();
		// Skins.getInstance().getLogger().info((Skins.getInstance().getSkinStorage().hasLoadedSkinData(name))+"�д�Ƥ��"
		// +
		// Skins.getInstance().getSkinStorage().getLoadedSkinData(name).isTooDamnOld());
		if (Skins.getInstance().getSkinStorage().hasLoadedSkinData(name)) {
			Skins.getInstance().logInfo("���" + name + "��Ƥ���Ѿ����档");
			return;
		}
		try {
			SkinProfile profile = SkinGetUtils.getSkinProfile(name);
			Skins.getInstance().getSkinStorage().addSkinData(name, profile);
			Skins.getInstance().logInfo("���" + name + "��Ƥ��˳��ƥ�䲢���档");
		} catch (SkinGetUtils.SkinFetchFailedException e) {
			Skins.getInstance().logInfo(name + "��Ƥ��ƥ��ʧ��: " + e.getMessage());
		}
	}

	@EventHandler
	public void onLoginEvent(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		String name = event.getPlayer().getName();
		boolean isAllowSkin = false;
		for (String p : Skins.getInstance().PlayersAllow) {
			if (p.equalsIgnoreCase(name))
				isAllowSkin = true;
		}
		if (!isAllowSkin) {
			// Skins.getInstance().logInfo("��Ҳ���Ƥ�������б��С�");
			return;
		}
		Skins.getInstance().getSkinStorage().loadData();
		if (Skins.getInstance().getSkinStorage().hasLoadedSkinData(name)) {
			SkinProperty skinproperty = Skins.getInstance().getSkinStorage().getLoadedSkinData(player.getName()).getPlayerSkinProperty();
			WrappedGameProfile wrappedprofile = WrappedGameProfile.fromPlayer(player);
			WrappedSignedProperty wrappedproperty = WrappedSignedProperty.fromValues(skinproperty.getName(), skinproperty.getValue(), skinproperty.getSignature());
			if (!wrappedprofile.getProperties().containsKey(wrappedproperty.getName()))
				wrappedprofile.getProperties().put(wrappedproperty.getName(), wrappedproperty);
		}
	}
}
