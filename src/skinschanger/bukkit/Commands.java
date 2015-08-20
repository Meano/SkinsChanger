package skinschanger.bukkit;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import skinschanger.shared.format.SkinProfile;
import skinschanger.shared.utils.SkinGetUtils;

public class Commands implements CommandExecutor {
	private ExecutorService executor = Executors.newSingleThreadExecutor();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("change"))
				return onChangeCommand(sender, cmd, label, args);
			if (args[0].equalsIgnoreCase("default"))
				return onDefaultCommand(sender, cmd, label, args);
			if (args[0].equalsIgnoreCase("help"))
				return onHelpCommand(sender, cmd, label, args);
		}
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eUse '&c/skin help&e' for help."));
		return true;
	}

	public boolean onHelpCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("skinschanger.use")) {
			sender.sendMessage("You don't have permission to do this");
			return true;
		}
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e]&c============&e[ &aSkinsChanger Help &e]&c============&e["));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e/skin change <skinname> &9-&a Changes your skin. &7&o//requires relog"));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e/skin default &9-&a Reverts your default skin &7&o//requires relog"));
		return false;
	}

	public boolean onChangeCommand(final CommandSender sender, Command cmd, String label, final String[] args) {
		if (!sender.hasPermission("skinschanger.use")) {
			sender.sendMessage("You don't have permission to do this");
			return true;
		}
		if (args.length == 2) {
			this.executor.execute(new Runnable() {
				public void run() {
					String name = sender.getName();
					try {
						SkinProfile profile = SkinGetUtils.getSkinProfile(args[1]);
						Skins.getInstance().getSkinStorage().addSkinData(name, profile);
						Skins.getInstance().getSkinStorage().saveData();
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b你已经成功更换了你的皮肤! (重新登陆来查看皮肤)"));
					} catch (SkinGetUtils.SkinFetchFailedException e) {
						sender.sendMessage(ChatColor.RED + "皮肤匹配失败: " + e.getMessage());
					}
				}
			});
		}
		return false;
	}

	public boolean onDefaultCommand(final CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("skinschanger.use")) {
			sender.sendMessage("你没有执行此命令的权限");
			return true;
		}
		if (args.length == 1) {
			this.executor.execute(new Runnable() {
				public void run() {
					String name = sender.getName();
					try {
						SkinProfile profile = SkinGetUtils.getSkinProfile(name);
						Skins.getInstance().getSkinStorage().addSkinData(name, profile);
						Skins.getInstance().getSkinStorage().saveData();
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b你已经重置了你的皮肤! (重新登陆来查看皮肤)"));
					} catch (SkinGetUtils.SkinFetchFailedException e) {
						sender.sendMessage(ChatColor.RED + "皮肤匹配失败: " + e.getMessage());
					}
				}
			});
		}
		return false;
	}
}