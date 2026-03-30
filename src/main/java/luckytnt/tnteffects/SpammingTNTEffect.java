package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class SpammingTNTEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		RandomSource random = level.getRandom();
		
		for (int count = 0; count < 400; count++) {
			ItemEntity dirt = new ItemEntity(level, entity.x(), entity.y(), entity.z(), new ItemStack(Items.DIRT));
			dirt.setDeltaMovement(random.nextDouble() * 6d - 3d, 3d + random.nextDouble() * 3d, random.nextDouble() * 6d - 3d);
			level.addFreshEntity(dirt);
		}
	}

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		if (!level.isClientSide()) {
			RandomSource random = level.getRandom();
			
			for (int count = 0; count < 20; count++) {
				ItemEntity dirt = new ItemEntity(level, entity.x(), entity.y(), entity.z(), new ItemStack(Items.DIRT));
				dirt.setDeltaMovement(random.nextDouble() * 4d - 2d, 2d + random.nextDouble() * 2d, random.nextDouble() * 4d - 2d);
				level.addFreshEntity(dirt);
			}
		}
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.SPAMMING_TNT.get();
	}

	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 160;
	}
}
