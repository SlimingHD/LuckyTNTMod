package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.block.Block;

public class ChunkTNTEffect extends CubicTNTEffect {

	public ChunkTNTEffect() {
		super(75, 2000f);
	}
	
	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if (entity.getTNTFuse() == 160) {
			Entity lighting = new LightningBolt(EntityType.LIGHTNING_BOLT, entity.getLevel());
			lighting.setPos(entity.getPos());
			entity.getLevel().addFreshEntity(lighting);
		}
	}

	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		for (double i = 0; i < 3.25d; i += 0.25d) {
			entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x() + 1.5d, entity.y() - 1d + i, entity.z() + 1.5d, 0, 0, 0);
			entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x() - 1.5d, entity.y() - 1d + i, entity.z() - 1.5d, 0, 0, 0);
			entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x() + 1.5d, entity.y() - 1d + i, entity.z() - 1.5d, 0, 0, 0);
			entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x() - 1.5d, entity.y() - 1d + i, entity.z() + 1.5d, 0, 0, 0);
			entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x() - 1.5d + i, entity.y() - 1d, entity.z() + 1.5d, 0, 0, 0);
			entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x() - 1.5d, entity.y() - 1d, entity.z() - 1.5d + i, 0, 0, 0);
			entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x() - 1.5d + i, entity.y() + 2d, entity.z() + 1.5d, 0, 0, 0);
			entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x() - 1.5d, entity.y() + 2d, entity.z() - 1.5d + i, 0, 0, 0);
			entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x() + 1.5d - i, entity.y() - 1d, entity.z() - 1.5d, 0, 0, 0);
			entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x() + 1.5d, entity.y() - 1d, entity.z() + 1.5d - i, 0, 0, 0);
			entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x() + 1.5d - i, entity.y() + 2d, entity.z() - 1.5d, 0, 0, 0);
			entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x() + 1.5d, entity.y() + 2d, entity.z() + 1.5d - i, 0, 0, 0);
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.CHUNK_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 160;
	}
}
