package net.juligames.serverutils.main;

import net.juligames.serverutils.commands.ExpressCommand;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class ServerUtils extends JavaPlugin {

    @Override
    public void onEnable() {
        registerCommand(new ExpressCommand(), "express");

        Bukkit.getLogger().info("Enabled ServerUtils V" + getDescription().getVersion());
    }

    @Override
    public void onDisable() {

    }

    public void registerCommand(CommandExecutor command, String name) {
        getCommand(name).setExecutor(command);
    }

    public static void sendMessage(String kennyTV, CommandSender sender) {
        sender.sendMessage(MiniMessage.miniMessage().deserialize(kennyTV));
    }

}
