package me.mckoxu.mckvote.manager;

import org.bukkit.OfflinePlayer;

import java.util.List;

public class VoteManager {

    public static List<OfflinePlayer> votePlayers;
    public static String voteName;
    public static List<String> commandsList;
    public static int voteYes;
    public static int voteNo;

    public static void startVote(String vote, List<String> commands){
        endVote();
        voteName = vote;
        commandsList = commands;
    }

    public static void endVote(){
        votePlayers.clear();
        voteName = null;
        voteYes = 0;
        voteNo = 0;
    }

}
