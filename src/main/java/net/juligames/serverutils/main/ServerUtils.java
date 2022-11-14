package net.juligames.serverutils.main;

import net.juligames.serverutils.commands.ExpressCommand;
import net.juligames.serverutils.commands.ResetNetherVoteCommand;
import net.juligames.serverutils.storage.Storage;
import net.juligames.serverutils.storage.StoragelessStorage;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

public class ServerUtils extends JavaPlugin {
    public static final Permission support_accept = new Permission("juligames.serverutils.express.support.accept");
    public static final Permission support_request = new Permission("juligames.serverutils.express.support.request");
    public static final Permission nether_vote = new Permission("juligames.serverutils.vote.nether");
    public static final Permission end_vote = new Permission("juligames.serverutils.vote.end");
    public static final Permission nether_vote_force = new Permission("juligames.serverutils.vote.nether.force");
    public static final Permission end_vote_force = new Permission("juligames.serverutils.vote.end.force");

    public static final String prefix = "[Juligames] ";

    public static Storage storage;

    @Override
    public void onEnable() {
        storage = new StoragelessStorage();
        storage.onEnable();

        registerCommand(new ExpressCommand(), "express");
        registerCommand(new ResetNetherVoteCommand(), "resetnethervote");

        Bukkit.getLogger().info("Enabled ServerUtils V" + getDescription().getVersion());
    }

    @Override
    public void onDisable() {
        getStorage().onDisable();
    }

    public static Storage getStorage() {
        return storage;
    }

    public void registerCommand(CommandExecutor command, String name) {
        getCommand(name).setExecutor(command);
    }

    public static void sendMessage(String kennyTV, CommandSender sender) {
        sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix + kennyTV));
    }

    public static void sendBroadcast(String kennyTV) {
        Bukkit.getServer().broadcast(MiniMessage.miniMessage().deserialize(prefix + kennyTV), Server.BROADCAST_CHANNEL_USERS);
    }

    public static void sendBroadcast(String kennyTV, Permission permission) {
        Bukkit.broadcast(MiniMessage.miniMessage().deserialize(prefix + kennyTV), permission.getName());
    }

}
