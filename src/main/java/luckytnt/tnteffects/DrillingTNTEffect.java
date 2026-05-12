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

public class DrillingTNTEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion particleExplosion = new ImprovedExplosion(entity.getLevel(), entity.getPos(), 4);
		particleExplosion.spawnExplosionParticles();
		for(int x = -3; x <= 3; x++) {
			for(int z = -3; z <= 3; z++) {
				double distance = Math.sqrt(x * x + z * z);
				if(distance <= 3) {
					float vectorLength = 480f;
					for(float y = 0f; y <= vectorLength; y++) {
						BlockPos pos = toBlockPos(entity.getPos().subtract(x, y, z));
						BlockState state = entity.getLevel().getBlockState(pos);
						vectorLength -= state.getExplosionResistance(entity.getLevel(), pos, particleExplosion);
						if (vectorLength < y) {
							break;
						}
						entity.getLevel().setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
						state.getBlock().wasExploded(entity.getLevel(), pos, particleExplosion);
					}
				}
			}
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(ParticleTypes.SMOKE, entity.x(), entity.y() + 1.6f, entity.z(), 0, -0.1f, 0);
		entity.getLevel().addParticle(ParticleTypes.SMOKE, entity.x() + 0.4f, entity.y() + 1.4f, entity.z() + 0.4f, 0, -0.1f, 0);
		entity.getLevel().addParticle(ParticleTypes.SMOKE, entity.x() + 0.4f, entity.y() + 1.4f, entity.z() - 0.4f, 0, -0.1f, 0);
		entity.getLevel().addParticle(ParticleTypes.SMOKE, entity.x() - 0.4f, entity.y() + 1.4f, entity.z() + 0.4f, 0, -0.1f, 0);
		entity.getLevel().addParticle(ParticleTypes.SMOKE, entity.x() - 0.4f, entity.y() + 1.4f, entity.z() - 0.4f, 0, -0.1f, 0);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.DRILLING_TNT.get();
	}
}
