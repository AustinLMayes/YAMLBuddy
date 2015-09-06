package me.austinlm.yamlbuddy.commands;

import com.google.common.collect.Maps;
import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.NestedCommand;
import me.austinlm.yamlbuddy.utils.InventoryUtils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryCommands {

    public static class InventoryParentCommand {
        @Command(aliases = {"inv", "inventory"}, desc = "Commands for working with inventories")
        @NestedCommand({InventoryCommands.class})
        public static void inv() {
        }
    }

    @Command(aliases = "all", desc = "Generate a loadout from your inventory.", flags = "s")
    public static void all(final CommandContext args, CommandSender sender) throws Exception {
        if (!(sender instanceof Player)) throw new CommandException("You must be a player to use this command!");

        HashMap<Object, Object> res = Maps.newHashMap();
        for (Map.Entry<Integer, ItemStack> entry : InventoryUtils.inventoryToMap((Player) sender).entrySet()) {
            res.putAll(InventoryUtils.itemDataToYAMLMap(entry.getValue(), entry.getKey()));
        }

        res.putAll(InventoryUtils.convertArmor((Player) sender));

        YamlConfiguration conf = new YamlConfiguration();
        conf.set("loadout", res);
        sender.sendMessage(conf.saveToString());
    }

    @Command(aliases = "hotbar", desc = "Generate a loadout from your hotbar.", flags = "s")
    public static void hotbar(final CommandContext args, CommandSender sender) throws Exception {
        if (!(sender instanceof Player)) throw new CommandException("You must be a player to use this command!");

        HashMap<Object, Object> res = Maps.newHashMap();
        for (Map.Entry<Integer, ItemStack> i : InventoryUtils.getHotbarItems((Player) sender).entrySet()) {
            res.putAll(InventoryUtils.itemDataToYAMLMap(i.getValue(), i.getKey()));
        }

        YamlConfiguration conf = new YamlConfiguration();
        conf.set("loadout", res);
        sender.sendMessage(conf.saveToString());
    }

    @Command(aliases = "armor", desc = "Generate a loadout from the armor you are wearing.", flags = "s")
    public static void armor(final CommandContext args, CommandSender sender) throws Exception {
        if (!(sender instanceof Player)) throw new CommandException("You must be a player to use this command!");

        HashMap<Object, Object> res = InventoryUtils.convertArmor((Player) sender);

        YamlConfiguration conf = new YamlConfiguration();
        conf.set("loadout", res);
        sender.sendMessage(conf.saveToString());
    }

    @Command(aliases = "potion", desc = "Generate a YAML list from the effects in the potion you are holding", flags = "s")
    public static void potion(final CommandContext args, CommandSender sender) throws Exception {
        if (!(sender instanceof Player)) throw new CommandException("You must be a player to use this command!");

        if (((Player) sender).getItemInHand().getType() != Material.POTION) throw new CommandException("You must be holding a potion!");

        List<String> effects = InventoryUtils.potionEffectToYAMLString(((Player) sender).getItemInHand());

        YamlConfiguration conf = new YamlConfiguration();
        conf.set("effects", effects);
        sender.sendMessage(conf.saveToString());
    }
}
