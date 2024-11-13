package me.piglinstudio.judgmentnight.NightSystem;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Town;
import me.piglinstudio.judgmentnight.JudgmentNight;
import me.piglinstudio.judgmentnight.util.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NightPhase {
    YamlConfiguration configuration = JudgmentNight.getCustomYaml();
    int countTimer = 0;
    private BossBar bossBar = Bukkit.createBossBar("",BarColor.RED, BarStyle.SOLID);;
    private static Plugin plugin = Bukkit.getPluginManager().getPlugin("JudgmentNight");
    private BukkitRunnable task;
    public void nightStart(){
        boolean isNight = new NightBolean().getterIsNight();
        if(isNight == false){
            World world = Bukkit.getWorld(configuration.getString("worldName"));
            world.setTime(13000);
            int timeToLong = configuration.getInt("timeToLong");
            List<Town> towns = TownyAPI.getInstance().getTowns();
            if(towns != null){
                for (int i = 0; i < towns.size(); i++){
                    Town town = towns.get(i);
                    town.setAdminEnabledPVP(true);
                    town.setPVP(true);
                }
            }
            Bukkit.broadcastMessage(ChatColor.getMsg(configuration.getString("startEventMessage")));
            String bossBarString = configuration.getString("bossBarMessage");
            bossBar.setTitle(ChatColor.getMsg(bossBarString));
            bossBar.setProgress(1.0);
            double onePer = timeToLong / 100;
            new NightBolean().isNightSet(true);
            task = new BukkitRunnable() {
                @Override
                public void run() {
                    if(world.getTime() < 13000 || world.getTime() > 18000 ){
                        world.setTime(13000);
                    }
                    if(onePer == countTimer){
                        onePerSetValue(onePer);
                        bossBarProgressValue();
                    }
                    List<Player> players = (List<Player>) Bukkit.getOnlinePlayers();
                    for (int i = 0; i < players.size(); i++){
                        Player player = players.get(i);
                        bossBar.addPlayer(player);
                    }
                    String timeToEndNight = String.valueOf(timeToLong - countTimer);
                    bossBar.setTitle(ChatColor.getMsg(bossBarString + timeToEndNight));
                    countTimer++;
                    if(countTimer >= timeToLong){
                        nightStop();
                        this.cancel();
                    }
                }
            };
            task.runTaskTimer(plugin, 0L, 20L);

            DateFormat dateFormatter = new SimpleDateFormat("hh:mm:ss");
            Date today = Calendar.getInstance().getTime();
            String todayAsString = dateFormatter.format(today);
            configuration.set("timeToStartEvent", todayAsString);
        }
        else {
            System.out.println("Судная ночь уже запущенна");
        }
    }
    public void nightStop(){
        boolean isNight = new NightBolean().getterIsNight();
        if(isNight == true){
            World world = Bukkit.getWorld(configuration.getString("worldName"));
            world.setTime(1000);
            List<Town> towns = TownyAPI.getInstance().getTowns();
            if(towns != null){
                for (int i = 0; i < towns.size(); i++){
                    Town town = towns.get(i);
                    town.setAdminEnabledPVP(false);
                    town.setPVP(false);
                }
            }
            new NightBolean().isNightSet(false);
            Bukkit.broadcastMessage(ChatColor.getMsg(configuration.getString("endEventMessage")));
            bossBar.removeAll();
            bossBar.setVisible(false);
            List<Player> players = bossBar.getPlayers();
            if(players.size() >= 1){
                for(int i = 0; i <= players.size(); i++){
                    Player player = players.get(i);
                    bossBar.removePlayer(player);
                }
            }
            cancelTask();
            int timeToLong = configuration.getInt("timeToLong");
            countTimer =  timeToLong;

        }
        else {
            System.out.println("Судная ночь не запущенна");
        }
    }

    private Double onePerSetValue(double onePer){
        if(onePer != 0){
            onePer += onePer;
        }
        return onePer;
    }
    private void bossBarProgressValue(){
        double bossBarProgress = bossBar.getProgress();
        bossBarProgress -= 0.01;
        bossBar.setProgress(bossBarProgress);
    }
    public void cancelTask() {
        if (task != null) {
            task.cancel();
        }
    }

}
