package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
import luckytntlib.entity.PrimedLTNT;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;

public class DividingTNTEffect extends PrimedTNTEffect {
	
	@Override
	public void baseTick(IExplosiveEntity entity) {
		super.baseTick(entity);
		if (entity.getTNTFuse() == 60 && entity.getPersistentData().getInt("level") != 0) {
			double x = entity.getPersistentData().getDouble("x") - entity.x();
			double z = entity.getPersistentData().getDouble("z") - entity.z();
			double magnitude = Math.sqrt(x * x + z * z) + 0.01d;
			((Entity)entity).setDeltaMovement(x / magnitude, 1, z / magnitude);
		}
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		CompoundTag data = entity.getPersistentData();
		Level level = entity.getLevel();
		RandomSource random = level.getRandom();
		if (data.getInt("level") == 0) {		
			for (int offX = -50; offX < 50; offX += 10) {
				for (int offZ = -50; offZ < 50; offZ += 10) {
					for (int offY = 320; offY > -64; offY--) {
						BlockPos pos = toBlockPos(new Vec3(entity.x() + offX, entity.y() + offY, entity.z() + offZ));
						if (level.getBlockState(pos).isCollisionShapeFullBlock(level, pos) && !level.getBlockState(pos.above()).isCollisionShapeFullBlock(level, pos.above())) {
							PrimedLTNT projectile = EntityRegistry.DIVIDING_TNT.get().create(level);
							projectile.setPos(entity.getPos().add(offX, offY + 1, offZ));
							projectile.setOwner(entity.owner());
							projectile.getPersistentData().putInt("maxLevel", random.nextInt(5));
							projectile.getPersistentData().putInt("level", 1);
							projectile.getPersistentData().putDouble("x", entity.x());
							projectile.getPersistentData().putDouble("z", entity.z());
							level.addFreshEntity(projectile);
							break;
						}
					}
				}
			}
			entity.destroy();
		} else if (data.getInt("level") >= data.getInt("maxLevel")) {
			ImprovedExplosion explosion = new ImprovedExplosion(level, (Entity)entity, entity.getPos(), 10);
			explosion.doEntityExplosion(1f, true);
			explosion.doImprovedBlockExplosion(1f, 1f, false, false, null);
			explosion.spawnExplosionParticles();
			playExplosionSound(entity);
			entity.destroy();
		} else {
			ImprovedExplosion explosion = new ImprovedExplosion(level, (Entity)entity, entity.getPos(), 10);
			explosion.doEntityExplosion(1.5f, true);
			explosion.doImprovedBlockExplosion(1f, 1f, false, false, null);
			explosion.spawnExplosionParticles();
			playExplosionSound(entity);
			PrimedLTNT projectile = EntityRegistry.DIVIDING_TNT.get().create(level);
			projectile.setOwner(entity.owner());
			projectile.setPos(entity.getPos());
			projectile.setDeltaMovement(random.nextDouble() * 2d - 1d, 1d + random.nextDouble() * 0.75d, random.nextDouble() * 2d - 1d);
			projectile.getPersistentData().putInt("maxLevel", entity.getPersistentData().getInt("maxLevel"));
			projectile.getPersistentData().putInt("level", entity.getPersistentData().getInt("level") + 1);
			projectile.getPersistentData().putDouble("x", entity.getPersistentData().getDouble("x"));
			projectile.getPersistentData().putDouble("z", entity.getPersistentData().getDouble("z"));
			projectile.setDeltaMovement(0, 0, 0);
			level.addFreshEntity(projectile);
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.DIVIDING_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 80;
	}
}
