package lb.lbeasylogin.listener;

import lb.lbeasylogin.PlayerManager;
import lb.lbeasylogin.Reference;
import lb.lbeasylogin.Util;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * 各种限制操作
 *
 * @date 2020/2/28 22:59
 */
public class PlayerLimitListener implements Listener {

    /**
     * 不让未登录的van家移动
     *
     * @param e event event
     */
    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent e) {
        if (!PlayerManager.isLogin(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    /**
     * 不让未登录的van家对话
     *
     * @param e event
     */
    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent e) {
        if (e.getMessage().substring(0, 1).equals("/")) // 输入命令也算 所以要检测一下
            return;
        if (!PlayerManager.isLogin(e.getPlayer())) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(Util.getConfigSetting("error.notAllowSpeak"));
        }
    }


    /**
     * 不让未登录的van家输入其他奇♂怪的命令
     *
     * @param e event
     */
    @EventHandler
    public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent e) {
        if (PlayerManager.isLogin(e.getPlayer()))
            return;

        String cmdName = e.getMessage().split(" ")[0].toLowerCase();
        // 遍历数组 如果是其中的String就放过233
        for (String str : Reference.commandList) {
            if (cmdName.equals(str)) { // 转换小写处理
                e.setCancelled(false);
                return;
            }
        }
        e.setCancelled(true);
        e.getPlayer().sendMessage(Util.getConfigSetting("error.notAllowUseCommand"));
    }

    /**
     * 不让未登录的van家跟别的东西交互，约等于屏蔽左右键
     *
     * @param e event
     */
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        e.setCancelled(PlayerManager.needCancelled(e.getPlayer()));
    }

    /**
     * 不让未登录的van家打开背包
     *
     * @param e event
     * @deprecated 没效果
     */
    @Deprecated
    @EventHandler
    public void onPlayerInventory(InventoryOpenEvent e) {
//        System.out.println("inv debug: " + PlayerManager.needCancelled((Player) e.getPlayer()));
        e.setCancelled(PlayerManager.needCancelled((Player) e.getPlayer()));
    }

    /**
     * van家加入时给他(她, 它)加到未登入的ArrayList里
     *
     * @param e event
     */
    @EventHandler
    private void onPlayerJoinEvent(PlayerJoinEvent e) {
        PlayerManager.setLogin(e.getPlayer(), false);

        // 没登录想动是吧 给你加个buff
        e.getPlayer().addPotionEffect(
                new PotionEffect(PotionEffectType.SLOW, Short.MAX_VALUE, 255, false, false), true);
        e.getPlayer().addPotionEffect(
                new PotionEffect(PotionEffectType.BLINDNESS, Short.MAX_VALUE, 255, false, false), true);
    }

    /**
     * van家离开服务器时给他(她, 它)从ArrayList里删除
     *
     * @param e event
     */
    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent e) {
        PlayerManager.setLogin(e.getPlayer(), true);
    }
}
