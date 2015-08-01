package me.austinlm.yamlbuddy.regions;

import com.sk89q.worldedit.Vector2D;
import org.bukkit.Color;
import org.bukkit.util.Vector;

public class VectorUtils {

    public static Vector weToBukkit(com.sk89q.worldedit.Vector v) {
        return new Vector(v.getX(), v.getY(), v.getZ());
    }

    public static Vector weToBukkit2D(com.sk89q.worldedit.Vector2D v) {
        return new Vector(v.getX(), 0, v.getZ());
    }

    public static Vector parseVector(String vec) {
        return new Vector(Double.parseDouble(vec.split(",")[0]), Double.parseDouble(vec.split(",")[1]), Double.parseDouble(vec.split(",")[2]));
    }

    public static Vector parse2DVector(String vec) {
        return new Vector(Double.parseDouble(vec.split(",")[0]), 0, Double.parseDouble(vec.split(",")[1]));
    }

    public static String vectorToString(Vector vec) {
        return vec.getBlockX()+","+vec.getBlockY()+","+vec.getBlockZ();
    }
}
