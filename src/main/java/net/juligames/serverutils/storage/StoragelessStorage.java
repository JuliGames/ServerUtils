package net.juligames.serverutils.storage;

import org.bukkit.entity.Player;

import java.util.*;

public class StoragelessStorage implements Storage {

    List<Player> tickets = new ArrayList<>();
    Map<Long, UUID> history = new HashMap<>();
    List<UUID> nether = new ArrayList<>();
    List<UUID> end = new ArrayList<>();

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public List<Player> getOpenTickets() {
        return tickets;
    }

    @Override
    public void addOpenTicket(Player player) {
        tickets.add(player);
    }

    @Override
    public void removeOpenTicket(Player player) {
        tickets.remove(player);
    }

    @Override
    public Map<Long, UUID> getTicketHistory() {
        return history;
    }

    @Override
    public void addToTicketHistory(Long time, UUID player) {
        history.put(time, player);
    }

    @Override
    public List<UUID> getNetherVotes() {
        return nether;
    }

    @Override
    public int getNetherVoteCount() {
        return nether.size();
    }

    @Override
    public void addNetherVote(UUID player) {
        nether.add(player);
    }

    @Override
    public void resetNetherVotes() {
        nether.clear();
    }

    @Override
    public List<UUID> getEndVotes() {
        return end;
    }

    @Override
    public int getEndVoteCount() {
        return end.size();
    }

    @Override
    public void addEndVote(UUID player) {
        end.add(player);
    }

    @Override
    public void resetEndVotes() {
        end.clear();
    }
}
