package net.hudica.tutorialmod.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class DiggerItem extends Item {

    public DiggerItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        Player player = pContext.getPlayer();
        if (!level.isClientSide()) {
            BlockPos positionClicked = pContext.getClickedPos();
            for (int y = positionClicked.getY(); y > -64; y--) {
                BlockPos currentPos = new BlockPos(positionClicked.getX(), y, positionClicked.getZ());
                BlockState state = level.getBlockState(currentPos);
                if (!state.is(Blocks.BEDROCK)) {
                    level.setBlock(currentPos, Blocks.AIR.defaultBlockState(), 3); // Set to air with update flags
                }
            }
        }
        pContext.getItemInHand().hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(p.getUsedItemHand()));
        return InteractionResult.SUCCESS;
    }
}
