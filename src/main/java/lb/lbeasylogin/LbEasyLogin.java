package lb.lbeasylogin;

import lb.lbeasylogin.command.PlayerLoginCommand;
import lb.lbeasylogin.listener.PlayerLimitListener;
import lb.lbeasylogin.listener.PlayerTipListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main
 * 不要出bug，秋梨膏
 *
 * @author littleblackLB
 * @date 2020/2/28 22:40
 */
public class LbEasyLogin extends JavaPlugin {
    public static JavaPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        // 配置文件init
        this.saveDefaultConfig();
        this.saveResource("hasRegistered.yml", false);

        // 注册Listener
        Bukkit.getPluginManager().registerEvents(new PlayerLimitListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerTipListener(), this);

        // 注册CommandExecutor
        PlayerLoginCommand plc = new PlayerLoginCommand();
        Bukkit.getPluginCommand("login").setExecutor(plc);
        Bukkit.getPluginCommand("register").setExecutor(plc);

        Bukkit.getConsoleSender().sendMessage("[LbEasyLogin] " + ChatColor.WHITE + " LbEasyLogin已加载!");
        Bukkit.getConsoleSender().sendMessage("[LbEasyLogin] " + ChatColor.WHITE + " 2020/2/28 23:16 ver1.0 " + ChatColor.YELLOW + "Bilibili乾杯!");
    }

    @Override
    public void onDisable() {
        for (Player p : PlayerManager.getNotLoginPlayerName()) // if plugin is reloaded, kick not login players
            p.kickPlayer("Sorry! The server have some problem, so u can rejoin the server.");
        Util.savePwdConfig();
        Bukkit.getConsoleSender().sendMessage("[LbEasyLogin] " + ChatColor.WHITE + " LbEasyLogin已卸载!");
    }
}