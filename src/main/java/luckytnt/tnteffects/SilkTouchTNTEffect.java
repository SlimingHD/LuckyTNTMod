package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class SilkTouchTNTEffect extends PrimedTNTEffect {

	@SuppressWarnings("deprecation")
	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		ImprovedExplosion dummy = ImprovedExplosion.dummyExplosion(ent.getLevel());
		ExplosionHelper.customSphericalExplosion(ent.getLevel(), ent.getPos(), 15, (level, center, pos, state) -> {
			if (!state.isAir() && Math.abs(ent.y() - pos.getY()) <= 5d && Math.max(state.getBlock().getExplosionResistance(), state.getFluidState().getExplosionResistance()) < 100f) {
				level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
				state.getBlock().wasExploded(level, pos, dummy);
				
				ItemEntity item = new ItemEntity(level, pos.getX() + 0.5d, pos.getY(), pos.getZ() + 0.5d, new ItemStack(Item.byBlock(state.getBlock())));
				level.addFreshEntity(item);
			}
		});
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.SILK_TOUCH_TNT.get();
	}
}
