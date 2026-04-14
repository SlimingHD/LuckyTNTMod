package luckytnt.tnteffects;

import java.lang.reflect.InvocationTargetException;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class ParticlePhysicsTNTEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		RandomSource random = level.getRandom();
		ImprovedExplosion dummy = ImprovedExplosion.dummyExplosion(level);

		ImprovedExplosion explosion = new ImprovedExplosion(level, (Entity) entity, null, entity.x(), entity.y(), entity.z(), 25).setCustomExplosionEffect((lev, center, pos, state) -> {
			lev.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
			state.getBlock().onBlockExploded(state, lev, pos, dummy);
			try {
				FallingBlockEntity block = CustomFireworkEffect.CONSTRUCTOR.newInstance(lev, pos.getX() + 0.5d, pos.getY(), pos.getZ() + 0.5d, state);
				block.setDeltaMovement(random.nextDouble() * 4d - 2d, 1d + random.nextDouble() * 3d, random.nextDouble() * 4d - 2d);
				lev.addFreshEntity(block);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		});
		explosion.doImprovedBlockExplosion(1f, 1.5f, false, false, null);
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.PARTICLE_PHYSICS_TNT.get();
	}

	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 160;
	}
}
