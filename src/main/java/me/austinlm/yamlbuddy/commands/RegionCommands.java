package me.austinlm.yamlbuddy.commands;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.NestedCommand;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;
import me.austinlm.yamlbuddy.BuddyPlayer;
import me.austinlm.yamlbuddy.PlayerManager;
import me.austinlm.yamlbuddy.YAMLBuddy;
import me.austinlm.yamlbuddy.regions.*;
import net.njay.MenuFramework;
import net.njay.player.MenuPlayer;
import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class RegionCommands {

    public static class RegionParentCommand {
        @Command(aliases = {"reg", "region"}, desc = "Commands for generating regions.")
        @NestedCommand({RegionCommands.class})
        public static void reg() {
        }
    }

    @Command(aliases = { "cuboid", "c" }, desc = "Generate a cuboid based on the current WorldEdit selection", usage = "[name] - The name of the region", min = 1, max = 1)
    public static void cuboid(final CommandContext args, CommandSender sender) throws CommandException {
        Validate.isTrue(sender instanceof Player, "This command cannot be run from the console. Get online!");
        WorldEditPlugin we = YAMLBuddy.getWorldEdit();
        BuddyPlayer p = (BuddyPlayer) MenuFramework.getPlayerManager().getPlayer((Player) sender);
        Selection s = we.getSelection(p.getBukkit());
        Validate.notNull(s, "Please make a selection first!");
        Validate.isTrue(s instanceof CuboidSelection, "Selection must be a cuboid.");
        CuboidSelection selection = (CuboidSelection) s;
        Vector min = selection.getNativeMinimumPoint();
        Vector max = selection.getNativeMaximumPoint();
        CuboidRegion region = new CuboidRegion(VectorUtils.weToBukkit(min), VectorUtils.weToBukkit(max));
        p.getCurrentYaml().putAll(region.toYaml(args.getString(0)));
        sendInfoMessage(sender);
    }

    @Command(aliases = { "cylinder", "cy" }, desc = "Generate a cylinder based on your current position", usage = "[radius] - The radius of the circle, [height] - The height of the cylinder, [name] - The name of the region", min = 3, max = 3)
    public static void cylinder(final CommandContext args, CommandSender sender) throws CommandException {
        Validate.isTrue(sender instanceof Player, "This command cannot be run from the console. Get online!");
        BuddyPlayer p = (BuddyPlayer) MenuFramework.getPlayerManager().getPlayer((Player) sender);
        int radius = args.getInteger(0);
        int height = args.getInteger(1);
        String name = args.getJoinedStrings(2);
        CylinderRegion region = new CylinderRegion(p.getBukkit().getLocation().toVector(), radius, height);
        p.getCurrentYaml().putAll(region.toYaml(name));
        sendInfoMessage(sender);
    }

    @Command(aliases = { "sphere", "s" }, desc = "Generate a sphere based on your current position", usage = "[radius] - The radius of the sphere, [name] - The name of the region", min = 2, max = 2)
    public static void sphere(final CommandContext args, CommandSender sender) throws CommandException {
        Validate.isTrue(sender instanceof Player, "This command cannot be run from the console. Get online!");
        BuddyPlayer p = (BuddyPlayer) MenuFramework.getPlayerManager().getPlayer((Player) sender);
        int radius = args.getInteger(0);
        String name = args.getJoinedStrings(1);
        SphereRegion region = new SphereRegion(p.getBukkit().getLocation().toVector(), radius);
        p.getCurrentYaml().putAll(region.toYaml(name));
        sendInfoMessage(sender);
    }

    @Command(aliases = { "point", "p" }, desc = "Generate a point based on where you are looking", usage = "[name] - The name of the region", min = 1, max = 1)
    public static void point(final CommandContext args, CommandSender sender) throws CommandException {
        Validate.isTrue(sender instanceof Player, "This command cannot be run from the console. Get online!");
        BuddyPlayer p = (BuddyPlayer) MenuFramework.getPlayerManager().getPlayer((Player) sender);
        String name = args.getJoinedStrings(0);
        Block looking = p.getBukkit().getTargetBlock((HashSet<Byte>) null, 100);
        PointRegion region = new PointRegion(looking.getLocation().toVector());
        p.getCurrentYaml().putAll(region.toYaml(name));
        sendInfoMessage(sender);
    }

    @Command(aliases = { "upload", "u" }, desc = "Upload all generated regions to DPaste", min = 0, max = 0, flags = "c", usage = "[-s] - Save regions instead of resetting user regions after upload.")
    public static void upload(final CommandContext args, CommandSender sender) throws CommandException {
        Validate.isTrue(sender instanceof Player, "This command cannot be run from the console. Get online!");
        BuddyPlayer p = (BuddyPlayer) MenuFramework.getPlayerManager().getPlayer((Player) sender);
        StringBuilder msg = new StringBuilder();
        msg.append(ChatColor.GREEN).append(ChatColor.BOLD).append("Success! ").append(ChatColor.DARK_BLUE).append("You regions have been uploaded to ").append(ChatColor.WHITE).append(p.uploadYAML(!args.hasFlag('s')));
        msg.append("\n");
        msg.append(ChatColor.AQUA).append("You chose to ").append(args.hasFlag('s')? ChatColor.GREEN : ChatColor.DARK_RED).append(args.hasFlag('s') ? "SAVE" : "CLEAR").append(" your cache");
        p.getBukkit().sendMessage(msg.toString());
    }

    public static void sendInfoMessage(CommandSender sender) {
        sender.sendMessage(ChatColor.GREEN + "Success! Your region has been saved to your " + ChatColor.GOLD + ChatColor.BOLD + "PERSONAL " + ChatColor.GREEN + "cache.");
        sender.sendMessage(ChatColor.BLUE + "To upload you regions to DPaste, type " + ChatColor.AQUA + "/yamlbuddy region upload " + ChatColor.ITALIC + "(Use -s to keep your regions in the cache after upload)" + ChatColor.BLUE + ".");
        sender.sendMessage(ChatColor.GOLD + "\u25BA" + ChatColor.RED + ChatColor.BOLD + "WARNING: " + ChatColor.DARK_PURPLE + "Your regions will be cleared if you log out." + ChatColor.GOLD + "\u25C0");
    }

}
