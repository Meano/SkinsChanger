package skinschanger.bukkit;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import skinschanger.shared.format.SkinProfile;
import skinschanger.shared.utils.SkinGetUtils;

public class AdminCommands implements CommandExecutor {
	private ExecutorService executor = Executors.newSingleThreadExecutor();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("change"))
				return onAdminChangeCommand(sender, cmd, label, args);
			if (args[0].equalsIgnoreCase("default"))
				return onAdminDefaultCommand(sender, cmd, label, args);
			if (args[0].equalsIgnoreCase("help"))
				return onAdminHelpCommand(sender, cmd, label, args);
			if (args[0].equalsIgnoreCase("add") && args.length == 2) {
				Skins.getInstance().AddPlayer(args[1]);
				sender.sendMessage(args[1] + "添加成功");
				return true;
			}
			if (args[0].equalsIgnoreCase("due") && args.length == 2) {
				Skins.getInstance().DuePlayer(args[1]);
				sender.sendMessage(args[1] + "删除成功");
				return true;
			}

		}
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e使用 '&c/skina help&e' 来获得帮助."));
		return true;
	}

	public boolean onAdminHelpCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("skinschanger.admin")) {
			sender.sendMessage("You don't have permission to do this");
			return true;
		}
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e]&c===========&e[ &aSkinsChanger Admin Help &e]&c===========&e["));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e/skinadmin change <player> <skinname> &9-&a Changes your skin. &7&o//requires relog"));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e/skinadmin default <player> &9-&a Reverts your default skin &7&o//requires relog"));
		return false;
	}

	public boolean onAdminChangeCommand(final CommandSender sender, Command cmd, String label, final String[] args) {
		if (!sender.hasPermission("skinschanger.admin")) {
			sender.sendMessage("You don't have permission to do this");
			return true;
		}
		if (args.length == 3) {
			this.executor.execute(new Runnable() {
				public void run() {
					String name = args[1];
					try {
						SkinProfile profile = SkinGetUtils.getSkinProfile(args[2]);
						Skins.getInstance().getSkinStorage().addSkinData(name, profile);
						Skins.getInstance().getSkinStorage().saveData();
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bYou've changed " + ChatColor.YELLOW + args[1] + ChatColor.AQUA + "'s skin!"));
					} catch (SkinGetUtils.SkinFetchFailedException e) {
						sender.sendMessage(ChatColor.RED + "Skin fetch failed: " + e.getMessage());
					}
				}
			});
		}

		return false;
	}

	public boolean onAdminDefaultCommand(final CommandSender sender, Command cmd, String label, final String[] args) {
		if (!sender.hasPermission("skinschanger.admin")) {
			sender.sendMessage("你没有权限。");
			return true;
		}
		if (args.length == 2) {
			this.executor.execute(new Runnable() {
				public void run() {
					String name = args[1];
					try {
						SkinProfile profile = SkinGetUtils.getSkinProfile(name);
						Skins.getInstance().getSkinStorage().addSkinData(name, profile);
						Skins.getInstance().getSkinStorage().saveData();
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bYou've reverted " + ChatColor.YELLOW + args[1] + ChatColor.AQUA + "'s skin!"));
					} catch (SkinGetUtils.SkinFetchFailedException e) {
						sender.sendMessage(ChatColor.RED + "Skin fetch failed: " + e.getMessage());
					}
				}
			});
		}

		return false;
	}
}