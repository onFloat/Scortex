package me.onfloat.Scortex;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;


public class Basic extends JavaPlugin implements Listener {
    String chatformat = ChatColor.GRAY + "(" + ChatColor.GREEN + "ScortexMC" + ChatColor.GRAY + ") ";

    public static Economy econ = null;

    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        System.out.println("Scortex has been Enabled, Developed by _onFloat_");
        getConfig();
        saveConfig();
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
    }

    @Override
    public void onDisable() {
        System.out.println("Scortex has been Disabled, Developed by _onFloat_");
    }

    @SuppressWarnings("unused")
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null)
            return false;

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null)
            return false;
        econ = rsp.getProvider();
        return econ != null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if(!sender.hasPermission("smc.info")){
            player.sendMessage(chatformat + ChatColor.RED + "You do not have permission to access this command contact one of the ScortexMC Staff for more information");
            return true;
        }else {
            if(cmd.getName().equalsIgnoreCase("info")) {
                player.sendMessage(chatformat + ChatColor.BLUE + getConfig().get("website"));
            }
            // if(cmd.getName().equalsIgnoreCase("test"));
        }
        return true;
    }

    @SuppressWarnings("unused")
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        String player = event.getEntity().getName();
        String Killer = event.getEntity().getKiller().getName();

        Player p = null;
        if(event.getEntity() instanceof Player)
            p = event.getEntity();

        event.getEntity().getPlayer().getKiller().sendMessage(chatformat + ChatColor.GREEN + "You have successfully killed " + ChatColor.BLUE + player + ChatColor.GREEN + " Here is a $500 reward");
        event.getEntity().getPlayer().sendMessage(chatformat + ChatColor.RED + "You have been killed by " + ChatColor.DARK_RED + Killer + ChatColor.RED + " $500 has been deducted out of your balance");
        econ.withdrawPlayer(p, 500);
        econ.depositPlayer(event.getEntity().getKiller(), 500);
    }
}
    



