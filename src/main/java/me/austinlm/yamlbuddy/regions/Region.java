package me.austinlm.yamlbuddy.regions;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.HashMap;
import java.util.List;

public interface Region {
    public List<Location> blocks(World w);
    public List<Location> blocksInChunk(Chunk c);
    public HashMap<String, Object> toYaml(String name);
}
