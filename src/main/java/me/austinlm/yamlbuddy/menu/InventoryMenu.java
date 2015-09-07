package me.austinlm.yamlbuddy.menu;

import me.austinlm.yamlbuddy.BuddyPlayer;
import me.austinlm.yamlbuddy.actions.InventoryActions;
import net.njay.Menu;
import net.njay.MenuFramework;
import net.njay.MenuManager;
import net.njay.annotation.ItemStackAnnotation;
import net.njay.annotation.MenuInventory;
import net.njay.annotation.MenuItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

@MenuInventory(
        slots = 18,
        name = "§5Inventory Utilities",
        onClose = MainMenu.class
)
public class InventoryMenu extends Menu {
    public InventoryMenu(MenuManager manager, Inventory inv) {
        super(manager, inv);
    }

    @MenuItem(slot = 1, item = @ItemStackAnnotation(material = Material.CHEST, name = "§4All", lore = {"§7Create a loadout from your entire inventory."}))
    public void all(BuddyPlayer player) throws Exception {
        InventoryActions.all(player.getBukkit());
        player.getBukkit().closeInventory();
    }

    @MenuItem(slot = 7, item = @ItemStackAnnotation(material = Material.WOOD_STEP, name = "§4Hotbar", lore = {"§7Create a loadout from your hotbar."}))
    public void hotbar(BuddyPlayer player) throws Exception {
        InventoryActions.hotbar(player.getBukkit());
        player.getBukkit().closeInventory();
    }

    @MenuItem(slot = 10, item = @ItemStackAnnotation(material = Material.GOLD_CHESTPLATE, name = "§4Armor", lore = {"§7Create a loadout from the armor you are wearing."}))
    public void armor(BuddyPlayer player) throws Exception {
        InventoryActions.armor(player.getBukkit());
        player.getBukkit().closeInventory();
    }

    @MenuItem(slot = 16, item = @ItemStackAnnotation(material = Material.GLASS_BOTTLE, name = "§4Potion", lore = {"§7Create a potion from your active potion effects."}))
    public void potion(BuddyPlayer player) throws Exception {
        InventoryActions.potion(player.getBukkit());
        player.getBukkit().closeInventory();
    }
}
