package me.peridot.peripanel.commands;

import api.peridot.periapi.configuration.langapi.LangAPI;
import api.peridot.periapi.utils.replacements.Replacement;
import me.peridot.peripanel.PeriPanel;
import me.peridot.peripanel.data.configuration.ConfigurationManager;
import net.dzikoysk.funnyguilds.basic.user.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

public class PanelCommand implements CommandExecutor {

    private final PeriPanel plugin;

    public PanelCommand(PeriPanel plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ConfigurationManager dataManager = plugin.getConfigurations();
        LangAPI lang = dataManager.getLang();
        if (!(sender instanceof Player)) {
            lang.sendSimpleMessage(sender, "errors.noplayer");
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("peripanel.cmd.panel")) {
            lang.sendMessage(player, "errors.noperm", new Replacement("{PERMISSION}", "peripanel.cmd.panel"));
            return true;
        }
        User user = User.get(player);
        if(!user.hasGuild()) {
            lang.sendMessage(player, "errors.noguild");
            return true;
        }
        plugin.getInventoryManager().getPanelInventory().open(player);
        return true;
    }

    public void registerCommand() {
        PluginCommand command = plugin.getCommand("panel");
        command.setExecutor(this);
    }
}
