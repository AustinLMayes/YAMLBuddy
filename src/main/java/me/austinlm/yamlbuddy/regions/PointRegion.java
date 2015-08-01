package me.austinlm.yamlbuddy.regions;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PointRegion implements Region {
    private Vector block;

    public PointRegion(Vector block) {
        this.block = block;
    }

    @Override
    public List<Location> blocks(World w) {
        return Arrays.asList(this.block.toLocation(w));
    }

    @Override
    public String toString() {
        return "Point region. Location: "+this.block.toString();
    }

    @Override
    public List<Location> blocksInChunk(Chunk c) {
        List<Location> returning = Lists.newArrayList();
        if (block.toLocation(c.getWorld()).getChunk().equals(c))
            returning.add(block.toLocation(c.getWorld()));
        return returning;
    }

    @Override
    public HashMap<String, Object> toYaml(String name) {
        HashMap<String, Object> res = Maps.newHashMap();
        HashMap<String, Object> region = Maps.newHashMap();

        region.put("type", "point");
        region.put("point", VectorUtils.vectorToString(this.block));

        res.put(name, region);

        return res;
    }
}
