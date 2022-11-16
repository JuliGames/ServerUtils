package net.juligames.serverutils.commands;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import net.juligames.serverutils.main.ServerUtils;
import net.juligames.serverutils.storage.Storage;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ResetNetherVoteCommand implements CommandExecutor {
    Storage storage = ServerUtils.getStorage();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = ((Player) sender);
            if (Bukkit.getServer().getPluginManager().isPluginEnabled("Multiverse-Core")) {
                if (args.length == 0) {
                    if (player.hasPermission(ServerUtils.NETHER_VOTE)) {
                        if (!storage.getNetherVotes().contains(player.getUniqueId())) {
                            storage.addNetherVote(player.getUniqueId());
                            ServerUtils.sendMessage("<green>You have successfully voted for a reset of the Nether!</green> <yellow>" + storage.getNetherVoteCount() + "/10</yellow>", player);
                            if (storage.getNetherVoteCount() >= 10) {
                                resetNether();
                            }
                        } else {
                            ServerUtils.sendMessage("<red>You have already voted for a reset of the Nether!</red> <yellow>" + storage.getNetherVoteCount() + "/10</yellow>", player);
                        }
                    } else {
                        ServerUtils.sendMessage("<red>You don´t have the permissions to execute this Command!</red>", player);
                    }
                } else {
                    if (player.hasPermission(ServerUtils.NETHER_VOTE_FORCE)) {
                        if (args[0].equalsIgnoreCase("force")) {
                            if (args.length == 2 && args[1].equalsIgnoreCase("confirm")) {
                                resetNether();
                                return true;
                            } else {
                                ServerUtils.sendMessage("<green>Are you sure you want to reset the Nether? </green><dark_green><click:run_command:'/resetnethervote force confirm'>[confirm]</click></dark_green>", player);
                            }
                        } else {
                            ServerUtils.sendMessage("<red>Use /resetnethervote [force]", player);
                        }
                    } else {
                        ServerUtils.sendMessage("<red>You don´t have the permissions to execute this Command!</red>", player);
                    }
                }
            } else {
                ServerUtils.sendMessage("<red>This command is not available!</red>", sender);
            }
        } else {
            ServerUtils.sendMessage("<red>This command can only be executed as a player!</red>", sender);
        }
        return true;
    }

    private void resetNether() {
        Bukkit.getServer().broadcast(MiniMessage.miniMessage().deserialize("\n<yellow>Resetting Nether</yellow>\n"), Server.BROADCAST_CHANNEL_USERS);
        storage.resetNetherVotes();

        MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
        MVWorldManager mv = core.getMVWorldManager();
        mv.deleteWorld("world_nether");
        mv.addWorld("world_nether", World.Environment.NETHER, null, WorldType.NORMAL, true, null);

        Bukkit.getServer().broadcast(MiniMessage.miniMessage().deserialize("\n<yellow>Resetted Nether</yellow>\n"), Server.BROADCAST_CHANNEL_USERS);
    }

}
