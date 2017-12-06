package eu.amazir.Systemy.Gildie;

import eu.amazir.CraftSlayer;
import eu.amazir.Systemy.DropSystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class G_Main
{
    CraftSlayer plugin;

    public G_Main(CraftSlayer instance) {plugin = instance;}

    public void Init()
    {
        plugin.getCommand("g").setExecutor(new G_Commands());
    }
}
