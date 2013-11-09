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
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import de.elicis.lom.Main;
import de.elicis.lom.api.LoM_API;
import de.elicis.lom.champions.Champion;
import de.elicis.lom.champions.skills.Skill;
import de.elicis.lom.data.Arena;
import de.elicis.lom.data.InvSave;
import de.elicis.lom.sign.LoM_Sign;
import de.elicis.lom.sign.LoM_SignType;
import de.elicis.lom.sign.LoM_TowerSign;
import de.elicis.lom.tower.Tower;
import de.elicis.lom.tower.TowerType;

public class L_Player implements Listener {
	FileConfiguration fconfig;
	public L_Player() {
		fconfig = de.elicis.lom.Main.getPlugin().getConfig();
	}

	HashMap<Player, ItemStack[]> Items = new HashMap<Player, ItemStack[]>();
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if (LoM_API.isInArena(player)) {
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
			if (LoM_API.isLoM_Sign(sign)) {
				de.elicis.lom.Main.getPlugin().Signs.remove(LoM_API.getLoM_Sign(sign));
			}
			if(LoM_API.isLoM_TowerSign(sign)){
				de.elicis.lom.Main.getPlugin().towerSigns.remove(LoM_API.getLoM_Sign(sign));
			}
		}
	}

	@EventHandler
	public void onMonsterKill(EntityDeathEvent event) {
		if (event.getEntity().getKiller() != null) {
			if (event.getEntity().getKiller().getType() == (EntityType.PLAYER)) {
				Player player = event.getEntity().getKiller();
				if (LoM_API.isInArena(player)) {
					if (LoM_API.getArenaP(player).isActive()) {
						if (event.getEntityType() == (EntityType.PLAYER)) {
							player.giveExp(10);
							Champion champ = LoM_API.getArenaP(player).getChamps().get(
									player);
							champ.setMoney(champ.getMoney() + 300);
							event.setDroppedExp(0);
						} else {
							Champion champ = LoM_API.getArenaP(player).getChamps().get(
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
		if (LoM_API.isInArena(event.getPlayer())) {
			event.setAmount(0);
		}
	}
	
	@EventHandler
	public void onPlayerPickup(PlayerPickupItemEvent event){
		if(LoM_API.isInArena(event.getPlayer())){
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerDrop(PlayerDropItemEvent event) {
		if (LoM_API.isInArena(event.getPlayer())) {
			if (!event.getPlayer().hasPermission("lom.drop")) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		if (LoM_API.isInArena(event.getEntity())) {
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
		if (LoM_API.isInArena(event.getPlayer())) {
			Player player = event.getPlayer();
			if (Items.containsKey(event.getPlayer())) {
				event.getPlayer().getInventory()
						.setContents(Items.get(event.getPlayer()));
			}
			if (LoM_API.getArenaP(player).getTeam(player).equalsIgnoreCase("red")) {
				player.teleport(LoM_API.getArenaP(player).getSpawnRed().getLocation());
			} else if (LoM_API.getArenaP(player).getTeam(player).equalsIgnoreCase(
					"blue")) {
				player.teleport(LoM_API.getArenaP(player).getSpawnBlue().getLocation());
			}

		}
	}

	@EventHandler
	public void onChatEvent(AsyncPlayerChatEvent event) {
		final Player player = event.getPlayer();
		if (LoM_API.isInArena(player)) {
			final String message = event.getMessage();
			Bukkit.getScheduler().runTaskAsynchronously(de.elicis.lom.Main.getPlugin(), new Runnable() {
				@Override
				public void run() {
					String name = player.getName();
					for (Player player2 : Bukkit.getServer().getOnlinePlayers()) {
						if (LoM_API.getArenaP(player).getTeam(player).equalsIgnoreCase(
								"blue")
								&& LoM_API.getArenaP(player2).getTeam(player2)
										.equalsIgnoreCase("blue")) {
							player2.sendMessage(ChatColor.BLUE + "[" + name
									+ "] " + ChatColor.WHITE + message);
						}
						if (LoM_API.getArenaP(player).getTeam(player).equalsIgnoreCase(
								"red")
								&& LoM_API.getArenaP(player2).getTeam(player2)
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
		if (LoM_API.isInArena(player)) {
			if (player.getItemInHand().getDurability() != 0) {
				player.getItemInHand().setDurability((short) 0.0);
			}
			
			/*
			if (event.getTo().getBlock().getTypeId() == 83) {
				for (Player player2 : LoM_API.getArenaP(player).getPlayers()) {
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
				for (Player player2 : LoM_API.getArenaP(player).getPlayers()) {
					player2.showPlayer(player);
				}
			}
			*/
		}
		
		if(LoM_API.isArena(to.getWorld())){
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
		if (LoM_API.isInArena((Player) event.getWhoClicked())) {
			if (!event.getWhoClicked().hasPermission("lom.inventory.change")) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onBowShoot(EntityShootBowEvent event) {
		if (event.getEntity() instanceof Player) {
			if (LoM_API.isInArena((Player) event.getEntity())) {
				event.getBow().setDurability((short) 0.0);
			}
		}
	}

	@EventHandler
	public void onSaturationChange(FoodLevelChangeEvent event) {
		if (event.getEntityType() == EntityType.PLAYER) {
			Player player = (Player) event.getEntity();
			if (LoM_API.isInArena(player)) {
				event.setFoodLevel(20);
			}
		}
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if (LoM_API.isInArena(player)) {
			LoM_API.getArenaP(player).removePlayer(player);
			player.setMaxHealth(20);
			player.setHealth((double) player.getMaxHealth());
			player.setFoodLevel(20);
			player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
			InvSave.reloadInventory(player);
			player.setGameMode(GameMode.SURVIVAL);
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (LoM_API.isInArena(player)) {
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
					if (de.elicis.lom.Main.getPlugin().Arenas.get(line2) != null) {
						LoM_Sign sign = new LoM_Sign(line2.toLowerCase(),
								LoM_SignType.ARENA, event.getBlock()
										.getLocation());
						de.elicis.lom.Main.getPlugin().Signs.add(sign);
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
							de.elicis.lom.Main.getPlugin().Arenas.put(line2.toLowerCase(), arena);
							LoM_Sign sign = new LoM_Sign(line2.toLowerCase(),
									LoM_SignType.ARENA, event
											.getBlock().getLocation());
							de.elicis.lom.Main.getPlugin().Signs.add(sign);
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
									.getLocation());
					de.elicis.lom.Main.getPlugin().Signs.add(sign);
					player.sendMessage(ChatColor.GREEN + "Succesfully created!");
				}
			}

		}
		if(event.getLine(0).equalsIgnoreCase("[Tower]")){
			if (player.hasPermission("lom.sign.tower")) {
				if (!event.getLine(1).isEmpty() && !event.getLine(2).isEmpty()) {
					String line2 = event.getLine(1);
					String line3 = event.getLine(2);
					Tower t = addTowers(line2, line3,event.getBlock()
							.getLocation());
					LoM_TowerSign sign = new LoM_TowerSign(t.getType().getType(), t);
					Main.getPlugin().towerSigns.add(sign);
					player.sendMessage(ChatColor.GREEN + "Succesfully created!");
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerItemHeld(PlayerItemHeldEvent event){
		Player player = event.getPlayer();
		int newSlot = event.getNewSlot();
		
		if(LoM_API.isInArena(player)){
			if(LoM_API.getArenaP(player).getChamps().get(player.getName()) != null){
				if(newSlot == LoM_API.getArenaP(player).getChamps().get(player.getName()).getBasicAttack().getSlot()){
					LoM_API.getArenaP(player).getChamps().get(player.getName()).getBasicAttack().useSkill();
					System.out.println("Used Basic attack");
				}
				for(Skill skill: LoM_API.getArenaP(player).getChamps().get(player.getName()).getSkills()){
					if(newSlot == skill.getSlot()){
						// Use Skill and then set item to an empty slot.
						skill.useSkill();
						System.out.println(player.getName() + " used skill");
					}
				}
				/*
				 * This slot will be empty, if the user tries to scroll this will mean
				 * they wont accidently pick a skill whilst scrolling and thus have to use the
				 * keyboard keys 1-9.
				 * This also resets the held item so that a skill does not get spammed.
				 */
				player.getInventory().setHeldItemSlot(8);
			}
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		// Use Basic Attack if left click
		if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK){
			if(LoM_API.isInArena(player)){
				if(LoM_API.getArenaP(player).getChamps().get(player) != null){
					LoM_API.getArenaP(player).getChamps().get(player).getBasicAttack().useSkill();
				}
			}
		}
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block b = event.getClickedBlock();
			if (b.getType() == Material.SIGN_POST
					|| b.getType() == Material.WALL_SIGN) {
				Sign sign = (Sign) b.getState();
				if (LoM_API.isLoM_Sign(sign)) {
					LoM_Sign lomSign = LoM_API.getLoM_Sign(sign);
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
			if(b.getType().equals(Material.IRON_BLOCK)){
				if(LoM_API.getArenaP(player).getChamps().get(player.getName()) != null){
					LoM_API.getArenaP(player).getChamps().get(player.getName()).setReady(true);
					boolean isready = true;
					if(LoM_API.getArenaP(player).getPlayers().size() == LoM_API.getArenaP(player).getChamps().size()){
						for(Champion c : LoM_API.getArenaP(player).getChamps().values()){
							if(!c.isReady()){
								isready = false;
							}
						}
						int minPlayer;
						if(fconfig.getInt("arena.minPlayer") > 10 ){
							minPlayer = 10;
						}else if (fconfig.getInt("arena.minPlayer") < 2 ){
							minPlayer = 2;
						}else{
							minPlayer = fconfig.getInt("arena.minPlayer");
						}
						if(LoM_API.getArenaP(player).getPlayers().size() > minPlayer){
							if(isready){
								LoM_API.getArenaP(player).startGame();
							}
						}
					}
					player.sendMessage(ChatColor.GOLD + "You are now ready.");
				}
			}
		}
	}
	public Tower addTowers(String line2, String line3, Location loc){
		Tower t = null;
		Arena a = null;
		if(LoM_API.getArenaW(loc.getWorld()) != null){
			a = LoM_API.getArenaW(loc.getWorld());
			if(line2.equalsIgnoreCase("red")){
				switch(line3.toLowerCase()){
				case "nexus_top":
					a.setT_red_nexus_top(new Tower(line2, loc, TowerType.NEXUSTOP));
					t = a.getT_red_nexus_top();
					break;
				case "nexus_bot":
					a.setT_red_nexus_bot(new Tower(line2, loc, TowerType.NEXUSBOT));
					t = a.getT_red_nexus_bot();
					break;
				case "inhib_top":
					a.setT_red_inhib_top(new Tower(line2, loc, TowerType.INHIBTOP));
					t = a.getT_red_inhib_top();
					break;
				case "inhib_mid":
					a.setT_red_inhib_mid(new Tower(line2, loc, TowerType.INHIBMID));
					t = a.getT_red_inhib_mid();
					break;
				case "inhib_bot":
					a.setT_red_inhib_bot(new Tower(line2, loc, TowerType.INHIBBOT));
					t = a.getT_red_inhib_bot();
					break;
				case "inner_top":
					a.setT_red_inner_top(new Tower(line2, loc, TowerType.INNERTOP));
					t = a.getT_red_inner_top();
					break;
				case "inner_mid":
					a.setT_red_inner_mid(new Tower(line2, loc, TowerType.INNERMID));
					t = a.getT_red_inner_mid();
					break;
				case "inner_bot":
					a.setT_red_inner_bot(new Tower(line2, loc, TowerType.INNERBOT));
					t = a.getT_red_inner_bot();
					break;
				case "outer_top":
					a.setT_red_outer_top(new Tower(line2, loc, TowerType.OUTERTOP));
					t = a.getT_red_outer_top();
					break;
				case "outer_mid":
					a.setT_red_outer_mid(new Tower(line2, loc, TowerType.OUTERMID));
					t = a.getT_red_outer_mid();
					break;
				case "outer_bot":
					a.setT_red_outer_bot(new Tower(line2, loc, TowerType.OUTERBOT));
					t = a.getT_red_outer_bot();
					break;
				default:
					break;
				}
			}else if(line2.equalsIgnoreCase("blue")){
				switch(line3.toLowerCase()){
				case "nexus_top":
					a.setT_blue_nexus_top(new Tower(line2, loc, TowerType.NEXUSTOP));
					t = a.getT_blue_nexus_top();
					break;
				case "nexus_bot":
					a.setT_blue_nexus_bot(new Tower(line2, loc, TowerType.NEXUSBOT));
					t = a.getT_blue_nexus_bot();
					break;
				case "inhib_top":
					a.setT_blue_inhib_top(new Tower(line2, loc, TowerType.INHIBTOP));
					t = a.getT_blue_inhib_top();
					break;
				case "inhib_mid":
					a.setT_blue_inhib_mid(new Tower(line2, loc, TowerType.INHIBMID));
					t = a.getT_blue_inhib_mid();
					break;
				case "inhib_bot":
					a.setT_blue_inhib_bot(new Tower(line2, loc, TowerType.INHIBBOT));
					t = a.getT_blue_inhib_bot();
					break;
				case "inner_top":
					a.setT_blue_inner_top(new Tower(line2, loc, TowerType.INNERTOP));
					t = a.getT_blue_inner_top();
					break;
				case "inner_mid":
					a.setT_blue_inner_mid(new Tower(line2, loc, TowerType.INNERMID));
					t = a.getT_blue_inner_mid();
					break;
				case "inner_bot":
					a.setT_blue_inner_bot(new Tower(line2, loc, TowerType.INNERBOT));
					t = a.getT_blue_inner_bot();
					break;
				case "outer_top":
					a.setT_blue_outer_top(new Tower(line2, loc, TowerType.OUTERTOP));
					t = a.getT_blue_outer_top();
					break;
				case "outer_mid":
					a.setT_blue_outer_mid(new Tower(line2, loc, TowerType.OUTERMID));
					t = a.getT_blue_outer_mid();
					break;
				case "outer_bot":
					a.setT_blue_outer_bot(new Tower(line2, loc, TowerType.OUTERBOT));
					t = a.getT_blue_outer_bot();
					break;
				default:
					break;
				}
			}
		}
		Main.getPlugin().Arenas.put(a.getName(), a);
		return t;
		
	}
	
}
