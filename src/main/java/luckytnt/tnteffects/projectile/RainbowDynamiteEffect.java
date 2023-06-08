package luckytnt.tnteffects.projectile;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import org.joml.Vector3f;

import luckytnt.registry.ItemRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class RainbowDynamiteEffect extends PrimedTNTEffect{

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		for(int count = 0; count <= 100; count++) {
			
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
	public void explosionTick(IExplosiveEntity entity) {
		((Entity)entity).setDeltaMovement(((Entity)entity).getDeltaMovement().add(0f, 0.08f, 0f));
		
		for(int count = 0; count < 5; count++) {
			
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
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(10f, 10f, 10f), 1f), entity.x(), entity.y(), entity.z(), 0, 0, 0);
	}
	
	@Override
	public boolean airFuse() {
		return true;
	}
	
	@Override
	public Item getItem() {
		return ItemRegistry.RAINBOW_DYNAMITE.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 40;
	}
}
