package me.austinlm.yamlbuddy.menu;

import me.austinlm.yamlbuddy.BuddyPlayer;
import net.njay.Menu;
import net.njay.MenuManager;
import net.njay.annotation.*;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

@MenuInventory(
        slots = 9,
        name = "§3YAMLBuddy Main Menu"
)
@NestedMenu({
        InventoryMenu.class,
        RegionMenu.class
})
public class MainMenu extends Menu {
    public MainMenu(MenuManager manager, Inventory inv) {
        super(manager, inv);
    }

    @MenuItem(slot = 1, item = @ItemStackAnnotation(name = "§4Inventory Utilities", material = Material.CHEST))
    public void openInventory(BuddyPlayer player) {
        player.setActiveMenu(new InventoryMenu(manager, null));
    }

    @MenuItem(slot = 7, item = @ItemStackAnnotation(name = "§4Region Utilities", material = Material.SNOW_BALL))
    public void openRegion(BuddyPlayer player) {
        player.setActiveMenu(new RegionMenu(manager, null));
    }
}
