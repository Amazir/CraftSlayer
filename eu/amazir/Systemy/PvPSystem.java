package eu.amazir.Systemy;

import eu.amazir.CraftSlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.io.File;
import java.io.IOException;

public class PvPSystem implements Listener {

    CraftSlayer plugin;

    public PvPSystem(CraftSlayer instance) {
        plugin = instance;
    }

    @EventHandler
    void onPlayerDeath(PlayerDeathEvent e)
    {
        // 100 i przez 2

        Player victim = e.getEntity();
        Player killer = e.getEntity().getKiller();

        File f1 = new File(Bukkit.getServer().getPluginManager().getPlugin("CraftSlayer").getDataFolder() + "/PlayerDatabase/" +victim.getName()+".yml");
        File f2 = new File(Bukkit.getServer().getPluginManager().getPlugin("CraftSlayer").getDataFolder() + "/PlayerDatabase/" +killer.getName()+".yml");

        FileConfiguration cfg_victim = YamlConfiguration.loadConfiguration(f1);
        FileConfiguration cfg_killer = YamlConfiguration.loadConfiguration(f2);

        int victim_kills = cfg_victim.getInt("stats.totalKills");
        int victim_deaths = cfg_victim.getInt("stats.totalDeads");
        victim_deaths++;
        cfg_victim.set("stats.totalDeads", victim_deaths);

        int killer_kills = cfg_killer.getInt("stats.totalKills");
        int killer_deaths = cfg_killer.getInt("stats.totalDeaths");
        killer_kills++;
        cfg_killer.set("stats.totalKills", killer_kills);

        e.getEntity().getWorld().strikeLightning(e.getEntity().getLocation());

        int KD_k = Math.round(killer_kills - killer_deaths);
        int KD_v = Math.round(victim_kills / victim_deaths);
        killer.getPlayer().sendMessage("kd: "+KD_k);

        int points_k = cfg_killer.getInt("stats.points");
        int points_v = cfg_victim.getInt("stats.points");
        int points = (points_k / 100) * 2;
        points_k += points * KD_k;
        points_v -= points * KD_k;
        cfg_killer.set("stats.points", points_k);
        cfg_victim.set("stats.points", points_v);

        String tag = plugin.getConfig().getString("general.tag");
        if(killer.getItemInHand().getItemMeta().getDisplayName() != null)
            e.setDeathMessage(ChatColor.translateAlternateColorCodes('&', tag) + " " + ChatColor.YELLOW + victim.getName()+" (+"+points_k+") zostal zabity przez "+killer.getName()+" (-"+points_v+") przedmiotem o nazwie "+ killer.getItemInHand().getItemMeta().getDisplayName());
        else
            e.setDeathMessage(ChatColor.translateAlternateColorCodes('&', tag) + " " +  ChatColor.YELLOW + victim.getName()+" zostal zabity przez "+killer.getName());

        try {
            cfg_victim.save(f1);
            cfg_killer.save(f2);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
