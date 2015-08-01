package me.austinlm.yamlbuddy;

import com.google.common.collect.Maps;
import com.sk89q.minecraft.util.commands.CommandException;
import me.austinlm.yamlbuddy.utils.PasteUtils;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class BuddyPlayer {
    private Player bukkit;
    private HashMap<String, Object> currentYaml = Maps.newHashMap();

    public BuddyPlayer(Player bukkit) {
        this.bukkit = bukkit;
    }

    public Player getBukkit() {
        return bukkit;
    }

    public HashMap<String, Object> getCurrentYaml() {
        return currentYaml;
    }

    public String uploadYAML(boolean clear) throws CommandException {
        YamlConfiguration conf = new YamlConfiguration();
        conf.set("regions", currentYaml);

        if (clear) this.currentYaml = Maps.newHashMap();

        return PasteUtils.uploadToDPaste(conf.saveToString());
    }
}
