package me.piglinstudio.judgmentnight;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimePlaceholder extends PlaceholderExpansion{
    @Override
    public String getAuthor() {
        return "Dorian - doria.n";
    }
    @Override
    public String getIdentifier() {
        return "JudgmentNight";
    }
    @Override
    public String getVersion() {
        return "1.0.1";
    }
    @Override
    public boolean canRegister() {
        return true; // Возвращаем true, если хотите зарегистрировать
    }
    @Override
    public @NotNull String onPlaceholderRequest(Player player, String params){
        if(params.equalsIgnoreCase("getTimeToStartNight")){
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
            if(date1 != date2){
                long diff = date2.getTime() - date1.getTime();
                long diffSeconds = diff / 1000 % 60;
                long diffMinutes = diff / (60 * 1000) % 60;
                long diffHours = diff / (60 * 60 * 1000) % 24;
                String result = diffHours + ":" + diffMinutes + ":" + diffSeconds;
                return result;
            }
            return "00:00:00";
        }
        return null;
    }
}
