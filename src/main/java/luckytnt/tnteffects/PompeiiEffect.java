package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
import luckytntlib.entity.LExplosiveProjectile;
import luckytntlib.entity.PrimedLTNT;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class PompeiiEffect extends PrimedTNTEffect{

	@SuppressWarnings("resource")
	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if(entity instanceof PrimedLTNT) {
			if(entity.getTNTFuse() < 150) {
				if(entity.getTNTFuse() % 15 == 0) {
					for(int i = 0; i < 30; i++) {
						LExplosiveProjectile pompeii = EntityRegistry.POMPEII_PROJECTILE.get().create(entity.getLevel());
						pompeii.setPos(entity.getPos());
						pompeii.setOwner(entity.owner());
						pompeii.shoot((Math.random() * 3D - 1.5D) * 0.1f, 0.6f + Math.random() * 0.4f, (Math.random() * 3D - 1.5D) * 0.1f, 3f + entity.getLevel().random.nextFloat() * 2f, 0f);	
						pompeii.setSecondsOnFire(1000);
						entity.getLevel().addFreshEntity(pompeii);
						entity.getLevel().playSound(null, toBlockPos(entity.getPos()), SoundEvents.GENERIC_EXPLODE, SoundSource.MASTER, 3, 1);
					}
				}
			}
		}
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		if(ent instanceof LExplosiveProjectile) {
			if(ent.getLevel().getBlockState(toBlockPos(ent.getPos()).above()).getExplosionResistance(ent.getLevel(), toBlockPos(ent.getPos()), ImprovedExplosion.dummyExplosion(ent.getLevel())) <= 200) {
				ent.getLevel().setBlock(toBlockPos(ent.getPos()).above(), Blocks.LAVA.defaultBlockState(), 3);
			}
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		if(ent instanceof PrimedLTNT) {
			ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x() + 0.5f, ent.y() + 1f, ent.z() + 0.5f, 0.05f, 0.2f, 0.05f);
			ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x() - 0.5f, ent.y() + 1f, ent.z() - 0.5f, -0.05f, 0.2f, -0.05f);
			ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x() + 0.5f, ent.y() + 1f, ent.z() - 0.5f, 0.05f, 0.2f, -0.05f);
			ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x() - 0.5f, ent.y() + 1f, ent.z() + 0.5f, -0.05f, 0.2f, 0.05f);
			ent.getLevel().addParticle(ParticleTypes.LAVA, ent.x(), ent.y() + 1f, ent.z(), 0, 0, 0);
		}
		else {
			ent.getLevel().addParticle(ParticleTypes.LARGE_SMOKE, true, ent.x(), ent.y() + 0.5f, ent.z(), 0, 0.1f, 0);
		}
	}
	
	@Override
	public BlockState getBlockState(IExplosiveEntity ent) {
		return ent instanceof PrimedLTNT ? BlockRegistry.POMPEII.get().defaultBlockState() : Blocks.MAGMA_BLOCK.defaultBlockState();
	}
	
	@Override
	public boolean airFuse() {
		return true;
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return entity instanceof PrimedLTNT ? 220 : 100000;
	}
}
