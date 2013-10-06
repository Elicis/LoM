package de.elicis.lom.listener;

import java.util.HashMap;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import de.elicis.lom.Arena;
import de.elicis.lom.Config;
import de.elicis.lom.InvSave;
import de.elicis.lom.Main;
import de.elicis.lom.champions.Champion;
import de.elicis.lom.sign.LoM_Sign;
import de.elicis.lom.sign.LoM_SignType;

public class L_Player implements Listener {
	private static Main plugin;
	Config config = new Config();
	FileConfiguration fconfig;
	public L_Player(Main t) {
		plugin = t;
		fconfig = config.getConfig("config.yml");
	}

	HashMap<Player, ItemStack[]> Items = new HashMap<Player, ItemStack[]>();
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if (isInArena(player)) {
			if (!player.hasPermission("lom.blockbreak")) {
				event.setCancelled(true);
			}
			if (player.getItemInHand().getDurability() != 0) {
				player.getItemInHand().setDurability((short) 0.0);
			}
		}
		Block b = event.getBlock();
		if (b.getType() == Material.SIGN_POST
				|| b.getType() == Material.WALL_SIGN) {
			Sign sign = (Sign) b.getState();
			if (isLoM_Sign(sign)) {
				plugin.Signs.remove(getLoM_Sign(sign));
			}
		}
	}

	@EventHandler
	public void onMonsterKill(EntityDeathEvent event) {
		if (event.getEntity().getKiller() != null) {
			if (event.getEntity().getKiller().getType() == (EntityType.PLAYER)) {
				Player player = event.getEntity().getKiller();
				if (isInArena(player)) {
					if (getArenaP(player).isActive()) {
						if (event.getEntityType() == (EntityType.PLAYER)) {
							player.giveExp(10);
							Champion champ = getArenaP(player).getChamps().get(
									player);
							champ.setMoney(champ.getMoney() + 300);
							event.setDroppedExp(0);
						} else {
							Champion champ = getArenaP(player).getChamps().get(
									player);
							champ.setMoney(champ.getMoney() + 25);
							if (player.getLevel() < 18) {
								event.setDroppedExp(0);
								player.giveExp(2);
							} else {
								event.setDroppedExp(0);
							}
						}
					}
					event.getDrops().clear();
				}

			}
		}
	}

	@EventHandler
	public void onExpPickUp(PlayerExpChangeEvent event) {
		if (isInArena(event.getPlayer())) {
			event.setAmount(0);
		}
	}

	@EventHandler
	public void onPlayerDrop(PlayerDropItemEvent event) {
		if (isInArena(event.getPlayer())) {
			if (!event.getPlayer().hasPermission("lom.drop")) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		if (isInArena(event.getEntity())) {
			ItemStack[] list;
			event.setKeepLevel(true);
			list = event.getEntity().getInventory().getContents();
			Items.put(event.getEntity(), list);
			event.getDrops().clear();
			event.setDroppedExp(0);
		}
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		if (isInArena(event.getPlayer())) {
			Player player = event.getPlayer();
			if (Items.containsKey(event.getPlayer())) {
				event.getPlayer().getInventory()
						.setContents(Items.get(event.getPlayer()));
			}
			if (getArenaP(player).getTeam(player).equalsIgnoreCase("red")) {
				player.teleport(getArenaP(player).getSpawnRed().getLocation());
			} else if (getArenaP(player).getTeam(player).equalsIgnoreCase(
					"blue")) {
				player.teleport(getArenaP(player).getSpawnBlue().getLocation());
			}

		}
	}

	@EventHandler
	public void onChatEvent(AsyncPlayerChatEvent event) {
		final Player player = event.getPlayer();
		if (isInArena(player)) {
			final String message = event.getMessage();
			Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
				@Override
				public void run() {
					String name = player.getName();
					for (Player player2 : Bukkit.getServer().getOnlinePlayers()) {
						if (getArenaP(player).getTeam(player).equalsIgnoreCase(
								"blue")
								&& getArenaP(player2).getTeam(player2)
										.equalsIgnoreCase("blue")) {
							player2.sendMessage(ChatColor.BLUE + "[" + name
									+ "] " + ChatColor.WHITE + message);
						}
						if (getArenaP(player).getTeam(player).equalsIgnoreCase(
								"red")
								&& getArenaP(player2).getTeam(player2)
										.equalsIgnoreCase("red")) {
							player2.sendMessage(ChatColor.RED + "[" + name
									+ "] " + ChatColor.WHITE + message);
						}
					}
				}
			});
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Location to = event.getTo();
		Player player = event.getPlayer();
		if (isInArena(player)) {
			if (player.getItemInHand().getDurability() != 0) {
				player.getItemInHand().setDurability((short) 0.0);
			}
			
			/*
			if (event.getTo().getBlock().getTypeId() == 83) {
				for (Player player2 : getArenaP(player).getPlayers()) {
					if (player2.getLocation().distance(player.getLocation()) <= 6.0
							&& player2.getLocation().getBlock().getTypeId() == 83) {
						player.showPlayer(player2);
						player2.showPlayer(player);
					} else {
						player2.hidePlayer(player);
					}
				}
			}
			if (event.getFrom().getBlock().getTypeId() == 83) {
				for (Player player2 : getArenaP(player).getPlayers()) {
					player2.showPlayer(player);
				}
			}
			*/
		}
		if(isArena(to.getWorld())){
			Location spawn = to.getWorld().getSpawnLocation();
			Double distance = spawn.distance(to);
			if(distance >= fconfig.getDouble("arena.radius")){
				event.setCancelled(true);
				player.sendMessage(ChatColor.RED + "You reached the end of the arena!");
				if(to.subtract(4, 0, 0).distance(spawn) > distance){
					player.teleport(to.add(4, 0, 0));
				}else if(to.subtract(4, 0, 0).distance(spawn) < distance){
					player.teleport(to.subtract(4, 0, 0));
				}else{
					player.teleport(spawn);
				}
				
			}
		}
	}
	

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (isInArena((Player) event.getWhoClicked())) {
			if (!event.getWhoClicked().hasPermission("lom.inventory.change")) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onBowShoot(EntityShootBowEvent event) {
		if (event.getEntity() instanceof Player) {
			if (isInArena((Player) event.getEntity())) {
				event.getBow().setDurability((short) 0.0);
			}
		}
	}

	@EventHandler
	public void onSaturationChange(FoodLevelChangeEvent event) {
		if (event.getEntityType() == EntityType.PLAYER) {
			Player player = (Player) event.getEntity();
			if (isInArena(player)) {
				event.setFoodLevel(20);
			}
		}
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if (isInArena(player)) {
			getArenaP(player).removePlayer(player);
			player.setMaxHealth(20);
			player.setHealth(player.getMaxHealth());
			player.setFoodLevel(20);
			player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
			InvSave.reloadInventory(player);
			player.setGameMode(GameMode.SURVIVAL);
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (isInArena(player)) {
			player.setHealthScaled(true);
		}
	}

	@EventHandler
	public void onSignCreate(SignChangeEvent event) {
		Player player = event.getPlayer();
		if (event.getLine(0).equalsIgnoreCase("[Arena]")) {
			if (player.hasPermission("lom.sign.arena")) {
				if (!event.getLine(1).isEmpty()) {
					String line2 = event.getLine(1);
					if (Main.Arenas.get(line2) != null) {
						LoM_Sign sign = new LoM_Sign(line2.toLowerCase(),
								LoM_SignType.ARENA, event.getBlock()
										.getLocation(), Main.Arenas);
						plugin.Signs.add(sign);
						Bukkit.getLogger().log(Level.INFO,
								player.getName() + " created a new Arena Sign");
						player.sendMessage(ChatColor.GREEN
								+ "Succesfully created!");
					} else {
						if (player.hasPermission("lom.arena.create")) {
							WorldCreator cre = new WorldCreator(
									line2.toLowerCase());
							World world = cre.createWorld();
							world.setKeepSpawnInMemory(true);
							Arena arena = new Arena(line2.toLowerCase());
							Main.Arenas.put(line2.toLowerCase(), arena);
							LoM_Sign sign = new LoM_Sign(line2.toLowerCase(),
									LoM_SignType.ARENA, event
											.getBlock().getLocation(), Main.Arenas);
							plugin.Signs.add(sign);
							Bukkit.getLogger().log(
									Level.INFO,
									player.getName()
											+ " created a new Champion Sign");
							player.sendMessage(ChatColor.GREEN
									+ "Succesfully created!");
						}
					}
				}
			}

		}
		if (event.getLine(0).equalsIgnoreCase("[Champion]")) {
			if (player.hasPermission("lom.sign.champion")) {
				if (!event.getLine(1).isEmpty()) {
					String line2 = event.getLine(1);
					LoM_Sign sign = new LoM_Sign(line2.toLowerCase(),
							LoM_SignType.CHAMPION, event.getBlock()
									.getLocation(), Main.Arenas);
					plugin.Signs.add(sign);
					player.sendMessage(ChatColor.GREEN + "Succesfully created!");
				}
			}

		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block b = event.getClickedBlock();
			if (b.getType() == Material.SIGN_POST
					|| b.getType() == Material.WALL_SIGN) {
				Sign sign = (Sign) b.getState();
				if (isLoM_Sign(sign)) {
					LoM_Sign lomSign = getLoM_Sign(sign);
					if (lomSign.getType().getType()
							.equalsIgnoreCase(LoM_SignType.ARENA.getType())) {
						player.performCommand("lom join " + lomSign.getName());
					}
					if (lomSign.getType().getType()
							.equalsIgnoreCase(LoM_SignType.CHAMPION.getType())) {
						player.performCommand("lom choose " + sign.getLine(1));
					}
				}
			}
		}
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
	 * Checks if the Sign is a LoM_Sign
	 */
	public boolean isLoM_Sign(Sign sign) {
		boolean isSign = false;
		Location loc = sign.getBlock().getLocation();
		for (LoM_Sign lomSign : plugin.Signs) {
			if (isSameLocation(loc, lomSign.getLocation().getLocation())) {
				isSign = true;
			}
		}
		return isSign;
	}

	/*
	 * Return the LoM_Sign for a Sign
	 */
	public LoM_Sign getLoM_Sign(Sign sign) {
		Location loc = sign.getBlock().getLocation();
		for (LoM_Sign lomSign : plugin.Signs) {
			if (isSameLocation(loc, lomSign.getLocation().getLocation())) {
				return lomSign;
			}
		}
		return null;
	}
	/*
	 * Compares two Locations
	 */
	public boolean isSameLocation(Location loc1, Location loc2){
		int x1 = loc1.getBlockX();
		int x2 = loc2.getBlockX();
		int y1 = loc1.getBlockY();
		int y2 = loc2.getBlockY();
		int z1 = loc1.getBlockZ();
		int z2 = loc2.getBlockZ();
		String world1 = loc1.getWorld().getName();
		String world2 = loc2.getWorld().getName();
		boolean isSame = false;
		if(x1 == x2 && y1 == y2 && z1 == z2 && world1.equalsIgnoreCase(world2)){
			isSame = true;
		}
		return isSame;
	}
}
