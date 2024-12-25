package com.lwsnr.delmvinv;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Level;

public class DeleteMVInvPlugin extends JavaPlugin implements CommandExecutor {

    @Override
    public void onEnable() {
        if (getCommand("delmvinv") != null) {
            getCommand("delmvinv").setExecutor(this);
        }
        getLogger().info("DeleteMVInvPlugin enabled on Paper!");
    }

    @Override
    public void onDisable() {
        getLogger().info("DeleteMVInvPlugin disabled.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("§cUsage: /delmvinv <argument>");
            return true;
        }

        String mvArgument = args[0];

        File worldsFolder = new File(getDataFolder().getParentFile(),
                "Multiverse-Inventories/worlds/" + mvArgument);
        File groupsFolder = new File(getDataFolder().getParentFile(),
                "Multiverse-Inventories/groups/" + mvArgument);

        boolean worldsDeleted = deleteDirectory(worldsFolder);
        boolean groupsDeleted = deleteDirectory(groupsFolder);

        if (worldsDeleted || groupsDeleted) {
            sender.sendMessage("§aSuccessfully deleted specified folders for: " + mvArgument);
            getLogger().log(Level.INFO, "Deleted folders for {0}", mvArgument);
        } else {
            sender.sendMessage("§cNo folders found or failed to delete for: " + mvArgument);
            getLogger().log(Level.WARNING, "Unable to find or delete folders for {0}", mvArgument);
        }

        return true;
    }

    private boolean deleteDirectory(File file) {
        if (!file.exists()) {
            return false;
        }
        if (file.isDirectory()) {
            File[] contents = file.listFiles();
            if (contents != null) {
                for (File child : contents) {
                    deleteDirectory(child);
                }
            }
        }
        return file.delete();
    }
}