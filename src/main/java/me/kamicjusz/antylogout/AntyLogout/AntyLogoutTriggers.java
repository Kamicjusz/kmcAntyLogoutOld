package me.kamicjusz.antylogout.AntyLogout;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import me.kamicjusz.antylogout.Main;
import me.kamicjusz.antylogout.Utils.ChatUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AntyLogoutTriggers implements Listener {

    private final Main plugin;
    private AntyLogout antyLogoutClass;
    private WorldGuard worldGuard;
    List<Player> kicked;
    RegionContainer container;
    RegionManager regions;
    ProtectedRegion spawn;

    Map<UUID, Integer> antyLogoutList;

    public AntyLogoutTriggers(Main plugin){
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        antyLogoutClass = plugin.getAntyLogoutClass();
        antyLogoutList = antyLogoutClass.getAntyLogoutList();
        kicked = new ArrayList<>();
        worldGuard = plugin.getWorldGuard();
        container = worldGuard.getPlatform().getRegionContainer();
        regions = container.get(BukkitAdapter.adapt(Bukkit.getWorld("world")));
        if(regions.hasRegion("spawn")){
            spawn = regions.getRegion("spawn");
        }else {
            ChatUtil.sendMessage(Bukkit.getConsoleSender(), "&7[KMCAntyLogout] &cBLAD!!!");
            ChatUtil.sendMessage(Bukkit.getConsoleSender(), "&7[KMCAntyLogout] &cBLAD!!!");
            ChatUtil.sendMessage(Bukkit.getConsoleSender(), "&7[KMCAntyLogout] &cBLAD!!!");
            ChatUtil.sendMessage(Bukkit.getConsoleSender(), "&7[KMCAntyLogout] &cBLAD!!!");
            ChatUtil.sendMessage(Bukkit.getConsoleSender(), "&7[KMCAntyLogout] &cBLAD!!!");
            ChatUtil.sendMessage(Bukkit.getConsoleSender(), "&7[KMCAntyLogout] &cBLAD!!!");
            ChatUtil.sendMessage(Bukkit.getConsoleSender(), "&7[KMCAntyLogout] &cBLAD!!!");
            ChatUtil.sendMessage(Bukkit.getConsoleSender(), "&7[KMCAntyLogout] &cBLAD!!!");
            ChatUtil.sendMessage(Bukkit.getConsoleSender(), "&7[KMCAntyLogout] &cBLAD!!!");
            ChatUtil.sendMessage(Bukkit.getConsoleSender(), "&7[KMCAntyLogout] &cBLAD!!! Nie znaleziono regionu 'spawn'");
            ChatUtil.sendMessage(Bukkit.getConsoleSender(), "&7[KMCAntyLogout] &cBLAD!!!");
            ChatUtil.sendMessage(Bukkit.getConsoleSender(), "&7[KMCAntyLogout] &cBLAD!!!");
            ChatUtil.sendMessage(Bukkit.getConsoleSender(), "&7[KMCAntyLogout] &cBLAD!!!");
            ChatUtil.sendMessage(Bukkit.getConsoleSender(), "&7[KMCAntyLogout] &cBLAD!!!");
            ChatUtil.sendMessage(Bukkit.getConsoleSender(), "&7[KMCAntyLogout] &cBLAD!!!");
            ChatUtil.sendMessage(Bukkit.getConsoleSender(), "&7[KMCAntyLogout] &cBLAD!!!");
            ChatUtil.sendMessage(Bukkit.getConsoleSender(), "&7[KMCAntyLogout] &cBLAD!!!");
            ChatUtil.sendMessage(Bukkit.getConsoleSender(), "&7[KMCAntyLogout] &cBLAD!!!");
            ChatUtil.sendMessage(Bukkit.getConsoleSender(), "&7[KMCAntyLogout] &cBLAD!!!");
            plugin.getPluginLoader().disablePlugin(plugin);
        }

    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onHit(EntityDamageByEntityEvent event){
        if(!event.isCancelled()){
            Player victim = null, atacker = null;
            if(event.getEntity() instanceof Player){
                victim = (Player) event.getEntity();
            }
            if(event.getDamager() instanceof Player){
                atacker = (Player) event.getDamager();
            }
            if(event.getDamager() instanceof Player && event.getEntity() instanceof Player){
                if(spawn.contains(atacker.getLocation().getBlockX(), atacker.getLocation().getBlockY(), atacker.getLocation().getBlockZ())){
                    event.setCancelled(true);
                }else {
                    antyLogoutClass.addPlayer(victim);
                    antyLogoutClass.addPlayer(atacker);
                }


            }else if(victim instanceof Player && event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE){
                if(event.getDamager().getType() == EntityType.ARROW){
                    Arrow arrow = (Arrow) event.getDamager();
                    if(arrow.getShooter() instanceof Player){
                        Player shooter = (Player) arrow.getShooter();
                        antyLogoutClass.addPlayer(victim);
                        antyLogoutClass.addPlayer(shooter);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onKick(PlayerKickEvent event){
        kicked.add(event.getPlayer());
        new BukkitRunnable() {
            @Override
            public void run() {
                kicked.remove(event.getPlayer());
            }
        }.runTaskLater(plugin, 60);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        if(antyLogoutClass.isOnAntyLogout(player) == true){
            if(!kicked.contains(player)){
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kill " + player.getName());
                player.getWorld().strikeLightningEffect(player.getLocation());
                Bukkit.broadcastMessage(ChatUtil.colored("&c" + player.getName() + " &cwylogowal sie podczas walki!"));
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player player = event.getEntity();
        if(antyLogoutClass.isOnAntyLogout(player) == true){
            if(event.getEntity().getKiller() == null){
                event.setDeathMessage(ChatUtil.colored("&c" + player.getName() + " &cwylogowal sie podczas walki!"));
            }
            antyLogoutClass.endAntyLogout(player);
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event){
        Player player = event.getPlayer();
        if(antyLogoutClass.isOnAntyLogout(player)){
            event.setCancelled(true);
            ChatUtil.sendMessage(player, "&cNie mozesz uzywac komend podczas pvp!");
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        Player p = event.getPlayer();
        Location to = event.getTo();
        if(spawn.contains(to.getBlockX(), to.getBlockY(), to.getBlockZ())){
            if(antyLogoutClass.isOnAntyLogout(p)){
                Location l = new Location(p.getWorld(), 0, 84,0).subtract(p.getLocation());
                double distance = p.getLocation().distance(p.getWorld().getSpawnLocation());
                Vector v = l.toVector().add(new Vector(0, 5, 0)).multiply(1.25D / distance);
                p.setVelocity(v.multiply(-1.5D));
                p.getLocation().getWorld().playEffect(p.getLocation(), Effect.MOBSPAWNER_FLAMES, 3);
                p.getLocation().getWorld().playEffect(p.getLocation(), Effect.BLAZE_SHOOT, 3);
                ChatUtil.sendMessage(p, "&cNie mozesz wejsc na spawn podczas pvp!");
            }
        }
    }





}
