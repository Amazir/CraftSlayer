package eu.amazir.Listeners;

import eu.amazir.CraftSlayer;
import eu.amazir.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.io.IOException;

public class OnConnection implements Listener
{
    CraftSlayer plugin;

    public OnConnection(CraftSlayer instance) {
        plugin = instance;
    }


    @EventHandler
    void onPlayerJoin(PlayerJoinEvent e)
    {
        String tag = plugin.getConfig().getString("general.tag");
        Player p = e.getPlayer();
        String msg = tag + " " + plugin.getConfig().getString("messages.join-msg");
        msg = msg.replace("%player%", p.getName());
        e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', msg));

        String playerName = p.getName();
        File userData = new File(Bukkit.getServer().getPluginManager().getPlugin("CraftSlayer").getDataFolder(), File.separator + "PlayerDatabase");
        File f = new File(userData, File.separator + playerName + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);

        e.getPlayer().sendMessage("working");

        if (!f.exists()) {
            try {

                playerData.createSection("drop");
                playerData.set("drop.stone", 0);

                playerData.createSection("stats");
                playerData.set("stats.totalLogins", 1);
                playerData.set("stats.totalKills", 0);
                playerData.set("stats.totalDeads", 0);
                playerData.set("stats.points", 500);

                playerData.save(f);
            } catch (IOException exception) {

                exception.printStackTrace();
            }
        }

        Utils.sendTitle(p, "&6Witamy na &2Craft&4Slayer&6!");
    }

    @EventHandler
    void onPlayerDisconnect(PlayerQuitEvent e)
    {
        String tag = plugin.getConfig().getString("general.tag");
        Player p = e.getPlayer();
        String msg = tag + " " + plugin.getConfig().getString("messages.quit-msg");
        msg = msg.replace("%player%", p.getName());
        e.setQuitMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }
}
