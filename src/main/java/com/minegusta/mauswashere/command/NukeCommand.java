package com.minegusta.mauswashere.command;

import com.censoredsoftware.helper.WrappedCommand;
import com.google.common.collect.Sets;
import com.minegusta.mauswashere.MausWasHere;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Set;

public class NukeCommand extends WrappedCommand {

    public NukeCommand() {
        super(false);
    }

    @Override
    public Set<String> getCommands() {
        return Sets.newHashSet("nuke");
    }

    @Override
    public boolean processCommand(CommandSender sender, Command command, String[] args) {
        if (!sender.hasPermission("explode.nuke")) {
            sender.sendMessage("Nice try " + sender + ", no permissions for you >:3");
        } else if (command.getName().equalsIgnoreCase("nuke") && sender.hasPermission("minegusta.nuke")) {
            try {
                final Location target = Bukkit.getServer().getPlayer(args[0]).getLocation();
                for (int i = 0; i < 60; i++) {
                    final int k = i;
                    Bukkit.getScheduler().scheduleSyncDelayedTask(MausWasHere.PLUGIN, new Runnable() {

                        @Override
                        public void run() {
                            nukeEffect(target, 110 + k, 30 * k, k / 4);

                        }

                    }, i);
                }
                Bukkit.getServer().broadcastMessage(ChatColor.YELLOW + sender.getName() + " nuked " + Bukkit.getServer().getPlayer(args[0]).getName() + "! Don't mess with North-Korea.");
            } catch (Exception e) {
                sender.sendMessage("Player is not real or not online. Try again.");
            }
        }
        return true;
    }

    public static void nukeEffect(Location target, int range, int particles, int offSetY) {
        target.getWorld().playSound(target, Sound.AMBIENCE_CAVE, 1F, 1F);
        target.getWorld().createExplosion(target.getX(), target.getY() + 3.9 + offSetY, target.getZ(), 6F, false, false);
        target.getWorld().spigot().playEffect(target, Effect.CLOUD, 1, 1, 3F, 0F, 3F, 1F, particles, range);
        target.getWorld().spigot().playEffect(target, Effect.LAVA_POP, 1, 1, 0.4F, 10F, 0.4F, 1F, particles, range);
        target.getWorld().spigot().playEffect(target, Effect.SMOKE, 1, 1, 0.4F, 10F, 0.4F, 1F, particles, range);
        target.getWorld().spigot().playEffect(target, Effect.FLAME, 1, 1, 0.4F, 10F, 0.4F, 1F, particles, range);
    }
}
