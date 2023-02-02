package luckytnt.tnteffects.projectile;

import java.util.List;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EffectRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class TsarBombaBombEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion explosion = new ImprovedExplosion(entity.level(), entity.getPos(), 160f);
		explosion.doEntityExplosion(15f, true);
		explosion.doBlockExplosion(1f, 1f, 0.167f, 0.05f, false, true);
		
		List<LivingEntity> list = entity.level().getEntitiesOfClass(LivingEntity.class, new AABB(entity.x() - 90, entity.y() - 65, entity.z() - 90, entity.x() + 90, entity.y() + 65, entity.z() + 90));
		for(LivingEntity living : list) {
			living.addEffect(new MobEffectInstance(EffectRegistry.CONTAMINATED_EFFECT.get(), 3600, 0, true, true, true));
		}
		
		for(int offX = -300; offX <= 300; offX++) {
			for(int offY = -300 / 3; offY <= 300 / 3; offY++) {
				for(int offZ = -300; offZ <= 300; offZ++) {
					double distance = Math.sqrt(offX * offX + offY * offY + offZ * offZ);
					BlockPos pos = new BlockPos(entity.x() + offX, entity.y() + offY, entity.z() + offZ);
					BlockState state = entity.level().getBlockState(pos);
					if(distance <= 300 && state.getExplosionResistance(entity.level(), pos, ImprovedExplosion.dummyExplosion()) <= 200) {
						if(distance <= 150 && entity.level().getBlockState(pos.below()).isFaceSturdy(entity.level(), pos.below(), Direction.UP) && Math.random() < 0.2D && (state.isAir() || state.getBlock().defaultDestroyTime() <= 0.2f)) {
							entity.level().setBlock(pos, BlockRegistry.NUCLEAR_WASTE.get().defaultBlockState(), 3);
						}
						if(state.is(BlockTags.LEAVES)) {
							entity.level().setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
						}
					}
				}
			}
		}
		
		if(entity.level() instanceof ServerLevel sl) {
			for(int count = 0; count < 1500; count++) {
				sl.addParticle(new DustParticleOptions(new Vector3f(1f, 2f, 0f), 10f), true, entity.x() + Math.random() * 60 - Math.random() * 60, entity.y() + Math.random() * 3 - Math.random() * 3, entity.z() + Math.random() * 60 - Math.random() * 60, 0, 0, 0);
			}
			for(int count = 0; count < 1000; count++) {
				sl.addParticle(new DustParticleOptions(new Vector3f(1f, 2f, 0f), 10f), true, entity.x() + Math.random() * 20 - Math.random() * 20, entity.y() + 3 + Math.random() * 3 - Math.random() * 3, entity.z() + Math.random() * 20 - Math.random() * 20, 0, 0, 0);
			}
			for(int count = 0; count < 800; count++) {
				sl.addParticle(new DustParticleOptions(new Vector3f(1f, 2f, 0f), 10f), true, entity.x() + Math.random() * 10 - Math.random() * 10, entity.y() + Math.random() * 3 - Math.random() * 3, entity.z() + Math.random() * 10 - Math.random() * 10, 0, 0, 0);
			}
			for(int count = 0; count < 600; count++) {
				sl.addParticle(new DustParticleOptions(new Vector3f(1f, 2f, 0f), 10f), true, entity.x() + Math.random() * 6 - Math.random() * 6, entity.y() + 4 + Math.random() * 3 - Math.random() * 3, entity.z() + Math.random() * 6 - Math.random() * 6, 0, 0, 0);
			}
			for(int count = 0; count < 600; count++) {
				sl.addParticle(new DustParticleOptions(new Vector3f(1f, 2f, 0f), 10f), true, entity.x() + Math.random() * 2 - Math.random() * 2, entity.y() + 15 + Math.random() * 12 - Math.random() * 12, entity.z() + Math.random() * 2 - Math.random() * 2, 0, 0, 0);
			}
			for(int count = 0; count < 600; count++) {
				sl.addParticle(new DustParticleOptions(new Vector3f(1f, 2f, 0f), 10f), true, entity.x() + Math.random() * 6 - Math.random() * 6, entity.y() + 22 + Math.random() * 3 - Math.random() * 3, entity.z() + Math.random() * 6 - Math.random() * 6, 0, 0, 0);
			}
			for(int count = 0; count < 600; count++) {
				sl.addParticle(new DustParticleOptions(new Vector3f(1f, 2f, 0f), 10f), true, entity.x() + Math.random() * 6 - Math.random() * 6, entity.y() + 29 + Math.random() * 3 - Math.random() * 3, entity.z() + Math.random() * 6 - Math.random() * 6, 0, 0, 0);
			}
			for(int count = 0; count < 2000; count++) {
				sl.addParticle(new DustParticleOptions(new Vector3f(1f, 2f, 0f), 10f), true, entity.x() + Math.random() * 12 - Math.random() * 12, entity.y() + 24 + Math.random() * 6 - Math.random() * 6, entity.z() + Math.random() * 12 - Math.random() * 12, 0, 0, 0);
			}
			for(int count = 0; count < 2000; count++) {
				sl.addParticle(ParticleTypes.LARGE_SMOKE, true, entity.x() + Math.random() * 2 - Math.random() * 2, entity.y() + 22 + Math.random() * 2 - Math.random() * 2, entity.z() + Math.random() * 2 - Math.random() * 2, Math.random() * 2 - Math.random() * 2, Math.random() * 2 - Math.random() * 2, Math.random() * 2 - Math.random() * 2);
			}
		}
	}
	
	@Override
	public float getSize(IExplosiveEntity ent) {
		return 2f;
	}
}
