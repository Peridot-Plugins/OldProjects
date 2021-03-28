package me.mckoxu.mckmagazyn.skript.conditions;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.sun.istack.internal.Nullable;
import me.mckoxu.mckmagazyn.MCKMagazyn;
import me.mckoxu.mckmagazyn.data.FileManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.io.File;

public class HasAccess extends Condition{
	
	private Expression<Player> player;
	private Expression<Number> number;
	
    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kl, SkriptParser.ParseResult pr) {
        player = (Expression<Player>) expr[0];
        number = (Expression<Number>) expr[1];
        return true;
    }
    @Override
    public String toString(@Nullable Event e, boolean b) {
        return null;
    }
    @Override
    public boolean check(Event e){
    	Player p = (Player) player.getSingle(e).getPlayer();
    	int i = number.getSingle(e).intValue();
    	File f;
		f = FileManager.getPFile(p);
		YamlConfiguration fYml = YamlConfiguration.loadConfiguration(f);
		ConfigurationSection csk = MCKMagazyn.getInst().getConfig().getConfigurationSection("data.magazyny."+i);
		if(csk != null) {
			if(MCKMagazyn.getInst().getConfig().getBoolean("data.magazyny."+i+".access") && !fYml.getBoolean("data.magazyny."+i+".access")) {
				return false;
			} else {
				return true;
			}
		} else{
			return false;
		}
    }
}
