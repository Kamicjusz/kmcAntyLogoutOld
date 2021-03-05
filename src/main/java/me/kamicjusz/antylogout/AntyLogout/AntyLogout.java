package me.kamicjusz.antylogout.AntyLogout;

import me.kamicjusz.antylogout.Main;
import me.kamicjusz.antylogout.Utils.ChatUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.IOException;
import java.util.*;

public class AntyLogout  implements Listener {

    public Map<UUID, Integer> antyLogoutList;
    public Map<UUID, Integer> runnable;

    final private Main plugin;
    BukkitTask bukkitTask;
    int schedule;

    public AntyLogout(Main plugin){
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        antyLogoutList = new HashMap<>();
        runnable = new HashMap<>();
    }
    public void addPlayer(Player player){
        schedule = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            int seconds = 15;

            @Override
            public void run() {
                if(seconds > 0){
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatUtil.colored("&cANTY-LOGOUT &a" + seconds)));
                    seconds--;
                }else {
                    if(runnable.containsKey(player.getUniqueId())){
                        int id = runnable.get(player.getUniqueId());
                        Bukkit.getScheduler().cancelTask(id);
                        runnable.remove(player.getUniqueId());
                        ChatUtil.sendMessage(player, "&aWalka zakonczona! Mozesz sie bezpiecznie wylogowac!");
                    }
                }

            }
        }, 0, 20);

        if(runnable.containsKey(player.getUniqueId())){
            int id = runnable.get(player.getUniqueId());
            Bukkit.getScheduler().cancelTask(id);
            runnable.put(player.getUniqueId(), schedule);
        }else {
            runnable.put(player.getUniqueId(), schedule);
            ChatUtil.sendMessage(player, "&cJestes w trakcie walki! Nie mozesz sie teraz wylogowac z gry!");
        }


    }

    public Map<UUID, Integer> getAntyLogoutList() {
        return antyLogoutList;
    }

    public boolean isOnAntyLogout(Player player){
        if(runnable.containsKey(player.getUniqueId())){
            return true;
        }else {
            return false;
        }
    }

    public void endAntyLogout(Player player){
        int id = runnable.get(player.getUniqueId());
        Bukkit.getScheduler().cancelTask(id);
        runnable.remove(player.getUniqueId());
    }
}
