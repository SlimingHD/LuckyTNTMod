package luckytnt.tnteffects;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class RainbowFireworkEffect extends PrimedTNTEffect{

	@Override
	public void baseTick(IExplosiveEntity entity) {
		super.baseTick(entity);
		((Entity)entity).setDeltaMovement(((Entity)entity).getDeltaMovement().x, 0.8f, ((Entity)entity).getDeltaMovement().z);
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		for(int count = 0; count <= 300; count++) {
			
			@SuppressWarnings("rawtypes")
			Class[] parameters = new Class[]{Level.class, double.class, double.class, double.class, BlockState.class};
			Constructor<FallingBlockEntity> sandConstructor;
			try {
				sandConstructor = FallingBlockEntity.class.getDeclaredConstructor(parameters);
				sandConstructor.setAccessible(true);
				try {
					BlockState state = Blocks.WHITE_CONCRETE.defaultBlockState();
					int rand = new Random().nextInt(12);
					switch (rand) {
						case 0: state = Blocks.RED_CONCRETE.defaultBlockState(); break;
						case 1: state = Blocks.GREEN_CONCRETE.defaultBlockState(); break;
						case 2: state = Blocks.BLUE_CONCRETE.defaultBlockState(); break;
						case 3: state = Blocks.YELLOW_CONCRETE.defaultBlockState(); break;
						case 4: state = Blocks.BROWN_CONCRETE.defaultBlockState(); break;
						case 5: state = Blocks.CYAN_CONCRETE.defaultBlockState(); break;
						case 6: state = Blocks.LIME_CONCRETE.defaultBlockState(); break;
						case 7: state = Blocks.PURPLE_CONCRETE.defaultBlockState(); break;
						case 8: state = Blocks.PINK_CONCRETE.defaultBlockState(); break;
						case 9: state = Blocks.MAGENTA_CONCRETE.defaultBlockState(); break;
						case 10: state = Blocks.ORANGE_CONCRETE.defaultBlockState(); break;
						case 11: state = Blocks.LIGHT_BLUE_CONCRETE.defaultBlockState(); break;
					}
					FallingBlockEntity sand = sandConstructor.newInstance(entity.getLevel(), entity.getPos().x, entity.getPos().y, entity.getPos().z, state);
					sand.setDeltaMovement((Math.random() - Math.random()) * 1.5f, (Math.random() - Math.random()) * 1.5f, (Math.random() - Math.random()) * 1.5f);
					entity.getLevel().addFreshEntity(sand);
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			} catch (NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(10f, 10f, 10f), 1f), entity.x(), entity.y() + 1f, entity.z(), 0, 0, 0);
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
