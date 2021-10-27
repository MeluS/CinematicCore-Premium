package me.aleiv.core.paper.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import lombok.Getter;
import me.aleiv.core.paper.utilities.TCT.BukkitTCT;

public class TaskChainTickEvent extends Event {
    
    private static final @Getter HandlerList HandlerList = new HandlerList();
    @SuppressWarnings({"java:S116", "java:S1170"})
    private final @Getter HandlerList Handlers = HandlerList;
    private final @Getter BukkitTCT bukkitTCT;


    public TaskChainTickEvent(BukkitTCT bukkitTCT, boolean async) {
        super(async);
        this.bukkitTCT = bukkitTCT;
    }

    public TaskChainTickEvent(BukkitTCT bukkitTCT) {
        this(bukkitTCT, false);
    }

}