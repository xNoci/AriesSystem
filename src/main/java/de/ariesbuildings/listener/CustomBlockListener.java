package de.ariesbuildings.listener;

import com.cryptomorin.xseries.XMaterial;
import de.ariesbuildings.utils.CustomBlock;
import me.noci.quickutilities.utils.DirectionUtils;
import me.noci.quickutilities.utils.QuickItemStack;
import org.bukkit.Art;
import org.bukkit.block.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Lightable;
import org.bukkit.block.data.Rotatable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;

public class CustomBlockListener implements Listener {

    @EventHandler
    private void handleBlockPlace(BlockPlaceEvent event) {
        if (event.isCancelled()) return;
        CustomBlock.matchCustomBlock(new QuickItemStack(event.getItemInHand()))
                .ifPresent(customBlock -> {
                    placeBlock(customBlock, event.getPlayer(), event.getBlockPlaced());
                });
    }

    @EventHandler
    private void handlePlaceHanging(HangingPlaceEvent event) {
        if (event.isCancelled()) return;
        if (event.getItemStack() == null) return;
        CustomBlock.matchCustomBlock(new QuickItemStack(event.getItemStack()))
                .ifPresent(customBlock -> {
                    placeHanging(customBlock, event.getEntity());
                });
    }

    private void placeBlock(CustomBlock customBlock, Player player, Block block) {
        boolean allowUpDown = false;

        switch (customBlock) {
            case LIT_FURNACE -> {
                block.setType(XMaterial.FURNACE.parseMaterial());
                Furnace furnace = (Furnace) block.getState();
                furnace.setBurnTime(Short.MAX_VALUE);
                furnace.update();
            }
            case LIT_BLAST_FURNACE -> {
                block.setType(XMaterial.BLAST_FURNACE.parseMaterial());
                BlastFurnace furnace = (BlastFurnace) block.getState();
                furnace.setBurnTime(Short.MAX_VALUE);
                furnace.update();
            }
            case LIT_SMOKER -> {
                block.setType(XMaterial.SMOKER.parseMaterial());
                Smoker furnace = (Smoker) block.getState();
                furnace.setBurnTime(Short.MAX_VALUE);
                furnace.update();
            }
            case PISTON_HEAD -> {
                block.setType(XMaterial.PISTON_HEAD.parseMaterial());
                allowUpDown = true;
            }
            case POWERED_REDSTONE_LAMP -> {
                block.setType(XMaterial.REDSTONE_LAMP.parseMaterial());
                Lightable lightable = (Lightable) block.getBlockData();
                lightable.setLit(true);
                block.setBlockData(lightable);
            }
            case NETHER_PORTAL -> block.setType(XMaterial.NETHER_PORTAL.parseMaterial());
            case END_PORTAL -> block.setType(XMaterial.END_PORTAL.parseMaterial());
            case END_PORTAL_GATEWAY -> block.setType(XMaterial.END_GATEWAY.parseMaterial());
            case COMMAND_BLOCK -> {
                block.setType(XMaterial.COMMAND_BLOCK.parseMaterial());
                allowUpDown = true;
            }
            case BARRIER -> block.setType(XMaterial.BARRIER.parseMaterial());
            case LIGHT -> block.setType(XMaterial.LIGHT.parseMaterial());
            case STRUCTURE_VOID -> block.setType(XMaterial.STRUCTURE_VOID.parseMaterial());
        }

        rotate(player, block, allowUpDown);
    }

    private void placeHanging(CustomBlock customBlock, Entity entity) {
        switch (customBlock) {
            case PAINTING_ELEMENT_EARTH -> {
                Painting painting = (Painting) entity;
                painting.setArt(Art.EARTH);
            }
            case PAINTING_ELEMENT_WIND -> {
                Painting painting = (Painting) entity;
                painting.setArt(Art.WIND);
            }
            case PAINTING_ELEMENT_WATER -> {
                Painting painting = (Painting) entity;
                painting.setArt(Art.WATER);
            }
            case PAINTING_ELEMENT_FIRE -> {
                Painting painting = (Painting) entity;
                painting.setArt(Art.FIRE);
            }
            case INVISIBLE_ITEM_FRAME -> {
                ItemFrame itemFrame = (ItemFrame) entity;
                itemFrame.setVisible(false);
            }
        }
    }

    private void rotate(Player player, Block block, boolean allowUpDown) {
        BlockData blockData = block.getBlockData();
        BlockFace facing = DirectionUtils.getBlockDirection(player, allowUpDown);
        if (blockData instanceof Directional directional) {
            directional.setFacing(facing);
        }

        if (blockData instanceof Rotatable rotatable) {
            rotatable.setRotation(facing);
        }

        block.setBlockData(blockData);
    }

}
