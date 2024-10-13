package me.piglinstudio.judgmentnight;

import me.piglinstudio.judgmentnight.Listeners.CommandListener;
import me.piglinstudio.judgmentnight.Listeners.EventHandlers;
import me.piglinstudio.judgmentnight.NightSystem.NightBolean;
import me.piglinstudio.judgmentnight.NightSystem.TimerToStartNight;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class JudgmentNight extends JavaPlugin {
    private static File configFile;
    public static YamlConfiguration getCustomYaml() {
        return customYaml;
    }

    private static YamlConfiguration customYaml;
    @Override
    public void onEnable() {
        System.out.println(
                        "██████╗░░█████╗░██████╗░██╗░█████╗░███╗░░██╗\n" +
                        "██╔══██╗██╔══██╗██╔══██╗██║██╔══██╗████╗░██║\n" +
                        "██║░░██║██║░░██║██████╔╝██║███████║██╔██╗██║\n" +
                        "██║░░██║██║░░██║██╔══██╗██║██╔══██║██║╚████║\n" +
                        "██████╔╝╚█████╔╝██║░░██║██║██║░░██║██║░╚███║\n" +
                        "╚═════╝░░╚════╝░╚═╝░░╚═╝╚═╝╚═╝░░╚═╝╚═╝░░╚══╝");

        getCommand("startNight").setExecutor(new CommandListener());
        getCommand("stopNight").setExecutor(new CommandListener());
        getServer().getPluginManager().registerEvents(new EventHandlers(), this);

        if(!getDataFolder().exists()){
            getDataFolder().mkdirs();
        }

        configFile = new File(getDataFolder(), "config.yml");
        customYaml = YamlConfiguration.loadConfiguration(configFile);

        if(!configFile.exists()){
            try {
                configFile.createNewFile();
                customYaml.set("timeToStartEvent", "18:00:00");
                System.out.println("Строка timeToStartEvent была создана");
                customYaml.set("timeToLong", 600);
                customYaml.set("startEventMessage", "&cВнимание ивент судная ночь!");
                customYaml.set("endEventMessage", "&cВнимание ивент судная ночь закончился!");
                customYaml.set("bossBarMessage", "&cСудная ночь: ");
                customYaml.set("worldName", "world");
                customYaml.save(configFile);
            }
            catch (IOException e){

            }
        }
        new TimePlaceholder().register();
        new TimerToStartNight().timerStartToNight();
        new NightBolean().isNightSet(false);
    }
    @Override
    public void onDisable() {

    }

}
