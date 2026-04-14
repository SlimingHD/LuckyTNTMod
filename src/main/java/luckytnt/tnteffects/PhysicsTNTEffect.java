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

public class PhysicsTNTEffect extends PrimedTNTEffect {

	private final int strength;

	public PhysicsTNTEffect(int strength) {
		this.strength = strength;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		RandomSource random = level.getRandom();
		ImprovedExplosion dummy = ImprovedExplosion.dummyExplosion(level);
		
		ImprovedExplosion explosion = new ImprovedExplosion(level, (Entity) entity, null, entity.x(), entity.y(), entity.z(), strength).setCustomExplosionEffect((lev, center, pos, state) -> {
			if (Math.max(state.getBlock().getExplosionResistance(), state.getFluidState().getExplosionResistance()) < 100f) {
				lev.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
				state.getBlock().onBlockExploded(state, lev, pos, dummy);
				try {
					FallingBlockEntity block = CustomFireworkEffect.CONSTRUCTOR.newInstance(lev, pos.getX() + 0.5d, pos.getY(), pos.getZ() + 0.5d, state);
					block.setDeltaMovement(random.nextDouble() * 2d - 1d, 0.5d + random.nextDouble() * 2d, random.nextDouble() * 2d - 1d);
					lev.addFreshEntity(block);
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		});
		explosion.doImprovedBlockExplosion(1f, 1f, false, false, null);
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.PHYSICS_TNT.get();
	}
}
