package luckytnt.tnteffects;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import luckytnt.entity.PrimedCustomFirework;
import luckytnt.registry.BlockRegistry;
import luckytnt.registry.keys.AdvancementKeys;
import luckytnt.util.AdvancementHelper;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class CustomFireworkEffect extends PrimedTNTEffect {

	public static Constructor<FallingBlockEntity> CONSTRUCTOR;
	
	static {
		try {
			Class<?>[] CONSTRUCTOR_PARAMETERS = new Class<?>[]{Level.class, double.class, double.class, double.class, BlockState.class};
			CONSTRUCTOR = FallingBlockEntity.class.getDeclaredConstructor(CONSTRUCTOR_PARAMETERS);
			CONSTRUCTOR.setAccessible(true);		
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void explosionTick(IExplosiveEntity entity) {
		Entity ent = (Entity)entity;
		if (entity.getTNTFuse() == 40 && entity instanceof PrimedCustomFirework tnt) {
			BlockPos pos = toBlockPos(new Vec3(entity.x(), entity.y() - 1f, entity.z()));
			entity.getPersistentData().putInt("x", pos.getX());
			entity.getPersistentData().putInt("y", pos.getY());
			entity.getPersistentData().putInt("z", pos.getZ());
			tnt.state = entity.getLevel().getBlockState(pos);
		}
		ent.setDeltaMovement(ent.getDeltaMovement().x, 0.8f, ent.getDeltaMovement().z);
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		if (entity instanceof PrimedCustomFirework firework) {
			Level level = entity.getLevel();
			RandomSource random = level.getRandom();
			BlockState state = level.getBlockState(new BlockPos(entity.getPersistentData().getInt("x"), entity.getPersistentData().getInt("y"), entity.getPersistentData().getInt("z")));
			if (firework.state != null) {
				state = firework.state;
			}
			if (state.is(getBlock())) {
				AdvancementHelper.grantAdvancementToOwnerOrNearby(entity, AdvancementKeys.FEEDBACK_LOOP);
				return;
			}
			
			for (int count = 0; count < 200; count++) {
				if (state.getBlock() instanceof TntBlock tnt) {
					tnt.onCaughtFire(state, entity.getLevel(), toBlockPos(entity.getPos()), null, entity.owner());
				} else {
					try {
						FallingBlockEntity fallingBlock = CONSTRUCTOR.newInstance(level, entity.x(), entity.y(), entity.z(), state);
						fallingBlock.setDeltaMovement(random.nextDouble() * 3d - 1.5d, random.nextDouble() * 3d - 1.5d, random.nextDouble() * 3d - 1.5d);
						fallingBlock.dropItem = false;
						level.addFreshEntity(fallingBlock);
					} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
			List<PrimedTnt> tnts = entity.getLevel().getEntitiesOfClass(PrimedTnt.class, new AABB(entity.getPos().add(-2d, -2d, -2d), entity.getPos().add(2d, 2d, 2d)));
			for (PrimedTnt tnt : tnts) {
				tnt.setDeltaMovement(Math.random() * 1.5f - Math.random() * 1.5f, Math.random() * 1.5f - Math.random() * 1.5f, Math.random() * 1.5f - Math.random() * 1.5f);
			}
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x(), entity.y(), entity.z(), 0, 0, 0);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.CUSTOM_FIREWORK.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 40;
	}
}
