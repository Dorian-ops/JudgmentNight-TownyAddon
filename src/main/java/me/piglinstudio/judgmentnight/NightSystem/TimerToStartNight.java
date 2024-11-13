package me.piglinstudio.judgmentnight.NightSystem;

import me.piglinstudio.judgmentnight.JudgmentNight;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimerToStartNight {
    private static Plugin plugin = Bukkit.getPluginManager().getPlugin("JudgmentNight");
    public void timerStartToNight(){
        YamlConfiguration yamlConfiguration = JudgmentNight.getCustomYaml();
        String dateString = yamlConfiguration.getString("timeToStartEvent");
        DateFormat dateFormatter = new SimpleDateFormat("hh:mm:ss");
        Date date1;
        try {
            date1 = dateFormatter.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Date today = Calendar.getInstance().getTime();
        String todayAsString = dateFormatter.format(today);
        Date date2;
        try {
            date2 = dateFormatter.parse(todayAsString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Long wait = null;
        if(date1.getTime() < date2.getTime()){
            wait = date2.getTime() - date1.getTime();
        } else if (date1.getTime() > date2.getTime()) {
            wait = date1.getTime() - date2.getTime();
        } else {
            wait = 0L;
        }

    }
}
