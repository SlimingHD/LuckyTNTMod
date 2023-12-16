package luckytnt.tnteffects;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import luckytnt.entity.PrimedCustomFirework;
import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
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

	@Override
	public void explosionTick(IExplosiveEntity ent) {
		if(ent.getTNTFuse() == 40 && ent instanceof PrimedCustomFirework tnt) {
			BlockPos pos = toBlockPos(new Vec3(ent.x(), ent.y() - 1f, ent.z()));
			ent.getPersistentData().putInt("x", pos.getX());
			ent.getPersistentData().putInt("y", pos.getY());
			ent.getPersistentData().putInt("z", pos.getZ());
			tnt.state = ent.getLevel().getBlockState(pos);
		}
		((Entity)ent).setDeltaMovement(((Entity)ent).getDeltaMovement().x, 0.8f, ((Entity)ent).getDeltaMovement().z);
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		if(ent instanceof PrimedCustomFirework fire) {
			BlockState state = ent.getLevel().getBlockState(new BlockPos(ent.getPersistentData().getInt("x"), ent.getPersistentData().getInt("y"), ent.getPersistentData().getInt("z")));
			if(fire.state != null) {
				state = fire.state;
			}
			
			for(int count = 0; count < 200; count++) {
				if(state.getBlock() instanceof TntBlock tnt) {
					tnt.onCaughtFire(state, ent.getLevel(), toBlockPos(ent.getPos()), null, ent.owner());
				} else {
					try {
						@SuppressWarnings("rawtypes")
						Class[] parameters = new Class[]{Level.class, double.class, double.class, double.class, BlockState.class};
						Constructor<FallingBlockEntity> sandConstructor = FallingBlockEntity.class.getDeclaredConstructor(parameters);
						sandConstructor.setAccessible(true);
						FallingBlockEntity sand = sandConstructor.newInstance(ent.getLevel(), ent.getPos().x, ent.getPos().y, ent.getPos().z, state);
						sand.setDeltaMovement(Math.random() * 1.5f - Math.random() * 1.5f, Math.random() * 1.5f - Math.random() * 1.5f, Math.random() * 1.5f - Math.random() * 1.5f);
						ent.getLevel().addFreshEntity(sand);
					} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
						e.printStackTrace();
					}
				}
			}
			BlockPos min = toBlockPos(ent.getPos()).offset(2, 2, 2);
			BlockPos max = toBlockPos(ent.getPos()).offset(-2, -2, -2);
			List<PrimedTnt> tnts = ent.getLevel().getEntitiesOfClass(PrimedTnt.class, new AABB(min.getX(), min.getY(), min.getZ(), max.getX(), max.getY(), max.getZ()));
			for(PrimedTnt tnt : tnts) {
				tnt.setDeltaMovement(Math.random() * 1.5f - Math.random() * 1.5f, Math.random() * 1.5f - Math.random() * 1.5f, Math.random() * 1.5f - Math.random() * 1.5f);
			}
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x(), ent.y(), ent.z(), 0, 0, 0);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.CUSTOM_FIREWORK.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 40;
	}
}
