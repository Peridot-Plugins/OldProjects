package me.peridot.peripanel.inventories;

import api.peridot.periapi.inventories.InventoryContent;
import api.peridot.periapi.inventories.items.InventoryItem;
import api.peridot.periapi.inventories.providers.InventoryProvider;
import api.peridot.periapi.utils.replacements.Replacement;
import me.peridot.peripanel.PeriPanel;
import me.peridot.peripanel.data.configuration.PluginConfiguration;
import net.dzikoysk.funnyguilds.basic.guild.Guild;
import net.dzikoysk.funnyguilds.basic.rank.Rank;
import net.dzikoysk.funnyguilds.basic.user.User;
import org.bukkit.entity.Player;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.TimeZone;

public class PanelInventory implements InventoryProvider {

    private final PeriPanel plugin;

    public PanelInventory(PeriPanel plugin) {
        this.plugin = plugin;
    }

    @Override
    public void init(Player player, InventoryContent content) {
        PluginConfiguration config = plugin.getConfigurations().getPluginConfiguration();

        User user = User.get(player);
        Rank userRank = user.getRank();

        if (!user.hasGuild()) {
            return;
        }
        Guild guild = user.getGuild();
        Rank guildRank = guild.getRank();

        content.fill(InventoryItem.builder().item(config.getItemBuilder("inventories.panel.buttons.background").clone()).build());

        String userRole = config.getColoredString("messages.roles.member");
        if (user.isOwner()) {
            userRole = config.getColoredString("messages.roles.owner");
        } else if (user.isDeputy()) {
            userRole = config.getColoredString("messages.roles.deputy");
        }
        String finalUserRole = userRole;

        String ownerOnline = guild.getOwner().isOnline() ? config.getColoredString("messages.status.owner.online") : config.getColoredString("messages.status.owner.offline");

        int deputiesOnline = 0;
        for (User deputy : guild.getDeputies()) {
            if (deputy.isOnline()) deputiesOnline++;
        }
        int finalDeputiesOnline = deputiesOnline;

        String guildValidity = PluginConfiguration.dateFormat.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(guild.getValidity()), TimeZone.getDefault().toZoneId()));
        String guildBorn = PluginConfiguration.dateFormat.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(guild.getBorn()), TimeZone.getDefault().toZoneId()));

        config.panelInventoryButtons.forEach((slot, button) -> {
            content.setItem(slot, InventoryItem.builder()
                    .item(button.getItem().clone()
                            .replaceInName(new Replacement("{PLAYER-NAME}", player.getName()),
                                    new Replacement("{PLAYER-POSITION}", userRank.getPosition()),
                                    new Replacement("{PLAYER-POINTS}", userRank.getPoints()),
                                    new Replacement("{PLAYER-KILLS}", userRank.getKills()),
                                    new Replacement("{PLAYER-DEATHS}", userRank.getDeaths()),
                                    new Replacement("{PLAYER-KDR}", userRank.getKDR()),
                                    new Replacement("{PLAYER-KDR}", userRank.getKDR()),
                                    new Replacement("{GUILD-TAG}", guild.getTag()),
                                    new Replacement("{GUILD-NAME}", guild.getName()),
                                    new Replacement("{GUILD-OWNER}", guild.getOwner().getName()),
                                    new Replacement("{GUILD-OWNER-ONLINE}", ownerOnline),
                                    new Replacement("{GUILD-MEMBERS}", guild.getMembers().size()),
                                    new Replacement("{GUILD-MEMBERS-ONLINE}", guild.getOnlineMembers().size()),
                                    new Replacement("{GUILD-DEPUTIES}", guild.getDeputies().size()),
                                    new Replacement("{GUILD-DEPUTIES-ONLINE}", finalDeputiesOnline),
                                    new Replacement("{GUIILD-USER-ROLE}", finalUserRole),
                                    new Replacement("{GUILD-ALLIES}", guild.getAllies().size()),
                                    new Replacement("{GUILD-POSITION}", guildRank.getPosition()),
                                    new Replacement("{GUILD-POINTS}", guildRank.getPoints()),
                                    new Replacement("{GUILD-KILLS}", guildRank.getKills()),
                                    new Replacement("{GUILD-DEATHS}", guildRank.getDeaths()),
                                    new Replacement("{GUILD-KDR}", guildRank.getKDR()),
                                    new Replacement("{GUILD-LIVES}", guild.getLives()),
                                    new Replacement("{GUILD-SIZE}", guild.getRegion().getSize()),
                                    new Replacement("{GUILD-VALIDITY-DATE}", guildValidity),
                                    new Replacement("{GUILD-BORN-DATE}", guildBorn))
                            .replaceInLore(new Replacement("{PLAYER-NAME}", player.getName()),
                                    new Replacement("{PLAYER-POSITION}", userRank.getPosition()),
                                    new Replacement("{PLAYER-POINTS}", userRank.getPoints()),
                                    new Replacement("{PLAYER-KILLS}", userRank.getKills()),
                                    new Replacement("{PLAYER-DEATHS}", userRank.getDeaths()),
                                    new Replacement("{PLAYER-KDR}", userRank.getKDR()),
                                    new Replacement("{PLAYER-KDR}", userRank.getKDR()),
                                    new Replacement("{GUILD-TAG}", guild.getTag()),
                                    new Replacement("{GUILD-NAME}", guild.getName()),
                                    new Replacement("{GUILD-OWNER}", guild.getOwner().getName()),
                                    new Replacement("{GUILD-OWNER-ONLINE}", ownerOnline),
                                    new Replacement("{GUILD-MEMBERS}", guild.getMembers().size()),
                                    new Replacement("{GUILD-MEMBERS-ONLINE}", guild.getOnlineMembers().size()),
                                    new Replacement("{GUILD-DEPUTIES}", guild.getDeputies().size()),
                                    new Replacement("{GUILD-DEPUTIES-ONLINE}", finalDeputiesOnline),
                                    new Replacement("{GUILD-USER-ROLE}", finalUserRole),
                                    new Replacement("{GUILD-ALLIES}", guild.getAllies().size()),
                                    new Replacement("{GUILD-POSITION}", guildRank.getPosition()),
                                    new Replacement("{GUILD-POINTS}", guildRank.getPoints()),
                                    new Replacement("{GUILD-KILLS}", guildRank.getKills()),
                                    new Replacement("{GUILD-DEATHS}", guildRank.getDeaths()),
                                    new Replacement("{GUILD-KDR}", guildRank.getKDR()),
                                    new Replacement("{GUILD-LIVES}", guild.getLives()),
                                    new Replacement("{GUILD-SIZE}", guild.getRegion().getSize()),
                                    new Replacement("{GUILD-VALIDITY-DATE}", guildValidity),
                                    new Replacement("{GUILD-BORN-DATE}", guildBorn)))
                    .consumer(event -> {
                        button.run(plugin, event);
                    })
                    .build());
        });
    }

    @Override
    public void update(Player player, InventoryContent content) {

    }
}
