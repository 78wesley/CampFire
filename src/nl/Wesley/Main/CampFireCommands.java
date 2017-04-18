package nl.Wesley.Main;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import static org.bukkit.ChatColor.*;

/**
 * All rights reserved.
 * CampFire Created by Wesley on 2/3/2017 on 4:19 PM.
 */
public class CampFireCommands implements CommandExecutor {

    private static CampFireLogFile settings = CampFireLogFile.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        if (cmd.getName().equalsIgnoreCase("campfire")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (player.isOp() || player.hasPermission("campfire.command.use")) {
                    if (args.length == 0 || args.length > 2) {
                        player.sendMessage(RED + "Usage: /campfire <add|remove|debug|list|on|off|reload>");
                    } else {
                        if (args[0].equalsIgnoreCase("add")) {
                            if (player.hasPermission("campfire.command.use.add")) {
                                if (args.length == 1 || args.length > 2) {
                                    player.sendMessage(RED + "Usage: /campfire add <name>");
                                } else {
                                    if (settings.containsData("locations." + args[1])) {
                                        player.sendMessage(RED + "It could not be te same!");
                                    } else {
                                        Location loc = new Location(player.getWorld(), player.getLocation().getX(),
                                                player.getLocation().getY() - 1.33008, player.getLocation().getZ());
                                        CampFireBuild.addCampFire(loc, args[1]);
                                        CampFireBuild.setFire(true, args[1]);
                                        settings.setData("locations." + args[1] + ".world", loc.getWorld().getName());
                                        settings.setData("locations." + args[1] + ".x", loc.getX());
                                        settings.setData("locations." + args[1] + ".y", loc.getY());
                                        settings.setData("locations." + args[1] + ".z", loc.getZ());
                                        settings.setData("locations." + args[1] + ".fire", true);
                                        settings.saveData();
                                        player.sendMessage(GREEN + "You have succesfully added " + args[1] + "!");
                                    }
                                }
                            } else {
                                player.sendMessage(RED + "No Permission");
                            }
                        } else if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("rem")) {
                            if (player.hasPermission("campfire.command.use.remove")) {
                                if (args.length == 1 || args.length > 2) {
                                    player.sendMessage(RED + "Usage: /campfire remove <name>");
                                } else {
                                    if (settings.containsData("locations." + args[1])) {
                                        settings.getData("locations." + args[1]);
                                        settings.setData("locations." + args[1], null);
                                        settings.saveData();
                                        CampFireBuild.removeCampFire(args[1]);
                                        player.sendMessage(GREEN + "You have succesfully removed " + args[1] + "!");
                                    } else {
                                        player.sendMessage(RED + "That name does not exist!");
                                    }
                                }
                            } else  {
                                player.sendMessage(RED + "No Permission");
                            }
                        } else if (args[0].equalsIgnoreCase("debug")) {
                            if (player.hasPermission("campfire.command.use.debug")) {
                                CampFireBuild.debugCampfires();
                            } else {
                                player.sendMessage(RED + "No Permission");
                            }
                        } else if (args[0].equalsIgnoreCase("list")) {
                            if (player.hasPermission("campfire.command.use.list")) {
                                String warps = "";
                                for (String key : settings.getData().getConfigurationSection("locations").getKeys(false)) {
                                    warps = warps + key.replace("[", "").replace("]", "") + ", ";
                                    if (key.isEmpty()) {
                                        player.sendMessage(GOLD + "CampFires: ");
                                    }
                                }
                                player.sendMessage(GOLD + "CampFires: " + WHITE + warps);
                            } else {
                                player.sendMessage(RED + "No Permission");
                            }
                        } else if (args[0].equalsIgnoreCase("on")) {
                            if (player.hasPermission("campfire.command.use.on")) {
                                if (args.length == 1 || args.length > 2) {
                                    player.sendMessage(RED + "Usage: /campfire on <name>");
                                } else {
                                    if (settings.containsData("locations." + args[1])) {
                                        if (!CampFireBuild.getFire(args[1])) {
                                            CampFireBuild.setFire(true, args[1]);
                                            settings.setData("locations." + args[1] + ".fire", true);
                                            settings.saveData();
                                            player.sendMessage(GREEN + "The fire has been turned on for " + WHITE + args[1] + GREEN + "!");
                                        } else {
                                            player.sendMessage(GREEN + "The fire is already on for " + WHITE + args[1] + GREEN + "!");
                                        }
                                    } else {
                                        player.sendMessage(RED + "That name does not exist!");
                                    }
                                }
                            } else {
                                player.sendMessage(RED + "No Permission");
                            }
                        } else if (args[0].equalsIgnoreCase("off")) {
                            if (player.hasPermission("campfire.command.use.off")) {
                                if (args.length == 1 || args.length > 2) {
                                    player.sendMessage(RED + "Usage: /campfire off <name>");
                                } else {
                                    if (settings.containsData("locations." + args[1])) {
                                        if (CampFireBuild.getFire(args[1])) {
                                            CampFireBuild.setFire(false, args[1]);
                                            settings.setData("locations." + args[1] + ".fire", false);
                                            settings.saveData();
                                            player.sendMessage(GREEN + "The fire has been turned off for " + WHITE + args[1] + GREEN + "!");
                                        } else {
                                            player.sendMessage(GREEN + "The fire is already off for " + WHITE + args[1] + GREEN + "!");
                                        }
                                    } else {
                                        player.sendMessage(RED + "That name does not exist!");
                                    }
                                }
                            } else {
                                player.sendMessage(RED + "No Permission");
                            }
                        } else if (args[0].equalsIgnoreCase("reload")) {
                            if (player.hasPermission("campfire.command.use.reload")) {
                                settings.reloadData();
                                CampFireBuild.deSpawnCampFire();
                                CampFireBuild.loadCampFire();
                                player.sendMessage(GREEN + "Succesfully reloaded!");
                            } else {
                                player.sendMessage(RED + "No Permission");
                            }
                        } else {
                            player.sendMessage(RED + "Usage: /campfire <add|remove|debug|list|on|off|reload>");
                        }
                    }
                } else {
                    player.sendMessage(RED + "No Permission");
                }
            } else {
                sender.sendMessage(RED + "You cant use it in the console");
            }
        }
        return false;
    }
}
