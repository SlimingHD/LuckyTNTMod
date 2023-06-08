package luckytnt.tnteffects;


import java.util.List;
import java.util.Random;

import org.joml.Vector3f;

import luckytnt.event.LevelEvents;
import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
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
	public void explosionTick(IExplosiveEntity ent) {
		if(ent.getTNTFuse() < 400 && ent.getTNTFuse() >= 300) {
			((Entity)ent).setNoGravity(true);
			((Entity)ent).setDeltaMovement(0, 0.05, 0);
		}
		if(ent.getTNTFuse() < 300) {
			((Entity)ent).setDeltaMovement(0, 0, 0);
		}
		if(ent.getTNTFuse() < 350) {
			if(ent.getTNTFuse() % 20 == 0 && !ent.getLevel().isClientSide()) {
				for(int i = 0; i <= 400 + (int)Math.round((1D / ((double)ent.getTNTFuse() * 0.5D))) * 1600D; i++) {
					int offX = new Random().nextInt(75) - new Random().nextInt(75);
					int offZ = new Random().nextInt(75) - new Random().nextInt(75);
					int offY = LevelEvents.getTopBlock(ent.getLevel(), (int)Math.round(ent.x()) + offX, (int)Math.round(ent.z()) + offZ, false);
					BlockPos pos = toBlockPos(new Vec3(ent.x() + offX, offY, ent.z() + offZ));
					FallingBlockEntity.fall(ent.getLevel(), pos, ent.getLevel().getBlockState(pos));
					ent.getLevel().setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
				}
			}
			
			List<LivingEntity> list = ent.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(ent.x() + 100, ent.y() + 100, ent.z() + 100, ent.x() - 100, ent.y() - 100, ent.z() - 100));
			List<FallingBlockEntity> blocks = ent.getLevel().getEntitiesOfClass(FallingBlockEntity.class, new AABB(ent.x() + 100, ent.y() + 100, ent.z() + 100, ent.x() - 100, ent.y() - 100, ent.z() - 100));
			
			for(FallingBlockEntity block : blocks) {
				double x = ent.x() - block.getX();
				double y = ent.y() - block.getY();
				double z = ent.z() - block.getZ();
				Vec3 vec = new Vec3(x, y, z);
				if(vec.length() <= 2) {
					block.discard();
				}
				Vec3 vec3 = vec.normalize().scale(0.4D);
				block.setDeltaMovement(vec3.add(0, 0.1D, 0));
			}
			
			for(LivingEntity living : list) {
				double x = ent.x() - living.getX();
				double y = ent.y() - living.getEyeY();
				double z = ent.z() - living.getZ();
				Vec3 vec = new Vec3(x, y, z);
				DamageSources sources = new DamageSources(ent.getLevel().registryAccess());
				if(vec.length() <= 2 && ent.getTNTFuse() % 80 == 0 && living instanceof Player) {
					living.hurt(sources.inWall(), 6f);
				}
				if(vec.length() <= 2 && !(living instanceof Player)) {
					living.discard();
				}
				Vec3 vec3 = vec.normalize().scale((1D / (0.25D * vec.length() + 0.0001D)) + 0.5D);
				living.setDeltaMovement(vec3);
			}
		}
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		EntityRegistry.TNT_X500_EFFECT.build().serverExplosion(ent);
		
		List<LivingEntity> list = ent.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(ent.x() + 100, ent.y() + 100, ent.z() + 100, ent.x() - 100, ent.y() - 100, ent.z() - 100));
		List<FallingBlockEntity> blocks = ent.getLevel().getEntitiesOfClass(FallingBlockEntity.class, new AABB(ent.x() + 100, ent.y() + 100, ent.z() + 100, ent.x() - 100, ent.y() - 100, ent.z() - 100));
	
		for(FallingBlockEntity block : blocks) {
			block.discard();
		}
		
		for(LivingEntity living : list) {
			double x = living.getX() - ent.x();
			double y = living.getEyeY() - ent.y();
			double z = living.getZ() - ent.z();
			Vec3 vec = new Vec3(x, y, z).normalize().scale(4);
			living.setDeltaMovement(vec);
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		if(ent.getTNTFuse() < 350) {
			double phi = Math.PI * (3D - Math.sqrt(5D));
			for(int i = 0; i < 1000; i++) {
				double y = 1D - ((double)i / (1000D - 1D)) * 2D;
				double radius = Math.sqrt(1D - y * y);
				
				double theta = phi * i;
				
				double x = Math.cos(theta) * radius;
				double z = Math.sin(theta) * radius;
				
				ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0f, 0f, 0f), 0.75f), ent.x() + x * 2, ent.y() + 0.5D  + y * 2, ent.z() + 2 * z, 0, 0, 0);
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
