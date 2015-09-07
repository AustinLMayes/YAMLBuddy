package me.austinlm.yamlbuddy.actions;

import com.google.common.collect.Maps;
import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import me.austinlm.yamlbuddy.utils.InventoryUtils;
import me.austinlm.yamlbuddy.utils.PasteUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryActions {
    public static void all(Player player) throws Exception {
        HashMap<Object, Object> res = Maps.newHashMap();
        for (Map.Entry<Integer, ItemStack> entry : InventoryUtils.inventoryToMap(player).entrySet()) {
            res.putAll(InventoryUtils.itemDataToYAMLMap(entry.getValue(), entry.getKey()));
        }

        res.putAll(InventoryUtils.convertArmor(player));

        YamlConfiguration conf = new YamlConfiguration();
        conf.set("loadout", res);
        player.sendMessage(pasteMessage(PasteUtils.uploadToDPaste(conf.saveToString())));
    }

    public static void hotbar(Player player) throws Exception {
        HashMap<Object, Object> res = Maps.newHashMap();
        for (Map.Entry<Integer, ItemStack> i : InventoryUtils.getHotbarItems(player).entrySet()) {
            res.putAll(InventoryUtils.itemDataToYAMLMap(i.getValue(), i.getKey()));
        }

        YamlConfiguration conf = new YamlConfiguration();
        conf.set("loadout", res);
        player.sendMessage(pasteMessage(PasteUtils.uploadToDPaste(conf.saveToString())));
    }

    public static void armor(Player player) throws Exception {
        HashMap<Object, Object> res = InventoryUtils.convertArmor(player);

        YamlConfiguration conf = new YamlConfiguration();
        conf.set("loadout", res);
        player.sendMessage(pasteMessage(PasteUtils.uploadToDPaste(conf.saveToString())));
    }

    public static void potion(Player player) throws Exception {
        if ((player).getItemInHand().getType() != Material.POTION) throw new CommandException("You must be holding a potion!");

        List<String> effects = InventoryUtils.potionEffectToYAMLString((player).getItemInHand());

        YamlConfiguration conf = new YamlConfiguration();
        conf.set("effects", effects);
        player.sendMessage(pasteMessage(PasteUtils.uploadToDPaste(conf.saveToString())));
    }

    public static String pasteMessage(String link) {
        StringBuilder msg = new StringBuilder();
        msg.append(ChatColor.GREEN).append(ChatColor.BOLD).append("Success! ").append(ChatColor.DARK_BLUE).append("You regions have been uploaded to ").append(ChatColor.WHITE).append(link);
        return msg.toString();
    }
}
