package eu.amazir;

import eu.amazir.Listeners.OnConnection;
import eu.amazir.Systemy.DropSystem;
import eu.amazir.Systemy.Gildie.G_Main;
import eu.amazir.Systemy.PvPSystem;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class CraftSlayer extends JavaPlugin
{
    @Override
    public void onEnable() {
        FileConfiguration config = this.getConfig();

        config.addDefault("messages.join-msg", "Witaj %player%!");
        config.addDefault("messages.quit-msg", "Zegnaj %player%!");
        config.addDefault("general.tag", "[TEST]");
        config.addDefault("drop.enabled", true);
        config.addDefault("drop.experience", 1000);
        config.addDefault("drop.diamond-chance", 3);
        config.addDefault("drop.emerald-chance", 2);
        config.addDefault("drop.gold-chance", 3);
        config.addDefault("drop.iron-chance", 5);

        config.options().copyDefaults(true);
        saveConfig();

        Bukkit.getServer().getPluginManager().registerEvents(new OnConnection(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new DropSystem(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PvPSystem(this), this);

        getCommand("drop").setExecutor(new DropSystem(this));

        // Gildie inicjacja
        G_Main m = new G_Main(this);
        m.Init();
    }

    @Override
    public void onDisable() {

    }

}
