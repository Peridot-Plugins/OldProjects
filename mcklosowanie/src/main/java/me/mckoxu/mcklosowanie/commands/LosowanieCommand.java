package me.mckoxu.mcklosowanie.commands;

import me.mckoxu.mcklosowanie.data.Config;
import me.mckoxu.mcklosowanie.util.ChatUtil;
import me.mckoxu.mcklosowanie.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LosowanieCommand implements CommandExecutor {

    public static ItemStack itemToWin;
    private static final Random random = new Random();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("losowanie")){
            if(sender.hasPermission("mcklosowanie.cmd")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if(args.length >= 1){
                        int playersAmount = 0;
                        try{
                            playersAmount = Integer.valueOf(args[0]);
                        } catch (Exception ex){
                            player.sendMessage(ChatUtil.color(Config.wrongNumber));
                            return true;
                        }
                        if(playersAmount < 1){
                            player.sendMessage(ChatUtil.color(Config.wrongNumber));
                            return true;
                        }
                        if(playersAmount > Config.maxPlayers){
                            player.sendMessage(ChatUtil.color(Config.tooMuchPlayers));
                            return true;
                        }
                        if(player.getItemInHand() != null && player.getItemInHand().getType() != Material.AIR) {
                            if(Bukkit.getOnlinePlayers().size() >= Config.minPlayersOnline) {
                                itemToWin = player.getItemInHand();
                                Bukkit.broadcastMessage(ChatUtil.color(Config.start
                                        .replace("{ADMIN}", player.getName())
                                        .replace("{ITEM}", Util.itemToString(itemToWin))
                                        .replace("{PLAYERSAMOUNT}", String.valueOf(playersAmount))));
                                List<Player> players = new ArrayList<Player>(Bukkit.getOnlinePlayers());
                                List<Player> winners = new ArrayList<>();
                           /* for(Player onlinePlayer : players){
                                if(onlinePlayer.hasPermission("mcklosowanie.ignore")){
                                    players.remove(onlinePlayer);
                                }
                            }*/
                                StringBuilder winnersString = new StringBuilder();
                                for (int i = 0; i < playersAmount; i++) {
                                    if (!players.isEmpty()) {
                                        Player winnerPlayer = players.get(random.nextInt(players.size()));
                                        winners.add(winnerPlayer);
                                        winnersString.append(", " + winnerPlayer.getName());
                                        players.remove(winnerPlayer);
                                    }
                                }
                                Bukkit.broadcastMessage(ChatUtil.color(Config.win
                                        .replace("{PLAYERS}", winnersString
                                                .toString()
                                                .replaceFirst(", ", ""))));
                                for (Player winner : winners) {
                                    if (winner.getInventory().firstEmpty() == -1) {
                                        player.getWorld().dropItem(player.getLocation(), itemToWin);
                                    } else {
                                        winner.getInventory().addItem(itemToWin);
                                    }
                                }
                            } else {
                                player.sendMessage(ChatUtil.color(Config.notEnoughPlayers));
                            }
                        } else{
                            player.sendMessage(ChatUtil.color(Config.airInHand));
                        }
                    } else{
                        player.sendMessage(ChatUtil.color(Config.correctUsage));
                    }
                } else {
                    sender.sendMessage("[MCKLosowanie] Tylko gracz na serwerze moze wykonac ta komende!");
                }
            } else {
                sender.sendMessage(ChatUtil.color(Config.noPerm));
            }
        }
        return true;
    }
}
