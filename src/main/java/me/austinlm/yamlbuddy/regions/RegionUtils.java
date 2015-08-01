package me.austinlm.yamlbuddy.regions;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.util.Vector;

public class RegionUtils {
    public static Region parseRegion(ConfigurationSection section) {
        if (section.getString("type").equalsIgnoreCase("cylinder"))
            return RegionUtils.parseCylinder(section);
        if (section.getString("type").equalsIgnoreCase("cuboid"))
            return RegionUtils.parseCuboid(section);
        if (section.getString("type").equalsIgnoreCase("sphere"))
            return RegionUtils.parseSphere(section);
        if (section.getString("type").equalsIgnoreCase("point"))
            return RegionUtils.parsePoint(section);
        return null;
    }

    public static Region parseCuboid(ConfigurationSection section) {
        Vector min = VectorUtils.parseVector(section.getString("min"));
        Vector max = VectorUtils.parseVector(section.getString("max"));
        return new CuboidRegion(min, max);
    }

    public static Region parseCylinder(ConfigurationSection section) {
        Vector base = VectorUtils.parseVector(section.getString("base"));
        int radius = (int) Double.parseDouble(section.getString("radius"));
        int height = (int) Double.parseDouble(section.getString("height"));
        return new CylinderRegion(base, radius, height);
    }

    public static Region parsePoint(ConfigurationSection section) {
        Vector base = VectorUtils.parseVector(section.getString("point"));
        return new PointRegion(base);
    }

    public static Region parseSphere(ConfigurationSection section) {
        Vector base = VectorUtils.parseVector(section.getString("center"));
        int radius = (int) Double.parseDouble(section.getString("radius"));
        return new SphereRegion(base, radius);
    }
}
