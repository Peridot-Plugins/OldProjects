package me.mckoxu.mckmagazyn.skript.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.sun.istack.internal.Nullable;
import me.mckoxu.mckmagazyn.MCKMagazyn;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class OpenMag extends Effect{
	
    private Expression<Player> player;
    private Expression<Number> number;
    
    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
    	number = (Expression<Number>) expr[0];
        player = (Expression<Player>) expr[1];
        return true;
    }
    @Override
    public String toString(@Nullable Event e, boolean b) {
        return null;
    }
    @Override
    protected void execute(Event e) {
        Player p = (Player) player.getSingle(e);
        int i = number.getSingle(e).intValue();
		MCKMagazyn.OpenMag(p, i);
    }
}
