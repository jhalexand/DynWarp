package com.cnaude.dynwarp;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.dynmap.DynmapCommonAPI;
import org.dynmap.markers.Marker;
import org.dynmap.markers.MarkerAPI;
import org.dynmap.markers.MarkerSet;

/**
 *
 * @author cnaude
 */
public class ListCommand implements CommandExecutor {

    private final DynWarp plugin;
    private final DynmapCommonAPI dynmapCommonAPI;
    private final MarkerAPI markerAPI;

    public ListCommand(DynWarp instance) {
        this.plugin = instance;
        this.dynmapCommonAPI = (DynmapCommonAPI) plugin.getServer().getPluginManager().getPlugin("dynmap");
        markerAPI = dynmapCommonAPI.getMarkerAPI();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender == null) {
            return true;
        }
        if (sender instanceof Player) {
            if (!sender.hasPermission("dynwarp.list")) {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
                return true;
            }
        }

        for (MarkerSet ms : markerAPI.getMarkerSets()) {
            List<Marker> sortedMarkers = ms.getMarkers().stream().collect(Collectors.toList());            
            Collections.sort(sortedMarkers, (o1, o2) -> o1.getMarkerID().compareTo(o2.getMarkerID()));            
            for (Marker m : sortedMarkers) {
                if (!m.getMarkerID().startsWith("_spawn_")) {
                    sender.sendMessage(ChatColor.GOLD + m.getLabel() + ChatColor.AQUA + " [" + m.getWorld() + "]");
                }
                //sender.sendMessage(ChatColor.GOLD + m.getMarkerID() + ": " + ChatColor.WHITE + m.getLabel() + ChatColor.AQUA + " [" + m.getWorld() + ": " + m.getX() + "," + m.getY() + "," + m.getZ() + "]");
            }
        }

        return true;
    }
}
