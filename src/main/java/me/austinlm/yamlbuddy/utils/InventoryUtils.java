package me.austinlm.yamlbuddy.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryUtils {
    public static HashMap<Object, Object> convertArmor(Player player) {
        HashMap<Object, Object> res = Maps.newHashMap();
        ItemStack[] armor = player.getInventory().getArmorContents();
        if (armor[0] != null && armor[0].getType() != Material.AIR) res.putAll(itemDataToYAMLMap(armor[0], 100));
        if (armor[1] != null && armor[1].getType() != Material.AIR) res.putAll(itemDataToYAMLMap(armor[1], 101));
        if (armor[2] != null && armor[2].getType() != Material.AIR) res.putAll(itemDataToYAMLMap(armor[2], 102));
        if (armor[3] != null && armor[3].getType() != Material.AIR) res.putAll(itemDataToYAMLMap(armor[3], 103));
        return res;
    }

    public static HashMap<Integer, ItemStack> getHotbarItems(Player player) {
        HashMap<Integer, ItemStack> hotStacks = Maps.newHashMap();

        for (int i = 0; i <= 8; i++) {
            if (player.getInventory().getItem(i) != null && player.getInventory().getItem(i).getType() != Material.AIR)
                hotStacks.put(i, player.getInventory().getItem(i));
        }

        return hotStacks;
    }

    public static HashMap<Integer, ItemStack> inventoryToMap(Player player) {
        //Make a hashmap with the data of the inventory
        HashMap<Integer, ItemStack> map = Maps.newHashMap();
        //Method for receiving the slot of a object. A inventory has 36 slots
        for (int slot = 0; slot < 36; slot++) {
            for (ItemStack i : player.getInventory().getContents()) {
                if (player.getInventory().getItem(slot) == null || i == null) continue;
                if (player.getInventory().getItem(slot).getType() == Material.AIR || i.getType() == Material.AIR)
                    continue;
                if (i.equals(player.getInventory().getItem(slot))) {
                    if (!map.containsKey(Integer.valueOf(slot))) {
                        map.put(slot, i);
                    }
                }
            }
        }
        return map;
    }

    public static HashMap<Object, Object> itemDataToYAMLMap(ItemStack item, int slot) {
        HashMap<Object, Object> res = Maps.newHashMap();
        HashMap<Object, Object> iteminfo = Maps.newHashMap();

        iteminfo.put("item", item.getType().toString().toLowerCase().replace("_", " "));
        if (item.getAmount() != 1) iteminfo.put("amount", item.getAmount());
        if (item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if (meta.hasDisplayName())
                iteminfo.put("title", meta.getDisplayName().replace("??", "§"));

            if (meta.hasLore())
                iteminfo.put("lore", "'" + meta.getLore());

            if (meta.hasEnchants()) {
                List<String> enchants = Lists.newArrayList();
                for (Map.Entry<Enchantment, Integer> enchant : meta.getEnchants().entrySet()) {
                    enchants.add(enchant.getKey().getName().toString().replace("_", " ").toLowerCase() + ":" + enchant.getValue());
                }

                iteminfo.put("enchants", enchants);
            }
        }

        Object betterSlot;

        if (slot == 100) betterSlot = "boots";
        else if (slot == 101) betterSlot = "leggings";
        else if (slot == 102) betterSlot = "chestplate";
        else if (slot == 103) betterSlot = "helmet";
        else betterSlot = slot;


        res.put(betterSlot, iteminfo);
        return res;
    }

    public static List<String> potionEffectToYAMLString(ItemStack item) {
        List<String> effectsString = Lists.newArrayList();

        if (item.getType() == Material.POTION) {
            Collection<PotionEffect> effects = Potion.fromItemStack(item).getEffects();
            for (PotionEffect effect : effects) {
                effectsString.add(effect.getType().getName().toLowerCase() + ":" + (effect.getAmplifier() + 1) + "," + effect.getDuration());
            }
        }

        return effectsString;
    }
}
