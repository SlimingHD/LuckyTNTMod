package luckytnt.tnteffects;


import org.joml.Vector3f;

import luckytnt.config.LuckyTNTConfigValues;
import luckytnt.registry.BlockRegistry;
import luckytnt.util.CustomTNTConfig;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.Block;

public class CustomTNTEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity ent) {
		
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		ent.level().addParticle(new DustParticleOptions(new Vector3f(1f, 0f, 0f), 1f), ent.x(), ent.y() + 1f, ent.z(), 0, 0, 0);
		ent.level().addParticle(new DustParticleOptions(new Vector3f(0f, 1f, 0f), 1f), ent.x(), ent.y() + 1f, ent.z(), 0, 0, 0);
		ent.level().addParticle(new DustParticleOptions(new Vector3f(0f, 0f, 1f), 1f), ent.x(), ent.y() + 1f, ent.z(), 0, 0, 0);
		if(ent.level().isClientSide()) {
			CustomTNTConfig config = LuckyTNTConfigValues.CUSTOM_TNT_FIRST_EXPLOSION.get();
			if(ent.getPersistentData().getInt("level") == 0) {
				if(config == CustomTNTConfig.FIREWORK) {
					ent.level().addParticle(ParticleTypes.FLAME, ent.x(), ent.y() + 0.5f, ent.z(), 0, 0, 0);
				}
			}
			if(ent.getPersistentData().getInt("level") == 1) {
				config = LuckyTNTConfigValues.CUSTOM_TNT_SECOND_EXPLOSION.get();
				if(config == CustomTNTConfig.FIREWORK) {
					ent.level().addParticle(ParticleTypes.FLAME, ent.x(), ent.y() + 0.5f, ent.z(), 0, 0, 0);
				}
			}
			if(ent.getPersistentData().getInt("level") == 2) {
				config = LuckyTNTConfigValues.CUSTOM_TNT_THIRD_EXPLOSION.get();
				if(config == CustomTNTConfig.FIREWORK) {
					ent.level().addParticle(ParticleTypes.FLAME, ent.x(), ent.y() + 0.5f, ent.z(), 0, 0, 0);
				}
			}
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.CUSTOM_TNT.get();
	}
}
