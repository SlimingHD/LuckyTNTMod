package luckytnt.tnteffects;

import java.lang.reflect.InvocationTargetException;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class RainbowFireworkEffect extends PrimedTNTEffect {

	public static final Block[] CONCRETE = new Block[]{Blocks.WHITE_CONCRETE, Blocks.RED_CONCRETE, Blocks.GREEN_CONCRETE, Blocks.BLUE_CONCRETE, Blocks.YELLOW_CONCRETE, Blocks.BROWN_CONCRETE, Blocks.CYAN_CONCRETE, Blocks.LIME_CONCRETE, Blocks.PURPLE_CONCRETE, Blocks.PINK_CONCRETE, Blocks.MAGENTA_CONCRETE, Blocks.ORANGE_CONCRETE, Blocks.LIGHT_BLUE_CONCRETE};
	
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
				FallingBlockEntity sand = CustomFireworkEffect.CONSTRUCTOR.newInstance(level, entity.x(), entity.y(), entity.z(), CONCRETE[random.nextInt(CONCRETE.length)].defaultBlockState());
				sand.setDeltaMovement(random.nextDouble() * 3d - 1.5d, random.nextDouble() * 3d - 1.5d, random.nextDouble() * 3d - 1.5d);
				level.addFreshEntity(sand);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(10f, 10f, 10f), 1f), entity.x(), entity.y() + 1d, entity.z(), 0d, 0d, 0d);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.RAINBOW_FIREWORK.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 40;
	}
}
