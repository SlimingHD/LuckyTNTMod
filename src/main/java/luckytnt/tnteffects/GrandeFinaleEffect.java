package luckytnt.tnteffects;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
import luckytntlib.entity.PrimedLTNT;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class GrandeFinaleEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		
	}
	
	@Override
	public void explosionTick(IExplosiveEntity ent) {
		if(ent.getTNTFuse() % (int)(1 + Math.random() * 50) == 0) {
			PrimedLTNT entity = EntityRegistry.SAND_FIREWORK.get().create(ent.getLevel());
			int random = new Random().nextInt(4);
			switch(random){
				case 0: entity = EntityRegistry.SAND_FIREWORK.get().create(ent.getLevel()); break;
				case 1: entity = EntityRegistry.GRAVEL_FIREWORK.get().create(ent.getLevel()); break;
				case 2: entity = EntityRegistry.RAINBOW_FIREWORK.get().create(ent.getLevel());; break;
				case 3: entity = EntityRegistry.NEW_YEARS_FIREWORK.get().create(ent.getLevel());
						entity.getPersistentData().putInt("type", 1); break;
			}
			ent.getLevel().playSound(null, toBlockPos(ent.getPos()), SoundEvents.FIREWORK_ROCKET_LAUNCH, SoundSource.MASTER, 3, 1);
			entity.setPos(ent.getPos());
			entity.setOwner(ent.owner());
			entity.setDeltaMovement(Math.random() * 5 - Math.random() * 5, 0, Math.random() * 5 - Math.random() * 5);
			entity.setTNTFuse(40 + new Random().nextInt(41));
			ent.getLevel().addFreshEntity(entity);
		}
		ent.getLevel().setBlock(toBlockPos(ent.getPos()), Blocks.AIR.defaultBlockState(), 3);
		ent.getLevel().setBlock(toBlockPos(ent.getPos()).offset(0, 1, 0), Blocks.AIR.defaultBlockState(), 3);
		if(ent.getTNTFuse() <= 40) {
			((Entity)ent).setDeltaMovement(((Entity)ent).getDeltaMovement().x, 1.6f, ((Entity)ent).getDeltaMovement().z);
			ent.getLevel().addParticle(ParticleTypes.LARGE_SMOKE, ent.x(), ent.y(), ent.z(), 0, -0.5f, 0);
			if(ent.getTNTFuse() == 0) {
				Block template = Blocks.WHITE_CONCRETE;
				for(int count = 0; count < 1000; count++) {
					int rand = new Random().nextInt(12);
					switch (rand) {
						case 0: template = Blocks.RED_CONCRETE; break;
						case 1: template = Blocks.GREEN_CONCRETE; break;
						case 2: template = Blocks.BLUE_CONCRETE; break;
						case 3: template = Blocks.YELLOW_CONCRETE; break;
						case 4: template = Blocks.BROWN_CONCRETE; break;
						case 5: template = Blocks.CYAN_CONCRETE; break;
						case 6: template = Blocks.LIME_CONCRETE; break;
						case 7: template = Blocks.PURPLE_CONCRETE; break;
						case 8: template = Blocks.PINK_CONCRETE; break;
						case 9: template = Blocks.MAGENTA_CONCRETE; break;
						case 10: template = Blocks.ORANGE_CONCRETE; break;
						case 11: template = Blocks.LIGHT_BLUE_CONCRETE; break;
					}
					FallingBlockEntity block = null;
					try {
						@SuppressWarnings("rawtypes")
						Class[] classes = new Class[]{Level.class, double.class, double.class, double.class, BlockState.class};
						Constructor<FallingBlockEntity> constructor = FallingBlockEntity.class.getDeclaredConstructor(classes);
						constructor.setAccessible(true);
						block = constructor.newInstance(ent.getLevel(), ent.x(), ent.y(), ent.z(), template.defaultBlockState());
					} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
					}
					if(block != null) {
						block.dropItem = false;
						block.setDeltaMovement(Math.random() * 5f - Math.random() * 5f, Math.random() * 5f - Math.random() * 5f, Math.random() * 5f - Math.random() * 5f);
						ent.getLevel().addFreshEntity(block);
					}
				}
				for(int count = 0; count < 500; count++) {
					PrimedLTNT tnt = EntityRegistry.TNT.get().create(ent.getLevel());
					tnt.setOwner(ent.owner());
					tnt.setPos(ent.getPos());
					tnt.setTNTFuse(80 + (int)(Math.random() * 100));
					tnt.setDeltaMovement(Math.random() * 5f - Math.random() * 5f, Math.random() * 5f - Math.random() * 5f, Math.random() * 5f - Math.random() * 5f);
					ent.getLevel().addFreshEntity(tnt);
				}
			}
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.GRANDE_FINALE.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 440;
	}
}
