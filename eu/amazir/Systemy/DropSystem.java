package eu.amazir.Systemy;

import eu.amazir.CraftSlayer;
import eu.amazir.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Random;

public class DropSystem implements Listener, CommandExecutor
{
    CraftSlayer plugin;

    boolean enabled;
    int experience;

    int diamond_chance, emerald_chance, gold_chance, iron_chance;

    public DropSystem(CraftSlayer instance)
    {
        plugin = instance;

        enabled  = plugin.getConfig().getBoolean("drop.enabled");
        experience = plugin.getConfig().getInt("drop.experience");

        diamond_chance = plugin.getConfig().getInt("drop.diamond-chance")*10;
        emerald_chance = plugin.getConfig().getInt("drop.emerald-chance")*10;
        iron_chance = plugin.getConfig().getInt("drop.iron-chance")*10;
        gold_chance = plugin.getConfig().getInt("drop.gold-chance")*10;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e)
    {
        if(enabled) {
            Player p = e.getPlayer();
            if (e.getBlock().getType().equals(Material.STONE))
            {
                p.giveExp(experience);

                Random r = new Random();
                int r1 = r.nextInt(1000);

                if(r1 < diamond_chance)
                {
                    Utils.sendActionbar(p, "&8Z kamienia otrzymales &bdiament&8!");
                    p.getInventory().addItem(new ItemStack(Material.DIAMOND));
                }
                else if(r1 < diamond_chance+emerald_chance) {
                    Utils.sendActionbar(p, "&8Z kamienia otrzymales &aszmaragd&8!");
                    p.getInventory().addItem(new ItemStack(Material.EMERALD));
                }
                else if(r1 < diamond_chance+emerald_chance+gold_chance) {
                    Utils.sendActionbar(p, "&8Z kamienia otrzymales &6zloto&8!");
                    p.getInventory().addItem(new ItemStack(Material.GOLD_INGOT));
                }
                else if(r1 < diamond_chance+emerald_chance+gold_chance+iron_chance)
                {
                    Utils.sendActionbar(p, "&8Z kamienia otrzymales &7zelazo&8!");
                    p.getInventory().addItem(new ItemStack(Material.IRON_INGOT));
                }
            }
        }
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if(cmd.getName().equals("drop"))
        {
            Player p = (Player) sender;

            Inventory inv = Bukkit.createInventory(null, 9, "Drop ze stone'a");
            ItemStack diamond = new ItemStack(Material.DIAMOND);
            ItemMeta diamondMeta = diamond.getItemMeta();
            ArrayList<String> diamondLore = new ArrayList<String>();
            diamondLore.add("Szansa: "+diamond_chance/10+"%");
            diamondMeta.setLore(diamondLore);
            diamond.setItemMeta(diamondMeta);
            inv.setItem(0,diamond);
            p.openInventory(inv);
        }
        return false;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Inventory inventory = e.getInventory();

        if(inventory.getName() == "Drop ze stone'a")
            e.setCancelled(true);
    }

}
