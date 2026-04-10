package luckytnt.tnteffects;

import java.lang.reflect.InvocationTargetException;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class GravelFireworkEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if (entity instanceof Entity ent) {
			ent.setDeltaMovement(ent.getDeltaMovement().x, 0.8f, ent.getDeltaMovement().z);
		}
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		RandomSource random = level.getRandom();
		for (int count = 0; count <= 300; count++) {
			try {
				FallingBlockEntity gravel = CustomFireworkEffect.CONSTRUCTOR.newInstance(level, entity.x(), entity.y(), entity.z(), Blocks.GRAVEL.defaultBlockState());
				gravel.setDeltaMovement(random.nextDouble() * 3d - 1.5d, random.nextDouble() * 3d - 1.5d, random.nextDouble() * 3d - 1.5d);
				gravel.dropItem = false;
				level.addFreshEntity(gravel);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x(), entity.y(), entity.z(), 0d, 0d, 0d);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.GRAVEL_FIREWORK.get();
	}

	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 40;
	}
}
