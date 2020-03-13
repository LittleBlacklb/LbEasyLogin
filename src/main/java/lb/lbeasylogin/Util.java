package lb.lbeasylogin;

import org.bukkit.ChatColor;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;

import static lb.lbeasylogin.Reference.PASSWDCONFIG;

/**
 * 工具类
 */
public class Util {

    // https://www.cnblogs.com/zhuyeshen/p/11424713.html
    public static String stringToMD5(String plainText) {
        byte[] secretBytes;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("System cannot calculate this MD5!");
        }
        StringBuilder md5code = new StringBuilder(new BigInteger(1, secretBytes).toString(16));
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code.insert(0, "0"); // 切记在loop里不能用拼接字符串，因为会产生n个对象，占用内存!!!
        }
        return md5code.toString();
    }

    public static String getConfigSetting(String path) {
        // 信息检查
        String info;
        if ((info = Reference.INSTANCE.getConfig().getString(path)) == null) {
            info = "&l&eOops! &cCan't find the info! &cHave you change the jar file??? " + path;
        }
        return ChatColor.translateAlternateColorCodes('&', info);
    }

    public static void savePwdConfig() {
        try {
            PASSWDCONFIG.save(Reference.PASSWDCONFIGFILE);
        } catch (IOException e) {
            Reference.LOGGER.log(Level.SEVERE, "Could not save config to " + Reference.PASSWDCONFIGFILE.getName(), e);
        }
    }

}
