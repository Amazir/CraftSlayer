package eu.amazir.Systemy.Gildie;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class G_Create {

    public static void CreateGuild(String name, String tag, Player p)
    {
        File guilds = new File(Bukkit.getServer().getPluginManager().getPlugin("CraftSlayer").getDataFolder(), File.separator + "Gildie");
        File f = new File(guilds, File.separator + name + ".yml");
        FileConfiguration guildData = YamlConfiguration.loadConfiguration(f);

        guildData.createSection("general");
        guildData.set("general.name", name);
        guildData.set("general.tag", tag);
        guildData.createSection("members");
        guildData.set("members.owner",p.getDisplayName());

        Location l = p.getLocation();
        Block b = l.getBlock().getRelative(BlockFace.DOWN);
        Block b2 = l.getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN);
        b.setType(Material.DRAGON_EGG);
        b2.setType(Material.BEDROCK);

        Location loc1 = new Location(p.getLocation().getWorld(), p.getLocation().getX() + 10, p.getLocation().getY() + 300, p.getLocation().getZ() + 10);
        Location loc2 = new Location(p.getLocation().getWorld(), p.getLocation().getX() - 10, p.getLocation().getY(), p.getLocation().getZ() - 10);
        Cuboid cuboid = new Cuboid(loc1, loc2);
        guildData.set("general.posX", loc1);
        guildData.set("general.posY", loc2);
        guildData.set("general.cubSize", "10x10");

        try {
            guildData.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }

        p.sendMessage("working");
    }

}
