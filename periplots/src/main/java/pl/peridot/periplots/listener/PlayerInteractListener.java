package pl.peridot.periplots.listener;

import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.domains.PlayerDomain;
import com.sk89q.worldguard.internal.platform.WorldGuardPlatform;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import pl.peridot.periplots.PeriPlotsMain;
import pl.peridot.periplots.util.Util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public static void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        Action action = event.getAction();

        if(block != null) {
            Location location = block.getLocation();
            if (action == Action.RIGHT_CLICK_BLOCK) {
                if (block.getType().toString().contains("SIGN")) {
                    Sign sign = (Sign) block.getState();
                    if (ChatColor.stripColor(sign.getLine(0)).equalsIgnoreCase("[Dzialka]")) {
                        WorldGuardPlatform wgp = WorldGuard.getInstance().getPlatform();
                        RegionManager regionManager = wgp.getRegionContainer().get(new BukkitWorld(location.getWorld()));
                        if (ChatColor.stripColor(sign.getLine(3)).equalsIgnoreCase("Wolna")) {
                            ProtectedRegion region = null;
                            int cost = 0;

                            try {
                                String costString = StringUtils.replace(ChatColor.stripColor(sign.getLine(2)), " zlota", "");
                                cost = Integer.parseInt(costString);
                            } catch (Exception ignored) { }

                            region = regionManager.getRegion(sign.getLine(1));

                            if (region != null) {
                                int goldAmount = Util.goldAmountInInventory(player);

                                if (goldAmount >= cost) {
                                    int goldBlockAmount = Util.itemsAmountInInventory(player, Material.GOLD_BLOCK);

                                    int additionalGoldIngotsAmount = 0;
                                    int goldBlockToSell = 0;
                                    int returnedGoldIngots = 0;

                                    if (goldBlockAmount * 9 < cost) {
                                        additionalGoldIngotsAmount = cost - goldBlockAmount * 9;
                                        goldBlockToSell = goldBlockAmount;
                                    } else {
                                        goldBlockToSell = (int) Math.ceil(cost / 9.0);
                                        returnedGoldIngots = goldBlockToSell * 9 - cost;
                                    }

                                    Util.removeItemFromInventory(player, Material.GOLD_BLOCK, goldBlockToSell);
                                    Util.removeItemFromInventory(player, Material.GOLD_INGOT, additionalGoldIngotsAmount);
                                    player.getInventory().addItem(new ItemStack(Material.GOLD_INGOT, returnedGoldIngots));

                                    LocalDateTime localDateTime = LocalDateTime.now();

                                    sign.setLine(2, player.getName());
                                    sign.setLine(3, PeriPlotsMain.dateTimeFormatter.format(localDateTime));
                                    sign.update();

                                    player.sendMessage("Kupiles dzialke!");
                                } else {
                                    player.sendMessage("Posiadasz za malo zlota!");
                                }
                            } else {
                                player.sendMessage("Podany region jest niepoprawny!");
                            }
                        }
                    }
                }
            }
        }
    }

}
