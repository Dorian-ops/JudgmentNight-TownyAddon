package me.piglinstudio.judgmentnight.Listeners;

import com.palmergames.bukkit.towny.event.town.toggle.TownTogglePVPEvent;
import me.piglinstudio.judgmentnight.JudgmentNight;
import me.piglinstudio.judgmentnight.NightSystem.NightBolean;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EventHandlers implements Listener {
    @EventHandler
    public void townToggleEvent(TownTogglePVPEvent event){
        boolean isNight = new NightBolean().getterIsNight();
        if(isNight == true){
            event.setCancelled(true);
            System.out.println("Отменили event" + isNight);
        }
    }
}
