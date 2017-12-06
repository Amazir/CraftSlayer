package eu.amazir.Systemy.Gildie;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class G_Commands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(cmd.getName().equalsIgnoreCase("g"))
        {
            Player p = (Player) sender;

          //  p.sendMessage(args[0]);

            System.out.println(args.length);
            System.out.println(args[0]);
            if(args[0].equalsIgnoreCase( "zaloz"))
            {
                G_Create.CreateGuild(args[1],args[2],p);
            }
           /* if(args.length == 0)
            {
                // po prostu /g
                p.sendMessage("gildie here");
            }*/
            //if(args[0] == "zaloz")
           // {
            //    p.sendMessage("dziala");
               //
            //}

        }
        return false;
    }
}
