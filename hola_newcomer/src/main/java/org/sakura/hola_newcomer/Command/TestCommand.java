package org.sakura.hola_newcomer.Command;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class TestCommand implements CommandExecutor {

    private final Plugin plugin;

    public TestCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

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

            player.getInventory().setItemInMainHand(giftBox);
            player.sendMessage("你已經收到新手禮包!");
        }
        return true;
    }
}
