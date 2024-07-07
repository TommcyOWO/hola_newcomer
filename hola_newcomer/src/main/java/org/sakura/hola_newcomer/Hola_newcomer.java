package org.sakura.hola_newcomer;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.sakura.hola_newcomer.Command.TestCommand;

public final class Hola_newcomer extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getCommand("hello").setExecutor(new TestCommand(this));
        getServer().getPluginManager().registerEvents(this, this);
        System.out.println("HolaNewcomer Plugin is RUNNING");
    }

    @Override
    public void onDisable() {
        System.out.println("HolaNewcomer Plugin is OUT");
    }
}