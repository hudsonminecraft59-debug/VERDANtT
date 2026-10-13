package com.verdant.client;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public final class VerdantEsp {
    private static int frame = 0;

    private VerdantEsp() {}

    public static void render(WorldRenderContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null || client.player == null) return;

        World world = client.world;
        BlockPos center = client.player.getBlockPos();
        int r = MathHelper.clamp(VerdantConfig.range, 8, 128);
        Vec3d cam = context.camera().getPos();
        var matrices = context.matrixStack();
        var consumers = context.consumers();
        VertexConsumer lines = (consumers != null) ? consumers.getBuffer(RenderLayer.getLines()) : null;
        boolean canDraw = (matrices != null && lines != null);

        int minY = Math.max(world.getBottomY(), center.getY() - r);
        int maxY = Math.min(world.getTopYInclusive(), center.getY() + r);
        BlockPos.Mutable pos = new BlockPos.Mutable();
        int found = 0;

        for (int x = center.getX() - r; x <= center.getX() + r; x++) {
            for (int z = center.getZ() - r; z <= center.getZ() + r; z++) {
                if ((x - center.getX()) * (x - center.getX()) + (z - center.getZ()) * (z - center.getZ()) > r * r) continue;
                for (int y = minY; y <= maxY; y++) {
                    pos.set(x, y, z);
                    BlockState state = world.getBlockState(pos);
                    Block block = state.getBlock();
                    boolean storage = block == Blocks.CHEST || block == Blocks.TRAPPED_CHEST || block == Blocks.ENDER_CHEST || block == Blocks.HOPPER || block instanceof ShulkerBoxBlock;
                    boolean spawner = block == Blocks.SPAWNER;
                    if (!(VerdantConfig.storageEsp && storage) && !(VerdantConfig.spawnerFinder && spawner)) continue;
                    found++;
                    if (!canDraw) continue;
                    int rgb = storage ? VerdantConfig.storageColor : VerdantConfig.spawnerColor;
                    float red = ((rgb >> 16) & 255) / 255f, green = ((rgb >> 8) & 255) / 255f, blue = (rgb & 255) / 255f;
                    Box box = new Box(x - cam.x, y - cam.y, z - cam.z, x + 1 - cam.x, y + 1 - cam.y, z + 1 - cam.z);
                    WorldRenderer.drawBox(matrices, lines, box, red, green, blue, 0.98f);
                }
            }
        }

        // TEST MESSAGE: shows above your hotbar about every 2 seconds
        frame++;
        if (frame % 120 == 0) {
            client.player.sendMessage(Text.literal("Verdant test: matrices=" + (matrices != null)
                    + " consumers=" + (consumers != null)
                    + " found=" + found
                    + " storageEsp=" + VerdantConfig.storageEsp), true);
        }
    }
}
