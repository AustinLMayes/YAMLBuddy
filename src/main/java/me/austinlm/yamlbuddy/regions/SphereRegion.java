package me.austinlm.yamlbuddy.regions;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SphereRegion implements Region {
    private List<Vector> range = new ArrayList<Vector>();
    public Vector base;
    private int radius;

    public SphereRegion(Vector center, int radius) {
        this.sphere(center, radius);
        this.base = center;
        this.radius = radius;
    }

    private void sphere(Vector loc, int r) {
        int bsize = r;
        int bx = loc.getBlockX();
        int by = loc.getBlockY();
        int bz = loc.getBlockZ();
        double zpow;
        double xpow;
        double bpow = Math.pow(r, 2);
        for (int z = -bsize; z <= bsize; z++) {
            zpow = Math.pow(z, 2);
            for (int x = -bsize; x <= bsize; x++) {
                xpow = Math.pow(x, 2);
                for (int y = -bsize; y <= bsize; y++) {
                    if ((xpow + Math.pow(y, 2) + zpow) <= bpow) {
                        range.remove(new Vector(bx + x, by + y, bz + z));
                        range.add(new Vector(bx + x, by + y, bz + z));
                    }
                }
            }
        }
    }

    @Override
    public List<Location> blocks(World w) {
        List<Location> returning = Lists.newArrayList();
        for (Vector v : this.range)
            returning.add(v.toLocation(w));
        return returning;
    }

    @Override
    public List<Location> blocksInChunk(Chunk c) {
        List<Location> returning = Lists.newArrayList();
        for (Vector v : this.range)
            if (v.toLocation(c.getWorld()).getChunk().equals(c))
                returning.add(v.toLocation(c.getWorld()));
        return returning;
    }

    @Override
    public HashMap<String, Object> toYaml(String name) {
        HashMap<String, Object> res = Maps.newHashMap();
        HashMap<String, Object> region = Maps.newHashMap();

        region.put("type", "sphere");
        region.put("radius", this.radius);
        region.put("base", VectorUtils.vectorToString(this.base));

        res.put(name, region);

        return res;
    }

    @Override
    public String toString() {
        return "Sphere region. Base: "+this.base.toString()+", radius: "+this.radius;
    }
}
