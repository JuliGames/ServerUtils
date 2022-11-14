package net.juligames.serverutils.storage;

import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface Storage {

    public void onEnable();
    public void onDisable();

    public List<Player> getOpenTickets();
    public void addOpenTicket(Player player);
    public void removeOpenTicket(Player player);

    public Map<Long, UUID> getTicketHistory();
    public void addToTicketHistory(Long time, UUID player);

    public List<UUID> getNetherVotes();
    public int getNetherVoteCount();
    public void addNetherVote(UUID player);
    public void resetNetherVotes();

    public List<UUID> getEndVotes();
    public int getEndVoteCount();
    public void addEndVote(UUID player);
    public void resetEndVotes();

}
