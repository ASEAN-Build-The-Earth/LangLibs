package li.cinnazeyy.langlibs.core;

import li.cinnazeyy.langlibs.LangLibs;
import li.cinnazeyy.langlibs.core.language.LangLibAPI;
import li.cinnazeyy.langlibs.util.HeadUtils;
import me.arcaniax.hdb.api.DatabaseLoadEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public class EventListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Bukkit.getScheduler().runTaskAsynchronously(LangLibs.getPlugin(), () -> {
            String uuid = player.getUniqueId().toString();
            try (ResultSet rsUser = DatabaseConnection.createStatement("SELECT uuid, lang FROM langUsers WHERE uuid = ?").setValue(uuid).executeQuery()) {
                if (!rsUser.next()) LangLibAPI.setPlayerLang(player,"en_GB");
                else LangLibAPI.setPlayerLang(player,rsUser.getString(2));
                DatabaseConnection.closeResultSet(rsUser);
            } catch (SQLException e) {
                Bukkit.getLogger().log(Level.SEVERE, "A SQL error occurred!", e);
            }
        });
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDisconnect(PlayerQuitEvent event) {
        LangLibAPI.removePlayerLang(event.getPlayer());
    }

    @EventHandler
    public void onDatabaseLoad(DatabaseLoadEvent event) {
        HeadUtils.loadHeadsAsync();
    }
}
