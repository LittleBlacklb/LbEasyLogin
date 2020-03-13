package lb.lbeasylogin.command;

import lb.lbeasylogin.PlayerManager;
import lb.lbeasylogin.Util;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

/**
 * 登录/注册 操作
 *
 * @date null
 */
public class PlayerLoginCommand implements CommandExecutor {

    public boolean onCommand(CommandSender commandSender, Command cmd, String label, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "u can't use this command, because u are not a player!");
            return true;
        }
        Player p = (Player) commandSender;

        String cmdName = cmd.getName().toLowerCase();
        if (cmdName.equals("login") | label.equals("l")) {
            return login(p, args);
        } else if (cmdName.equals("register") | label.equals("reg")) {
            return register(p, args);
        }
        return false;
    }

    private boolean login(Player p, String[] args) {
        if (args.length != 1) {
            p.sendMessage(Util.getConfigSetting("error.outOfRange"));
            return false;
        }
        if (!PlayerManager.isRegister(p)) {
            p.sendMessage(Util.getConfigSetting("error.notRegister"));
            return true;
        }
        if (PlayerManager.isLogin(p)) {
            p.sendMessage(Util.getConfigSetting("error.hasLogin"));
            return true;
        }
        if (PlayerManager.isCorrectPasswd(p, args[0])) {
            PlayerManager.setLogin(p, true);
            p.getPlayer().removePotionEffect(PotionEffectType.SLOW);
            p.getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
            p.sendMessage(Util.getConfigSetting("succ.login"));
        } else {
            p.sendMessage(Util.getConfigSetting("error.login"));
        }
        return true;
    }

    private boolean register(Player p, String[] args) {
        if (args.length != 2) {
            p.sendMessage(Util.getConfigSetting("error.outOfRange"));
            return false;
        }
        if (PlayerManager.isRegister(p)) {
            p.sendMessage(Util.getConfigSetting("error.hasRegister"));
            return true;
        }
        if (!args[0].equals(args[1])) {
            p.sendMessage(Util.getConfigSetting("error.passwordNotSame"));
            return true;
        }
        // 正则表达式验证密码是否合法
        String patten = Util.getConfigSetting("other.pwdPatten");
        if (!patten.equals("")) { // 如果表达式不为空
            if (!args[0].matches(patten)) { // 如果不合法
                p.sendMessage(Util.getConfigSetting("error.pwdPattenWrong"));
                return true;
            }
        }
        PlayerManager.setRegister(p, args[0]);
        p.getPlayer().removePotionEffect(PotionEffectType.SLOW);
        p.getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
        p.getPlayer().sendMessage(Util.getConfigSetting("succ.register"));
        PlayerManager.setLogin(p, true);
        return true;
    }
}
