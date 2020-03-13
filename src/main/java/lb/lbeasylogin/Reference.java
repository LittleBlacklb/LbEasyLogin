package lb.lbeasylogin;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

/**
 * 一些常用常量
 *
 * @date null
 */
public class Reference {
    public final static JavaPlugin INSTANCE = LbEasyLogin.instance;
    public final static Logger LOGGER = INSTANCE.getLogger();
    public final static File PASSWDCONFIGFILE = new File(INSTANCE.getDataFolder(), "hasRegistered.yml");
    public final static FileConfiguration PASSWDCONFIG = YamlConfiguration.loadConfiguration(
            PASSWDCONFIGFILE);
    public final static String[] commandList = {"/register", "/reg", "/login", "/l"};
}
