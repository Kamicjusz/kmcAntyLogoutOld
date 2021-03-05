package me.kamicjusz.antylogout;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import me.kamicjusz.antylogout.AntyLogout.AntyLogout;
import me.kamicjusz.antylogout.AntyLogout.AntyLogoutTriggers;
import me.kamicjusz.antylogout.Utils.ChatUtil;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private AntyLogout antyLogoutClass;
    private AntyLogoutTriggers antyLogoutTriggersClass;

    WorldGuard worldGuard;

    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage(ChatUtil.colored(" &f[KMCAntyLogout]"));
        getServer().getConsoleSender().sendMessage(ChatUtil.colored(" &f[KMCAntyLogout] Plugin zostal &awlaczony"));
        getServer().getConsoleSender().sendMessage(ChatUtil.colored(" &f[KMCAntyLogout] Plugin autorstwa: &bKamicjusz"));
        getServer().getConsoleSender().sendMessage(ChatUtil.colored(" &f[KMCAntyLogout]"));
        worldGuard = WorldGuard.getInstance();

        antyLogoutClass = new AntyLogout(this);
        antyLogoutTriggersClass = new AntyLogoutTriggers(this);
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatUtil.colored(" &f[KMCAntyLogout]"));
        getServer().getConsoleSender().sendMessage(ChatUtil.colored(" &f[KMCAntyLogout] Plugin zostal &cwylaczony"));
        getServer().getConsoleSender().sendMessage(ChatUtil.colored(" &f[KMCAntyLogout] Plugin autorstwa: &bKamicjusz"));
        getServer().getConsoleSender().sendMessage(ChatUtil.colored(" &f[KMCAntyLogout]"));
    }

    public AntyLogout getAntyLogoutClass() {
        return antyLogoutClass;
    }

    public AntyLogoutTriggers getAntyLogoutTriggersClass() {
        return antyLogoutTriggersClass;
    }

    public WorldGuard getWorldGuard() {
        return worldGuard;
    }
}
