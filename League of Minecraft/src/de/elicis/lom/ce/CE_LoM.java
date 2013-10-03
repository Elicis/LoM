package de.elicis.lom.ce;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.elicis.lom.Arena;
import de.elicis.lom.InvSave;
import de.elicis.lom.Main;
import de.elicis.lom.champions.Alistar;
import de.elicis.lom.champions.Ashe;
import de.elicis.lom.champions.Champion;
import de.elicis.lom.champions.Garen;
import de.elicis.lom.champions.Jax;
import de.elicis.lom.champions.Veigar;

public class CE_LoM implements CommandExecutor {
	private static Main plugin;

	public CE_LoM(Main t) {
		plugin = t;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2,
			String[] args) {
		Player player = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("lom")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED
						+ "Sorry but this Commands are only for Players!");
				return true;
			}
			/*
			 * 
			 * 2 Arguments
			 */
			if (args.length == 2) {
				/*
				 * Creates a new Arena
				 */
				if (args[0].equalsIgnoreCase("create")) {
					if (sender.hasPermission("lom.arena.create")) {
						if (!Main.Arenas.containsKey(args[1].toLowerCase())) {
							WorldCreator cre = new WorldCreator(
									args[1].toLowerCase());
							World world = cre.createWorld();
							world.setKeepSpawnInMemory(true);
							Arena arena = new Arena(args[1].toLowerCase());
							Main.Arenas.put(args[1].toLowerCase(), arena);
							sender.sendMessage(ChatColor.GREEN
									+ "Succesfully created!");
							return true;
						}
						sender.sendMessage(ChatColor.RED
								+ "This arena already exist!");
						return true;
					}
					sender.sendMessage(ChatColor.RED
							+ "You don't have the permission to do that!");
					return true;
				}
				/*
				 * Teleports you to the spawn of this arena
				 */
				if (args[0].equalsIgnoreCase("goto")) {
					if (sender.hasPermission("lom.arena.goto")) {
						if (Bukkit.getWorld(args[1].toLowerCase()) != null) {
							player.teleport(Bukkit.getWorld(args[1])
									.getSpawnLocation());
							return true;
						}
						sender.sendMessage(ChatColor.RED
								+ "This world doesnt exist!");
						return true;
					}
					sender.sendMessage(ChatColor.RED
							+ "You don't have the permission to do that!");
					return true;
				}
				/*
				 * Delete a existing arena
				 */
				if (args[0].equalsIgnoreCase("delete")) {
					if (sender.hasPermission("lom.arena.delete")) {
						if (Bukkit.getWorld(args[1].toLowerCase()) != null) {
							Bukkit.unloadWorld(args[1].toLowerCase(), true);
							if (Main.Arenas.containsKey(args[1])) {
								Main.Arenas.remove(args[1].toLowerCase());
							}
							sender.sendMessage(ChatColor.GREEN
									+ "Arena removed!");
							return true;
						}
						sender.sendMessage(ChatColor.RED
								+ "This arena doesn't exist!");
						return true;
					}
					sender.sendMessage(ChatColor.RED
							+ "You don't have the permission to do that!");
					return true;
				}
				/*
				 * Sets the lobby for a Team in your Arena
				 */
				if (args[0].equalsIgnoreCase("setLobby")) {
					if (sender.hasPermission("lom.arena.setLobby")) {
						if (getArenaW(player.getWorld()) != null) {
							Arena arena = getArenaW(player.getWorld());
							if (args[1].equalsIgnoreCase("red")) {
								arena.setLobbyRed(player.getLocation());
								Main.Arenas.put(arena.getName(), arena);
								sender.sendMessage(ChatColor.GREEN
										+ "Lobby set!");
								return true;
							}
							if (args[1].equalsIgnoreCase("blue")) {
								arena.setLobbyBlue(player.getLocation());
								Main.Arenas.put(arena.getName(), arena);
								sender.sendMessage(ChatColor.GREEN
										+ "Lobby set!");
								return true;
							}
							sender.sendMessage(ChatColor.RED
									+ "/lom setlobby <Team: red/blue>");
							return true;
						}
						sender.sendMessage(ChatColor.RED
								+ "This is not an arena!");
						return true;
					}
					sender.sendMessage(ChatColor.RED
							+ "You don't have the permission to do that!");
					return true;
				}
				/*
				 * Sets the spawn for a Team in your Arena
				 */
				if (args[0].equalsIgnoreCase("setSpawn")) {
					if (sender.hasPermission("lom.arena.setSpawn")) {
						if (getArenaW(player.getWorld()) != null) {
							Arena arena = getArenaW(player.getWorld());
							if (args[1].equalsIgnoreCase("red")) {
								arena.setSpawnRed(player.getLocation());
								Main.Arenas.put(arena.getName(), arena);
								sender.sendMessage(ChatColor.GREEN
										+ "Spawn set!");
								return true;
							}
							if (args[1].equalsIgnoreCase("blue")) {
								arena.setSpawnBlue(player.getLocation());
								Main.Arenas.put(arena.getName(), arena);
								sender.sendMessage(ChatColor.GREEN
										+ "Spawn set!");
								return true;
							}
							sender.sendMessage(ChatColor.RED
									+ "/lom setspawn <Team: red/blue>");
							return true;
						}
						sender.sendMessage(ChatColor.RED
								+ "This is not an arena!");
						return true;
					}
					sender.sendMessage(ChatColor.RED
							+ "You don't have the permission to do that!");
					return true;
				}
				/*
				 * Join a specific arena
				 */
				if (args[0].equalsIgnoreCase("join")) {
					if (sender.hasPermission("lom.arena.join")) {
						if (!isInArena(player)) {
							if (Main.Arenas
									.containsKey(args[1].toLowerCase())) {
								if (!(Main.Arenas.get(args[1].toLowerCase())
										.getPlayers().size() == 10)) {
									if (!Main.Arenas.get(args[1]).isActive()) {
										if (Main.Arenas.get(
												args[1].toLowerCase())
												.getLobbyBlue() != null
												&& Main.Arenas.get(
														args[1].toLowerCase())
														.getLobbyRed() != null
												&& Main.Arenas.get(
														args[1].toLowerCase())
														.getSpawnBlue() != null
												&& Main.Arenas.get(
														args[1].toLowerCase())
														.getSpawnBlue() != null) {
											InvSave.saveInventory(player);
											Arena arena = Main.Arenas
													.get(args[1].toLowerCase());
											joinTeam(arena, player);
											sender.sendMessage(ChatColor.GREEN
													+ "Success!");
											return true;
										}
										sender.sendMessage(ChatColor.RED
												+ "This arena isn't ready yet!");
										return true;
									}
									sender.sendMessage(ChatColor.RED
											+ "This arena is already active!");
									return true;
								}
								sender.sendMessage(ChatColor.RED
										+ "This arena is full!");
								return true;
							}
							sender.sendMessage(ChatColor.RED
									+ "This is not an arena!");
							return true;
						}
						sender.sendMessage(ChatColor.RED
								+ "You are already in an arena!");
						return true;
					}
					sender.sendMessage(ChatColor.RED
							+ "You don't have the permission to do that!");
					return true;
				}
				/*
				 * Choose a Champion
				 */
				if (args[0].equalsIgnoreCase("choose")) {
					if (sender.hasPermission("lom.champ.choose")) {
						if (isInArena(player)) {
							player.getInventory().clear();
							if (getArenaP(player).getChamps().get(
									player.getName()) != null) {
								Arena a = getArenaP(player);
								a.removeChamp(player);
								Main.Arenas.put(a.getName(), a);
							}
							Champion champ = null;
							switch (args[1].toLowerCase()) {
							case "ashe":
								champ = new Ashe(player);
								break;
							case "garen":
								champ = new Garen(player);
								break;
							case "veigar":
								champ = new Veigar(player);
								break;
							case "alistar":
								champ = new Alistar(player);
								break;
							case "jax":
								champ = new Jax(player);
								break;
							default:
								break;
							}

							if (champ != null) {
								Arena a = getArenaP(player);
								a.addChamp(player, champ);
								Main.Arenas.put(a.getName(), a);
								player.sendMessage(ChatColor.GREEN
										+ "You are now playing as: "
										+ ChatColor.GOLD + champ.getName());
								if (a.getChamps().size() == 10) {
									a.startGame();
								}
								return true;
							} else {
								sender.sendMessage(ChatColor.RED
										+ "This is not a valid chap!");
								return true;
							}
						}
						sender.sendMessage(ChatColor.RED
								+ "You are not in an arena!");
						return true;
					}
					sender.sendMessage(ChatColor.RED
							+ "You don't have the permission to do that!");
					return true;
				}
				if (args[0].equalsIgnoreCase("help")) {
					if (sender.hasPermission("lom.help")) {
						if (args[1].equalsIgnoreCase("admin")) {
							if (sender.hasPermission("lom.help.admin")) {
								sender.sendMessage(ChatColor.GOLD
										+ "League of Minecraft Help:");
								sender.sendMessage(ChatColor.GREEN
										+ "/lom create <name> = Creates a new arena with this name");
								sender.sendMessage(ChatColor.GREEN
										+ "/lom delete <name> = Deletes the arena with this name");
								sender.sendMessage(ChatColor.GREEN
										+ "/lom goto <name> = Teleports you to the spawn of this world/arena");
								sender.sendMessage(ChatColor.GREEN
										+ "/lom setLobby <red/blue> = Sets the lobby for this team");
								sender.sendMessage(ChatColor.GREEN
										+ "/lom setSpawn <red/blue> = Sets the spawn for this team");
								return true;
							}
						}
						if (args[2].equalsIgnoreCase("1")) {
							sender.sendMessage(ChatColor.GOLD
									+ "League of Minecraft Help:");
							sender.sendMessage(ChatColor.GREEN
									+ "/lom join <name> = Join this arena");
							sender.sendMessage(ChatColor.GREEN
									+ "/lom leave <name> = Leave this arena");
							sender.sendMessage(ChatColor.GREEN
									+ "/lom choose <champname> = select a champion");
							sender.sendMessage(ChatColor.GREEN
									+ "/lom money = Your money");

							return true;
						}
						sender.sendMessage(ChatColor.RED + "Wrong number!");
					}
					sender.sendMessage(ChatColor.RED
							+ "You don't have the permission to do that!");
					return true;
				}
				if (args[0].equalsIgnoreCase("start")) {
					if (sender.hasPermission("lom.arena.start")) {
						if (Main.Arenas.containsKey(args[1].toLowerCase())) {
							Arena arena = Main.Arenas.get(args[1]
									.toLowerCase());
							if (arena.getLobbyBlue() != null
									&& arena.getLobbyRed() != null
									&& arena.getSpawnBlue() != null
									&& arena.getSpawnBlue() != null) {
								boolean allchamps = true;
								ArrayList<String> ar = new ArrayList<String>();
								for (String str : arena.getPlayersS()) {
									if(arena.getChamps().get(str) == null){
										ar.add(str);
										allchamps = false;
									}
									continue;
								}
								if (allchamps) {
									arena.startGame();
									Main.Arenas.put(arena.getName(), arena);
									return true;
								}
								sender.sendMessage(ChatColor.RED
										+ "The following players haven't choosen their champ yet: ");
								for(int i = 0; i <= ar.size(); i++){
									player.sendMessage( ChatColor.GOLD +"- " + ar.get(i));
								}
								return true;

							}
							sender.sendMessage(ChatColor.RED
									+ "This arena isn't ready yet!");
							return true;
						}
						sender.sendMessage(ChatColor.YELLOW
								+ "Sorry but this arena doesnt exist!");
						return true;
					}
					sender.sendMessage(ChatColor.RED
							+ "You don't have the permission to do that!");
					return true;
				}

			}
			/*
			 * 
			 * 1 Argument
			 */
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("leave")) {
					if (sender.hasPermission("lom.arena.leave")) {
						if (isInArena(player)) {
							Arena arena = getArenaP(player);
							if (!arena.isActive()) {
								arena.removePlayer(player);
								player.teleport(plugin
										.getServer()
										.getWorld(
												Bukkit.getWorlds().get(0)
														.getName())
										.getSpawnLocation());
								InvSave.reloadInventory(player);
								player.setGameMode(GameMode.SURVIVAL);
								player.setMaxHealth(20.0);
								player.setFoodLevel(20);
								sender.sendMessage(ChatColor.GREEN
										+ "You left the arena!");
								return true;
							}
							sender.sendMessage(ChatColor.RED
									+ "Sorry but your game already started! You have to play this game to the end!");
							return true;
						}
						sender.sendMessage(ChatColor.RED
								+ "You are not in an arena!");
						return true;
					}
					sender.sendMessage(ChatColor.RED
							+ "You don't have the permission to do that!");
					return true;
				}
				if (args[0].equalsIgnoreCase("help")) {
					if (sender.hasPermission("lom.help")) {
						sender.sendMessage(ChatColor.GOLD
								+ "League of Minecraft Help:");
						sender.sendMessage(ChatColor.GREEN
								+ "/lom join <name> = Join this arena");
						sender.sendMessage(ChatColor.GREEN
								+ "/lom leave <name> = Leave this arena");
						sender.sendMessage(ChatColor.GREEN
								+ "/lom choose <champname> = select a champion");
						sender.sendMessage(ChatColor.GREEN
								+ "/lom money = Your money");
						return true;

					}
					sender.sendMessage(ChatColor.RED
							+ "You don't have the permission to do that!");
					return true;
				}
				if (args[0].equalsIgnoreCase("money")) {
					if (isInArena(player)) {
						if (getArenaP(player).isActive()) {
							int money = getArenaP(player).getChamps()
									.get(player.getName()).getMoney();
							sender.sendMessage(ChatColor.GOLD
									+ "You currently have: " + ChatColor.RED
									+ money + ChatColor.GOLD + " gold");
							return true;
						}
						sender.sendMessage(ChatColor.RED
								+ "This Arena has not started yet!");
						return true;
					}
					sender.sendMessage(ChatColor.RED
							+ "You have to be in an Arena to use this!");
					return true;
				}
				if(args[0].equalsIgnoreCase("shop")){
					if (sender.hasPermission("lom.arena.leave")) {
						if (isInArena(player)) {
							player.openInventory(plugin.shop.getPage(0));
							return true;
						}
						sender.sendMessage(ChatColor.RED
								+ "You have to be in an Arena to use this!");
						return true;
					}
					sender.sendMessage(ChatColor.RED
							+ "You don't have the permission to do that!");
					return true;
				}
				

			}

			sender.sendMessage(ChatColor.RED + "Use /lom help!");
			return true;
		}
		return true;
	}

	/*
	 * Returns true if the world is a Arena
	 */
	public boolean isArena(World world) {
		for (Arena arena : Main.Arenas.values()) {
			if (arena.getWorld().getName() == world.getName()) {
				return true;
			}
			continue;
		}
		return false;
	}

	/*
	 * Returns an Arena for a specific World
	 */
	public Arena getArenaW(World world) {
		for (Arena arena : Main.Arenas.values()) {
			if (arena.getWorld() == world) {
				return arena;
			}
			continue;
		}
		return null;
	}

	/*
	 * Return an Arena for a specific Player
	 */
	public Arena getArenaP(Player player) {
		for (Arena arena : Main.Arenas.values()) {
			if (arena.getPlayers().contains(player)) {
				return arena;
			}
			continue;
		}
		return null;
	}

	/*
	 * Checks if the player is already in an arena
	 */
	public boolean isInArena(Player player) {
		for (Arena arena : Main.Arenas.values()) {
			if (arena.getPlayersS().contains(player.getName())) {
				return true;
			}
			continue;
		}
		return false;
	}

	/*
	 * Random choosen Team
	 */
	public void joinTeam(Arena arena, Player player) {
		player.getInventory().clear();
		player.setLevel(1);
		player.setExp(0);
		player.setGameMode(GameMode.ADVENTURE);
		player.setSaturation((float) 20);
		if (arena.getTeamBlue().size() < 5 && arena.getTeamRed().size() < 5) {
			int randomTeam = (int) (Math.random() * 2 + 1);
			if (randomTeam == 1) {
				arena.addPlayerBlue(player);
				player.sendMessage(ChatColor.BLUE + "You are in Team Blue!");
				player.teleport(arena.getLobbyBlue().getLocation());
			} else {
				arena.addPlayerRed(player);
				player.sendMessage(ChatColor.RED + "You are in Team Red!");
				player.teleport(arena.getLobbyRed().getLocation());
			}
		} else if (arena.getTeamBlue().size() < 5) {
			arena.addPlayerBlue(player);
			player.sendMessage(ChatColor.BLUE + "You are in Team Blue!");
			player.teleport(arena.getLobbyBlue().getLocation());
		} else if (arena.getTeamRed().size() < 5) {
			arena.addPlayerRed(player);
			player.sendMessage(ChatColor.RED + "You are in Team Red!");
			player.teleport(arena.getLobbyRed().getLocation());
		}
		Main.Arenas.put(arena.getName(), arena);

	}

}
