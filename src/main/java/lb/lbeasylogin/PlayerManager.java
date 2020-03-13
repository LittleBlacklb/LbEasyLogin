package lb.lbeasylogin;

import org.bukkit.entity.Player;

import java.util.ArrayList;

import static lb.lbeasylogin.Reference.PASSWDCONFIG;

/**
 * van家登录管理器
 *
 * @date null
 */
public class PlayerManager {
    private static ArrayList<Player> notLoginPlayerName = new ArrayList<Player>();

    public static ArrayList<Player> getNotLoginPlayerName() {
        return notLoginPlayerName;
    }

    public static void setRegister(Player p, String pwd) {
        PASSWDCONFIG.set(p.getName().toLowerCase() + ".password", Util.stringToMD5(pwd)); // 典型bug fixed :)
        Util.savePwdConfig();
        Reference.LOGGER.info(p.getName().toLowerCase() + " has already registered successful!");
    }

    public static boolean isRegister(Player p) {
        return PASSWDCONFIG.contains(p.getName().toLowerCase());
    }

    public static boolean isCorrectPasswd(Player p, String pwd) {
        return Util.stringToMD5(pwd).equals(PASSWDCONFIG.get(p.getName().toLowerCase() + ".password"));
    }

    public static boolean isLogin(Player p) {
        return !notLoginPlayerName.contains(p);
    }

    public static void setLogin(Player p, boolean b) {
        if (b) {
            notLoginPlayerName.remove(p);
        } else {
            notLoginPlayerName.add(p);
        }
    }

    public static boolean needCancelled(Player p) {
        return !isLogin(p);
    }
}
