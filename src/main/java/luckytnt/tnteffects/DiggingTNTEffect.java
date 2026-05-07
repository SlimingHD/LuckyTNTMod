package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.keys.AdvancementKeys;
import luckytnt.util.AdvancementHelper;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class DiggingTNTEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion particleExplosion = new ImprovedExplosion(entity.getLevel(), entity.getPos(), 4);
		particleExplosion.spawnExplosionParticles();
		float vectorLength = 480f;
		int removedBlocks = 0;
		for (float y = 0f; y <= vectorLength; y++) {
			BlockPos pos = toBlockPos(entity.getPos().subtract(0d, y, 0d));
			BlockState state = entity.getLevel().getBlockState(pos);
			vectorLength -= state.getExplosionResistance(entity.getLevel(), pos, particleExplosion);
			if (vectorLength < y) {
				break;
			}
			++removedBlocks;
			state.getBlock().wasExploded(entity.getLevel(), pos, particleExplosion);
			entity.getLevel().setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
		}
		if (removedBlocks <= 3) {
			AdvancementHelper.grantAdvancementToOwnerOrNearby(entity, AdvancementKeys.SIZE_MATTERS);
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(ParticleTypes.SMOKE, entity.x(), entity.y() + 1.4f, entity.z(), 0, -0.1f, 0);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.DIGGING_TNT.get();
	}
}
