package me.austinlm.yamlbuddy;

import com.sk89q.bukkit.util.CommandsManagerRegistration;
import com.sk89q.minecraft.util.commands.*;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import me.austinlm.yamlbuddy.commands.InventoryCommands;
import me.austinlm.yamlbuddy.commands.RegionCommands;
import me.austinlm.yamlbuddy.menu.MainMenu;
import net.njay.MenuFramework;
import net.njay.MenuRegistry;
import net.njay.player.MenuPlayer;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class YAMLBuddy extends JavaPlugin {

    CommandsManager commands;


    @Override
    public void onEnable() {
        // Config

        // Commands
        setupCommands();

        // Listeners
        registerListeners();

        // Other
        setupMenus();
    }

    private void setupMenus() {
        MenuFramework.enable(new MenuRegistry(this, MainMenu.class), new PlayerManager());
    }

    @Override
    public void onDisable() {

    }

    public void registerListeners() {

    }

    public static WorldEditPlugin getWorldEdit() {
        WorldEditPlugin worldEditPlugin = null;
        worldEditPlugin = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
        if (worldEditPlugin == null) {
            Validate.notNull(null, "WorldEdit is null. Please install WorldEdit if you want to make use of its features.");
        }
        return worldEditPlugin;
    }

    public void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        try {
            this.commands.execute(cmd.getName(), args, sender, sender);
        } catch (CommandPermissionsException e) {
            sender.sendMessage(ChatColor.RED + "You don't have permission.");
        } catch (IllegalArgumentException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
        } catch (MissingNestedCommandException e) {
            sender.sendMessage(ChatColor.RED + e.getUsage());
        } catch (CommandUsageException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
            sender.sendMessage(ChatColor.RED + e.getUsage());
        } catch (WrappedCommandException e) {
            if (e.getCause() instanceof NumberFormatException) {
                sender.sendMessage(ChatColor.RED + "Number expected, string received instead.");
            } else if (e.getCause() instanceof IllegalArgumentException) {
                sender.sendMessage(ChatColor.RED + e.getCause().getMessage());
            } else {
                sender.sendMessage(ChatColor.RED + "An error has occurred. See console.");
                e.printStackTrace();
            }
        } catch (CommandException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
        }

        return true;
    }

    public static class ParentCommand {
        @com.sk89q.minecraft.util.commands.Command(aliases = {"yamlbuddy", "avicusyamlbuddy"}, desc = "All YAMLBuddy commands.")
        @NestedCommand({MenuCommand.class, InventoryCommands.InventoryParentCommand.class, RegionCommands.RegionParentCommand.class})
        public static void command() {
        }
    }

    public static class MenuCommand {
        @com.sk89q.minecraft.util.commands.Command(aliases = {"menu"}, desc = "Open the GUI.")
        public static void menu(CommandContext args, CommandSender sender) throws Exception {
            if (!(sender instanceof Player)) throw new CommandException("Only players can open GUIs!");

            MenuPlayer player = MenuFramework.getPlayerManager().getPlayer((Player) sender);

            player.setActiveMenu(new MainMenu(player.getMenuManager(), null));
        }
    }

    private void setupCommands() {
        this.commands = new CommandsManager<CommandSender>() {
            @Override
            public boolean hasPermission(CommandSender sender, String perm) {
                return sender instanceof ConsoleCommandSender || sender.hasPermission(perm);
            }
        };
        CommandsManagerRegistration cmdRegister = new CommandsManagerRegistration(this, this.commands);
        cmdRegister.register(ParentCommand.class);
    }
}
