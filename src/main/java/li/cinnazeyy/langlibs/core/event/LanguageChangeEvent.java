package li.cinnazeyy.langlibs.core.event;

import li.cinnazeyy.langlibs.core.Language;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class LanguageChangeEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private final Language language;
    public LanguageChangeEvent(Player player, Language language) {
        this.player = player;
        this.language = language;
    }

    public Player getPlayer() { return player; }

    public Language getLanguage() { return language; }

    public static HandlerList getHandlerList() { return HANDLERS; }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
