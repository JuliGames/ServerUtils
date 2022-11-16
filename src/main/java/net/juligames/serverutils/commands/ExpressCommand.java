package net.juligames.serverutils.commands;

import net.juligames.serverutils.main.ServerUtils;
import net.juligames.serverutils.storage.Storage;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ExpressCommand implements CommandExecutor {
    Storage storage = ServerUtils.getStorage();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = ((Player) sender);
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("support")) {
                    if (player.hasPermission(ServerUtils.SUPPORT_REQUEST)) {
                        if (Bukkit.getOnlinePlayers().stream().anyMatch(player1 -> player1.hasPermission(ServerUtils.SUPPORT_ACCEPT))) {
                            ServerUtils.sendMessage("<green>You successfully requested the Express Support!</green>", player);

                            storage.addToTicketHistory(System.currentTimeMillis(), player.getUniqueId());
                            if (!storage.getOpenTickets().contains(player)) {
                                storage.addOpenTicket(player);
                            }
                            ServerUtils.sendBroadcast("<green>" + MiniMessage.miniMessage().serialize(player.displayName()) + " is asking for Express-Support</green> <dark_green><click:run_command:'/express claimsupport " + player.getName() + "'>[Accept]</click></dark_green>", ServerUtils.SUPPORT_ACCEPT);
                        } else {
                            ServerUtils.sendMessage("<red>There are currently no Team Members online!</red> <green>You can try to ask for help in the Discord!</green>", player);
                        }
                    } else {
                        ServerUtils.sendMessage("<red>You don´t have the permissions to execute this Command!</red>", sender);
                    }
                    return true;
                }
                if (args[0].equalsIgnoreCase("claimsupport")) {
                    if (player.hasPermission(ServerUtils.SUPPORT_ACCEPT)) {
                        if (args.length == 2) {
                            if (Bukkit.getPlayer(args[1]) != null) {
                                Player target = Bukkit.getPlayer(args[1]);
                                if (storage.getOpenTickets().contains(target)) {
                                    storage.removeOpenTicket(target);
                                    ServerUtils.sendMessage("<green>" + MiniMessage.miniMessage().serialize(player.displayName()) + " will message you as soon as possible!</green>", target);
                                    ServerUtils.sendMessage("<green>You accepted the case of " + MiniMessage.miniMessage().serialize(target.displayName()) + "</green>", player);
                                    for (Player player2 : Bukkit.getOnlinePlayers()) {
                                        if (player2.hasPermission(ServerUtils.SUPPORT_ACCEPT) && player2 != player) {
                                            ServerUtils.sendMessage("<green>" + MiniMessage.miniMessage().serialize(player.displayName()) + " accepted the case of " + MiniMessage.miniMessage().serialize(target.displayName()) + "</green>", player2);
                                        }
                                    }
                                } else {
                                    ServerUtils.sendMessage("<red>" + MiniMessage.miniMessage().serialize(target.displayName()) + " has no open ticket!</red>", player);
                                }
                            } else {
                                ServerUtils.sendMessage("<red>" + args[1] + " is not a online player!</red>", player);
                            }
                        } else {
                            ServerUtils.sendMessage("<red>Use /express claimsupport <Player>!</red>", player);
                        }
                    } else {
                        ServerUtils.sendMessage("<red>You don´t have the permissions to execute this Command!</red>", sender);
                    }
                    return true;
                }
                ServerUtils.sendMessage("<red>Use /express <support/claimsupport>!</red>", player);
            } else {
                ServerUtils.sendMessage("<red>Use /express <support/claimsupport>!</red>", player);
            }
        } else {
            ServerUtils.sendMessage("<red>This command can only be executed as a player!</red>", sender);
        }
        return true;
    }
}
