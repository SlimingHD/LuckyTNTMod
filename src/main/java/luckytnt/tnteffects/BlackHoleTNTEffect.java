package luckytnt.tnteffects;


import java.util.List;
import java.util.Random;

import org.joml.Vector3f;

import luckytnt.event.LevelEvents;
import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
import luckytnt.registry.keys.AdvancementKeys;
import luckytnt.util.AdvancementHelper;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class BlackHoleTNTEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		Entity ent = (Entity)entity;
		int fuse = entity.getTNTFuse();
		if (fuse < 400 && fuse >= 300) {
			ent.setNoGravity(true);
			ent.setDeltaMovement(0d, 0.05d, 0d);
		}
		if (fuse < 300) {
			ent.setDeltaMovement(0d, 0d, 0d);
		}
		if (fuse < 350) {
			if (fuse % 20 == 0 && !entity.getLevel().isClientSide()) {
				for (int i = 0; i <= 400 + (int)Math.round((1d / (fuse * 0.5d))) * 1600d; i++) {
					int offX = new Random().nextInt(75) - new Random().nextInt(75);
					int offZ = new Random().nextInt(75) - new Random().nextInt(75);
					int offY = LevelEvents.getTopBlock(entity.getLevel(), (int)Math.round(entity.x()) + offX, (int)Math.round(entity.z()) + offZ, false);
					BlockPos pos = toBlockPos(new Vec3(entity.x() + offX, offY, entity.z() + offZ));
					FallingBlockEntity.fall(entity.getLevel(), pos, entity.getLevel().getBlockState(pos));
					entity.getLevel().setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
				}
			}
			
			List<LivingEntity> list = entity.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(entity.x() + 100d, entity.y() + 100d, entity.z() + 100d, entity.x() - 100d, entity.y() - 100d, entity.z() - 100d));
			List<FallingBlockEntity> blocks = entity.getLevel().getEntitiesOfClass(FallingBlockEntity.class, new AABB(entity.x() + 100d, entity.y() + 100d, entity.z() + 100d, entity.x() - 100d, entity.y() - 100d, entity.z() - 100d));
			
			for (FallingBlockEntity block : blocks) {
				double x = entity.x() - block.getX();
				double y = entity.y() - block.getY();
				double z = entity.z() - block.getZ();
				Vec3 vec = new Vec3(x, y, z);
				if (vec.length() <= 2) {
					block.discard();
				}
				Vec3 vec3 = vec.normalize().scale(0.4D);
				block.setDeltaMovement(vec3.add(0, 0.1D, 0));
			}
			
			for (LivingEntity living : list) {
				double x = entity.x() - living.getX();
				double y = entity.y() - living.getEyeY();
				double z = entity.z() - living.getZ();
				Vec3 vec = new Vec3(x, y, z);
				DamageSources sources = new DamageSources(entity.getLevel().registryAccess());
				if (vec.length() <= 2 && fuse % 80 == 0 && living instanceof Player) {
					living.hurt(sources.inWall(), 6f);
				}
				if (vec.length() <= 2 && !(living instanceof Player)) {
					living.discard();
				}
				Vec3 vec3 = vec.normalize().scale((1d / (0.25d * vec.length() + 0.0001d)) + 0.5d);
				living.setDeltaMovement(vec3);
			}
		}
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		EntityRegistry.TNT_X500_EFFECT.build().serverExplosion(entity);
		
		List<LivingEntity> list = entity.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(entity.x() + 100d, entity.y() + 100d, entity.z() + 100d, entity.x() - 100d, entity.y() - 100d, entity.z() - 100d));
		List<FallingBlockEntity> blocks = entity.getLevel().getEntitiesOfClass(FallingBlockEntity.class, new AABB(entity.x() + 100d, entity.y() + 100d, entity.z() + 100d, entity.x() - 100d, entity.y() - 100d, entity.z() - 100d));
	
		for (FallingBlockEntity block : blocks) {
			block.discard();
		}
		
		for (LivingEntity living : list) {
			double x = living.getX() - entity.x();
			double y = living.getEyeY() - entity.y();
			double z = living.getZ() - entity.z();
			Vec3 vec = new Vec3(x, y, z).normalize().scale(4);
			living.setDeltaMovement(vec);
		}
		
		AdvancementHelper.grantAdvancementToOwnerOrNearby(entity, AdvancementKeys.WHAT_LIES_BEYOND);
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		if (ent.getTNTFuse() < 350) {
			double phi = Math.PI * (3d - Math.sqrt(5d));
			for (int i = 0; i < 1000; i++) {
				double y = 1d - (i / 999d) * 2d;
				double radius = Math.sqrt(1d - y * y);
				
				double theta = phi * i;
				
				double x = Math.cos(theta) * radius;
				double z = Math.sin(theta) * radius;
				
				ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0f, 0f, 0f), 0.75f), ent.x() + x * 2d, ent.y() + 0.5d + y * 2d, ent.z() + 2d * z, 0, 0, 0);
			}
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.BLACK_HOLE_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 500;
	}
}
