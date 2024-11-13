package me.piglinstudio.judgmentnight.NightSystem;

import me.piglinstudio.judgmentnight.JudgmentNight;
import org.bukkit.configuration.file.YamlConfiguration;

public class NightBolean {
    private YamlConfiguration yamlConfiguration = JudgmentNight.getCustomYaml();
    public void isNightSet(boolean bolRes){
        yamlConfiguration.set("isNight", bolRes);
    }

    public boolean getterIsNight(){
        boolean isNight = yamlConfiguration.getBoolean("isNight");
        return  isNight;
    }
}
