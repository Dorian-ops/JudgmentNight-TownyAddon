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
    private BossBar bossBar;
    private static Plugin plugin = Bukkit.getPluginManager().getPlugin("JudgmentNight");
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
            isNight = true;
            Bukkit.broadcastMessage(ChatColor.getMsg(configuration.getString("startEventMessage")));
            String bossBarString = configuration.getString("bossBarMessage");
            bossBar = Bukkit.createBossBar(ChatColor.getMsg(bossBarString),BarColor.RED, BarStyle.SOLID);
            final int[] onePer = new int[1];
            onePer[0] = timeToLong % 10;
            final double[] bossBarProgress = {0.0};
            new BukkitRunnable() {
                @Override
                public void run() {
                    if(world.getTime() < 12000){
                        world.setTime(13000);
                    }
                    if(countTimer >= timeToLong){
                        nightStop();
                        this.cancel();
                        return;
                    }
                    if(countTimer == onePer[0]){
                        onePer[0] += onePer[0];
                        bossBarProgress[0] += 0.1;
                        bossBar.setProgress(bossBarProgress[0]);
                    }
                    List<Player> players = (List<Player>) Bukkit.getOnlinePlayers();
                    for (int i = 0; i < players.size(); i++){
                       Player player = players.get(i);
                       bossBar.addPlayer(player);
                    }
                    String timeToEndNight = String.valueOf(timeToLong - countTimer);
                    bossBar.setTitle(ChatColor.getMsg(bossBarString + timeToEndNight));
                    countTimer++;
                }
            }.runTaskTimer(plugin, 0L, 20L); // 0L - задержка перед началом; 20L - 20 тиктов = 1 секунда
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
            isNight = false;
            Bukkit.broadcastMessage(ChatColor.getMsg(configuration.getString("endEventMessage")));
            bossBar.removeAll();
        }
        else {
            System.out.println("Судная ночь не запущенна");
        }
    }
}
