package luckytnt.tnteffects;

import java.lang.reflect.InvocationTargetException;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
import luckytntlib.entity.PrimedLTNT;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class GrandeFinaleEffect extends PrimedTNTEffect {
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		RandomSource random = level.getRandom();
		
		for (int i = 0; i < 1000; i++) {
			try {
				FallingBlockEntity concrete = CustomFireworkEffect.CONSTRUCTOR.newInstance(level, entity.x(), entity.y(), entity.z(), RainbowFireworkEffect.CONCRETE[random.nextInt(RainbowFireworkEffect.CONCRETE.length)].defaultBlockState());
				concrete.setDeltaMovement(random.nextDouble() * 10d - 5d, random.nextDouble() * 10d - 5d, random.nextDouble() * 10d - 5d);
				concrete.dropItem = false;
				level.addFreshEntity(concrete);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | ExceptionInInitializerError e) {
				e.printStackTrace();
			}
		}
		for (int i = 0; i < 500; i++) {
			PrimedLTNT tnt = EntityRegistry.TNT.get().create(level);
			tnt.setOwner(entity.owner());
			tnt.setPos(entity.getPos());
			tnt.setTNTFuse(80 + random.nextInt(101));
			tnt.setDeltaMovement(random.nextDouble() * 10d - 5d, random.nextDouble() * 10d - 5d, random.nextDouble() * 10d - 5d);
			level.addFreshEntity(tnt);
		}
	}
	
	@Override
	public void explosionTick(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		if (!level.isClientSide()) {
			RandomSource random = level.getRandom();
			BlockPos pos = BlockPos.containing(entity.getPos());
			
			level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
			level.setBlockAndUpdate(pos.above(), Blocks.AIR.defaultBlockState());
			
			if (entity.getTNTFuse() % 10 == 0 && random.nextBoolean()) {
				int rand = random.nextInt(4);
				PrimedLTNT tnt;
				if (rand == 0) {
					tnt = EntityRegistry.SAND_FIREWORK.get().create(level);
				} else if (rand == 1) {
					tnt = EntityRegistry.GRAVEL_FIREWORK.get().create(level);
				} else if (rand == 2) {
					tnt = EntityRegistry.RAINBOW_FIREWORK.get().create(level);
				} else {
					tnt = EntityRegistry.NEW_YEARS_FIREWORK.get().create(level);
					tnt.getPersistentData().putInt("type", 1);
				}
				level.playSound(null, pos, SoundEvents.FIREWORK_ROCKET_LAUNCH, SoundSource.MASTER, 3f, 1f);
				tnt.setPos(entity.getPos());
				tnt.setOwner(entity.owner());
				tnt.setDeltaMovement(random.nextDouble() * 10d - 5d, 0d, random.nextDouble() * 10d - 5d);
				tnt.setTNTFuse(40 + random.nextInt(41));
				level.addFreshEntity(tnt);
			}
			
			if (entity.getTNTFuse() <= 40 && entity instanceof Entity ent) {
				ent.setDeltaMovement(ent.getDeltaMovement().x, 1.6d, ent.getDeltaMovement().z);
			}
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		if (entity.getTNTFuse() <= 40) {
			entity.getLevel().addParticle(ParticleTypes.LARGE_SMOKE, entity.x(), entity.y(), entity.z(), 0d, -0.5d, 0d);
		} else {
			super.spawnParticles(entity);
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.GRANDE_FINALE.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 440;
	}
}
