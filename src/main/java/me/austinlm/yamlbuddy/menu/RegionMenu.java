package me.austinlm.yamlbuddy.menu;

import net.njay.Menu;
import net.njay.MenuManager;
import net.njay.annotation.MenuInventory;
import org.bukkit.inventory.Inventory;

@MenuInventory(
        slots = 9,
        name = "§5Region Utilities",
        onClose = MainMenu.class
)
public class RegionMenu extends Menu {
    public RegionMenu(MenuManager manager, Inventory inv) {
        super(manager, inv);
    }
}
