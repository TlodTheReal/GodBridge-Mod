package tlod;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class GodBot {
    Minecraft mc = Minecraft.getMinecraft();

    public int direction;

    public int blockCount;

    public void onEnable() {
        if (this.mc.inGameHasFocus) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), true);
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindRight.getKeyCode(), true);
            blockCount = 0;
            direction = MathHelper.floor_double((double) ((mc.thePlayer.rotationYaw * 4F) / 360F) + 0.5D) & 3;
            MinecraftForge.EVENT_BUS.register(this);
        } else {
            Settings.setToggled(false);
        }
    }

    public void onDisable() {
        blockCount = 0;
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), false);
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindRight.getKeyCode(), false);
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), false);
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        BlockPos playerBlock = new BlockPos(mc.thePlayer.posX, mc.thePlayer.getEntityBoundingBox().minY, mc.thePlayer.posZ);

        if (direction == 0) { //south
            mc.thePlayer.rotationYaw = -135F;
            mc.thePlayer.rotationPitch = 75.8F;

            if (mc.thePlayer.moveForward < 0) {
                placeBlock(playerBlock.add(0, -1, 0), EnumFacing.SOUTH);
                blockCount++;
            }
        } else if (direction == 1) { //west
            mc.thePlayer.rotationYaw = -45F;
            mc.thePlayer.rotationPitch = 75.8F;

            if (mc.thePlayer.moveForward < 0) {
                placeBlock(playerBlock.add(0, -1, 0), EnumFacing.WEST);
                blockCount++;
            }
        } else if (direction == 2) { //north
            mc.thePlayer.rotationYaw = 45F;
            mc.thePlayer.rotationPitch = 75.8F;

            if (mc.thePlayer.moveForward < 0) {
                placeBlock(playerBlock.add(0, -1, 0), EnumFacing.NORTH);
                blockCount++;
            }
        } else if (direction == 3) { //east
            mc.thePlayer.rotationYaw = 135F;
            mc.thePlayer.rotationPitch = 75.7F;

            if (mc.thePlayer.moveForward < 0) {
                placeBlock(playerBlock.add(0, -1, 0), EnumFacing.EAST);
                blockCount++;
            }
        }

        if (blockCount == 66) {
            blockCount = 0;
            if (mc.thePlayer.onGround) {
                mc.thePlayer.jump();
            }
        }
    }

    public void placeBlock(BlockPos pos, EnumFacing face) {
        if (!doesSlotHaveBlocks(mc.thePlayer.inventory.currentItem))
            mc.thePlayer.inventory.currentItem = getFirstHotBarSlotWithBlocks();

        if (!isBlockAir(getBlock(pos)))
            return;

        if (face == EnumFacing.UP) {
            pos = pos.add(0, -1, 0);
        } else if (face == EnumFacing.NORTH) {
            pos = pos.add(0, 0, 1);
        } else if (face == EnumFacing.EAST) {
            pos = pos.add(-1, 0, 0);
        } else if (face == EnumFacing.SOUTH) {
            pos = pos.add(0, 0, -1);
        } else if (face == EnumFacing.WEST) {
            pos = pos.add(1, 0, 0);
        }

        if (mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock) {
            mc.thePlayer.swingItem();
            mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), pos, face, new Vec3(0.5D, 0.5D, 0.5D));
        }
    }

    public boolean isBlockAir(Block block) {
        if (block.getMaterial().isReplaceable()) {
            if (block instanceof BlockSnow && block.getBlockBoundsMaxY() > 0.125D)
                return false;
            return true;
        }
        return false;
    }

    public static Block getBlock(BlockPos pos) {
        return (Minecraft.getMinecraft()).theWorld.getBlockState(pos).getBlock();
    }

    public int getFirstHotBarSlotWithBlocks() {
        for (int i = 0; i < 9; i++) {
            if (mc.thePlayer.inventory.getStackInSlot(i) != null && mc.thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemBlock)
                return i;
        }
        return 0;
    }

    public boolean doesSlotHaveBlocks(int slotToCheck) {
        if (mc.thePlayer.inventory.getStackInSlot(slotToCheck) != null && mc.thePlayer.inventory.getStackInSlot(slotToCheck).getItem() instanceof ItemBlock &&
                (mc.thePlayer.inventory.getStackInSlot(slotToCheck)).stackSize > 0)
            return true;
        return false;
    }
}

