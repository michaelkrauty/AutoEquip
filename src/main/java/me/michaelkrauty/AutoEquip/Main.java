package me.michaelkrauty.AutoEquip;

import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created on 7/4/2015.
 *
 * @author michaelkrauty
 */
public class Main extends JavaPlugin implements Listener, CommandExecutor {

    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }


    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {

        // Variables
        final Material placedBlock = event.getBlockPlaced().getType();
        final Player player = event.getPlayer();

        // Schedule task to run 1 tick after bock place
        getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            public void run() {

                // Variables
                Inventory inv = player.getInventory();
                Material inHand = player.getItemInHand().getType();

                // Check whether the block placed was the last one in the stack
                if (inHand == Material.AIR) {

                    // Check whether the inventory contains more of the block
                    if (inv.contains(placedBlock)) {

                        // Loop through the inventory, find all blocks of the same type
                        for (ItemStack itemStack : inv.getContents())

                            // If the loop item matches the placed block, move it into the player's hand
                            if (itemStack != null && itemStack.getType() == placedBlock) {
                                player.getInventory().remove(itemStack);
                                player.setItemInHand(itemStack);
                                break;
                            }
                    }
                }
            }
        }, 1);
    }

    @EventHandler
    public void onPlayerItemBreak(PlayerItemBreakEvent event) {

        // Variables
        final Material brokenTool = event.getBrokenItem().getType();
        final Player player = event.getPlayer();

        // Schedule task to run 1 tick after tool breakage
        getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            public void run() {

                // Variables
                Inventory inv = player.getInventory();
                Material inHand = player.getItemInHand().getType();

                // Check whether the player has anything in their hand
                if (inHand == Material.AIR) {

                    int index = -1;

                    // Loop through the inventory, find the first occurrence of the same type of item
                    for (ItemStack item : inv.getContents()) {
                        if (item == null)
                            continue;
                        if (isPickaxe(brokenTool))
                            if (isPickaxe(item.getType())) {
                                index = inv.first(item.getType());
                                break;
                            }
                        if (isShovel(brokenTool))
                            if (isShovel(item.getType())) {
                                index = inv.first(item.getType());
                                break;
                            }
                        if (isAxe(brokenTool))
                            if (isAxe(item.getType())) {
                                index = inv.first(item.getType());
                                break;
                            }
                        if (isHoe(brokenTool))
                            if (isHoe(item.getType())) {
                                index = inv.first(item.getType());
                                break;
                            }
                        if (isSword(brokenTool))
                            if (isSword(item.getType())) {
                                index = inv.first(item.getType());
                                break;
                            }
                    }

                    // Check whether there is another of the same type of item in the inventory
                    if (index != -1) {

                        // Move the first tool found of the same type into the player's hand
                        player.setItemInHand(inv.getItem(index));
                        player.getInventory().setItem(index, new ItemStack(Material.AIR));
                    }
                }
            }
        }, 1);
    }

    private boolean isPickaxe(Material item) {
        return item == Material.WOOD_PICKAXE
                || item == Material.STONE_PICKAXE
                || item == Material.IRON_PICKAXE
                || item == Material.GOLD_PICKAXE
                || item == Material.DIAMOND_PICKAXE;
    }

    private boolean isShovel(Material item) {
        return item == Material.WOOD_SPADE
                || item == Material.STONE_SPADE
                || item == Material.IRON_SPADE
                || item == Material.GOLD_SPADE
                || item == Material.DIAMOND_SPADE;
    }

    private boolean isAxe(Material item) {
        return item == Material.WOOD_AXE
                || item == Material.STONE_AXE
                || item == Material.IRON_AXE
                || item == Material.GOLD_AXE
                || item == Material.DIAMOND_AXE;
    }

    private boolean isHoe(Material item) {
        return item == Material.WOOD_HOE
                || item == Material.STONE_HOE
                || item == Material.IRON_HOE
                || item == Material.GOLD_HOE
                || item == Material.DIAMOND_HOE;
    }

    private boolean isSword(Material item) {
        return item == Material.WOOD_SWORD
                || item == Material.STONE_SWORD
                || item == Material.IRON_SWORD
                || item == Material.GOLD_SWORD
                || item == Material.DIAMOND_SWORD;
    }
}
