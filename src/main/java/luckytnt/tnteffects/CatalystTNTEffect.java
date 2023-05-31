package luckytnt.tnteffects;

import java.util.Random;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;

public class CatalystTNTEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity ent) {
		if(ent.getTNTFuse() < 200) {
			if(ent.getPersistentData().getInt("nextExplosion") <= 0) {
				double x = ent.x() + Math.random() * 40 - Math.random() * 40;
				double y = ent.y() + Math.random() * 15 - Math.random() * 15;
				double z = ent.z() + Math.random() * 40 - Math.random() * 40;
				if(!ent.level().isClientSide()) {
					ImprovedExplosion explosion = new ImprovedExplosion(ent.level(), (Entity)ent, new Vec3(x, y, z), 25 + new Random().nextInt(16));
					explosion.doEntityExplosion(2f, true);
					explosion.doBlockExplosion(1f, 1f, 0.75f, 1f, false, false);
				}
				ent.level().playSound(null, new BlockPos(Mth.floor(x), Mth.floor(y), Mth.floor(z)), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 4, (1.0F + (ent.level().getRandom().nextFloat() - ent.level().getRandom().nextFloat()) * 0.2F) * 0.7F);
				ent.getPersistentData().putInt("nextExplosion", 4 + (int)Math.round(Math.random()));
			}
			ent.getPersistentData().putInt("nextExplosion", ent.getPersistentData().getInt("nextExplosion") - 1);
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		for(double i = 0D; i < 1D; i += 0.05D) {
			ent.level().addParticle(new DustParticleOptions(new Vector3f(1f, 0f, 0f), 1f), ent.x() + 0.5D, ent.y() + i, ent.z() + 0.5D, 0, 0, 0);
			ent.level().addParticle(new DustParticleOptions(new Vector3f(1f, 0f, 0f), 1f), ent.x() - 0.5D, ent.y() + i, ent.z() + 0.5D, 0, 0, 0);
			ent.level().addParticle(new DustParticleOptions(new Vector3f(1f, 0f, 0f), 1f), ent.x() + 0.5D, ent.y() + i, ent.z() - 0.5D, 0, 0, 0);
			ent.level().addParticle(new DustParticleOptions(new Vector3f(1f, 0f, 0f), 1f), ent.x() - 0.5D, ent.y() + i, ent.z() - 0.5D, 0, 0, 0);
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.CATALYST_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 340;
	}
}
