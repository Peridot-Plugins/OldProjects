package me.mckoxu.mckmagazyn.skript.conditions;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.sun.istack.internal.Nullable;
import me.mckoxu.mckmagazyn.MCKMagazyn;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.Event;

public class MagExist extends Condition{
	
	private Expression<Number> number;
	
    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kl, SkriptParser.ParseResult pr) {
        number = (Expression<Number>) expr[0];
        return true;
    }
    @Override
    public String toString(@Nullable Event e, boolean b) {
        return null;
    }
    @Override
    public boolean check(Event e){
    	int i = number.getSingle(e).intValue();
		ConfigurationSection csk = MCKMagazyn.getInst().getConfig().getConfigurationSection("data.magazyny."+i);
		if(csk != null) {
			return true;
		} else{
			return false;
		}
    }
}
