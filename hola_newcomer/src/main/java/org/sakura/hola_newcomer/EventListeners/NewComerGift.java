package org.sakura.hola_newcomer.EventListeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class NewComerGift implements Listener {

    private Plugin plugin;

    public NewComerGift(Plugin plugin) {
        this.plugin = plugin;
    }

    private ItemStack getNewPlayerGift() {
        ItemStack giftBox = new ItemStack(Material.SHULKER_BOX, 1);
        BlockStateMeta meta = (BlockStateMeta) giftBox.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("新手禮包");
            List<String> lore = new ArrayList<>();
            lore.add("裡面有哪些東西呢(。ヘ°)?");
            meta.setLore(lore);
            meta.setUnbreakable(true);

            NamespacedKey key = new NamespacedKey(plugin, "newcomer_gift");
            meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "newcomer_gift");

            org.bukkit.block.ShulkerBox shulkerBox = (org.bukkit.block.ShulkerBox) meta.getBlockState();
            Inventory inventory = shulkerBox.getInventory();
            inventory.addItem(new ItemStack(Material.STONE_AXE, 1));
            inventory.addItem(new ItemStack(Material.STONE_SHOVEL, 1));
            inventory.addItem(new ItemStack(Material.COPPER_INGOT, 12));
            inventory.addItem(new ItemStack(Material.COOKED_BEEF, 32));
            inventory.addItem(new ItemStack(Material.BAKED_POTATO, 16));
            inventory.addItem(new ItemStack(Material.COOKED_PORKCHOP, 32));

            meta.setBlockState(shulkerBox);
            giftBox.setItemMeta(meta);
        }

        return giftBox;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPlayedBefore()) {
            // 玩家第一次加入
            player.getInventory().setItemInMainHand(getNewPlayerGift());
            player.sendMessage("歡迎來到櫻花鄉伺服器!!");
            player.sendMessage("這是你的新手禮包~(ﾉ´▽｀)ﾉ♪");
        } else {
            // 不是第一次加入
            player.sendMessage("歡迎回來!(*´∀`)~♥");
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory().getType() == InventoryType.SHULKER_BOX) {
            Inventory inventory = event.getInventory();
            ItemStack item = event.getPlayer().getInventory().getItemInMainHand();

            if (item.getType() == Material.SHULKER_BOX && item.hasItemMeta()) {
                BlockStateMeta meta = (BlockStateMeta) item.getItemMeta();
                NamespacedKey key = new NamespacedKey(plugin, "newcomer_gift");

                assert meta != null;
                if (meta.getPersistentDataContainer().has(key, PersistentDataType.STRING)) {
                    Player player = (Player) event.getPlayer();
                    for (ItemStack content : inventory.getContents()) {
                        if (content != null) {
                            player.getInventory().addItem(content);
                        }
                    }
                    inventory.clear();
                    player.sendMessage("新手禮包已關閉，剩餘物品會放入你的背包。");
                }
            }
        }
    }
}