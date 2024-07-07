package org.sakura.hola_newcomer;

import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.sakura.hola_newcomer.Command.TestCommand;

public final class Hola_newcomer extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getCommand("hello").setExecutor(new TestCommand(this));
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE+"HolaNewcomer Plugin is RUNNING");
        //System.out.println();
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE+"HolaNewcomer Plugin is OUT"); //System.out.println();
    }
}