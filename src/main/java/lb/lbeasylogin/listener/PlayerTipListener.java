package lb.lbeasylogin.listener;

import lb.lbeasylogin.PlayerManager;
import lb.lbeasylogin.Util;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * van家入服的友♂好面对
 *
 * @date null
 */
public class PlayerTipListener implements Listener {

    /**
     * 友♂好的面对刚进入服务器的va♂n家
     *
     * @param e event
     */
    @EventHandler // 注入灵魂 狗头.jpg
    public void onPlayerJoinEvent(PlayerJoinEvent e) {
        e.getPlayer().sendMessage(PlayerManager.isRegister(e.getPlayer()) ?
                Util.getConfigSetting("info.login") :
                Util.getConfigSetting("info.register"));
    }
}
