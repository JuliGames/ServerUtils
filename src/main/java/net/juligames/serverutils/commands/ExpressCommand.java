package net.juligames.serverutils.commands;

import net.juligames.serverutils.main.ServerUtils;
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
    public static List<Player> open = new ArrayList<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = ((Player) sender);
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("support")) {
                    if (player.hasPermission(ServerUtils.support_request)) {
                        if (Bukkit.getOnlinePlayers().stream().anyMatch(player1 -> player1.hasPermission(ServerUtils.support_accept))) {
                            ServerUtils.sendMessage("<green>You successfully requested the Express Support!</green>", player);

                            if (!open.contains(player)) {
                                open.add(player);
                            }
                            ServerUtils.sendBroadcast("<green>" + player.displayName() + " is asking for Express-Support</green> <dark_green><click:run_command:'/express claimsupport " + player.getName() + "'>[Accept]</click></dark_green>", ServerUtils.support_accept);
                        } else {
                            ServerUtils.sendMessage("<red>There are currently no Team Members online!</red> <green>You can try to ask for help in the Discord!</green>", player);
                        }
                    } else {
                        ServerUtils.sendMessage("<red>You don´t have the permissions to execute this Command!</red>", sender);
                    }
                    return true;
                }
                if (args[0].equalsIgnoreCase("claimsupport")) {
                    if (player.hasPermission(ServerUtils.support_accept)) {
                        if (args.length == 2) {
                            if (Bukkit.getPlayer(args[1]) != null) {
                                Player target = Bukkit.getPlayer(args[1]);
                                if (open.contains(target)) {
                                    open.remove(target);
                                    ServerUtils.sendMessage("<green>" + player.displayName() + " will message you as soon as possible!</green>", target);
                                    ServerUtils.sendMessage("<green>You accepted the case of " + target.displayName() + "</green>", player);
                                } else {
                                    ServerUtils.sendMessage("<red>" + target.displayName() + " has no open ticket!</red>", player);
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
