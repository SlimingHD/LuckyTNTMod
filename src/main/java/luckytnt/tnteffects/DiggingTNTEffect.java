package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
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
		ImprovedExplosion dummyExplosion = ImprovedExplosion.dummyExplosion(entity.getLevel());
		float vectorLength = 240f;
		for (float y = vectorLength; y >= 0; y--) {
			BlockPos pos = toBlockPos(entity.getPos().subtract(0d, y, 0d));
			BlockState state = entity.getLevel().getBlockState(pos);
			y -= state.getExplosionResistance(entity.getLevel(), pos, dummyExplosion);
			if (y < 0) {
				break;
			}
			state.getBlock().wasExploded(entity.getLevel(), pos, dummyExplosion);
			entity.getLevel().setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
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
