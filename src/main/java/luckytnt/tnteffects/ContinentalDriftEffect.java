package luckytnt.tnteffects;

import java.util.List;
import java.util.Random;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ContinentalDriftEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity ent) {
		if(ent.getTNTFuse() == 400) {
			double vecx = Math.random() * 2D - 1D;
			double vecz = Math.random() * 2D - 1D;
			Vec3 vec = new Vec3(vecx, 0, vecz).normalize();
			((Entity)ent).getPersistentData().putDouble("vecx", vec.x);
			((Entity)ent).getPersistentData().putDouble("vecz", vec.z);
			
			double vecx2 = Math.random() * 2D - 1D;
			double vecz2 = Math.random() * 2D - 1D;
			Vec3 vec2 = new Vec3(vecx2, 0, vecz2).normalize();
			((Entity)ent).getPersistentData().putDouble("vecx2", vec2.x);
			((Entity)ent).getPersistentData().putDouble("vecz2", vec2.z);
			
			((Entity)ent).getPersistentData().putDouble("x", ent.x());
			((Entity)ent).getPersistentData().putDouble("y", ent.y());
			((Entity)ent).getPersistentData().putDouble("z", ent.z());
	      	
			((Entity)ent).getPersistentData().putInt("second", 30 + new Random().nextInt(101));
	      	
	      	List<Player> list = ent.getLevel().getEntitiesOfClass(Player.class, new AABB(ent.x() - 200, ent.y() - 200, ent.z() - 200, ent.x() + 200, ent.y() + 200, ent.z() + 200));
	      	for(Player player : list) {
	      		player.getPersistentData().putInt("shakeTime", 400);
	      	}
		}
		
		if(ent.getTNTFuse() <= 400 && (ent.getTNTFuse() % 60 == 0 || ent.getTNTFuse() == 400) && !ent.getLevel().isClientSide()) {
			BlockPos origin = toBlockPos(new Vec3(((Entity)ent).getPersistentData().getDouble("x"), ((Entity)ent).getPersistentData().getDouble("y"), ((Entity)ent).getPersistentData().getDouble("z")));
			BlockPos start = origin.offset(toBlockPos(new Vec3(((Entity)ent).getPersistentData().getDouble("vecx") * -80, 0, ((Entity)ent).getPersistentData().getDouble("vecz") * -80)));
			Vec3 vec = new Vec3(((Entity)ent).getPersistentData().getDouble("vecx"), 0, ((Entity)ent).getPersistentData().getDouble("vecz"));
			Vec3 vec2 = new Vec3(((Entity)ent).getPersistentData().getDouble("vecx2"), 0, ((Entity)ent).getPersistentData().getDouble("vecz2"));
			BlockPos start2 = start.offset(toBlockPos(new Vec3(vec.x * ((Entity)ent).getPersistentData().getInt("second"), 0, vec.z * ((Entity)ent).getPersistentData().getInt("second")))).offset(toBlockPos(new Vec3(vec2.x * 8, 0, vec2.z * 8)));
			
			for(double i = 0; i < 160D; i += 1D) {
				for(int offX = -10; offX <= 10; offX++) {
					for(int offZ = -10; offZ <= 10; offZ++) {
						double distance = Math.sqrt(offX * offX + offZ * offZ);
						BlockPos pos = start.offset(toBlockPos(new Vec3(i * vec.x + offX, 0, i * vec.z + offZ)));
						if(distance <= 7) {
							if(Math.random() > 0.1D) {
								BlockPos pos1 = new BlockPos(pos.getX(), ent.getLevel().getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, pos.getX(), pos.getZ()) - 1, pos.getZ());
								if(ent.getLevel().getBlockState(pos1).getExplosionResistance(ent.getLevel(), pos, ImprovedExplosion.dummyExplosion(ent.getLevel())) <= 100) {
									ent.getLevel().setBlock(pos1, Blocks.AIR.defaultBlockState(), 3);
								}
							}
						}
						if(distance <= 9 && distance > 7) {
							if(Math.random() > 0.5D) {
								BlockPos pos1 = new BlockPos(pos.getX(), ent.getLevel().getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, pos.getX(), pos.getZ()) - 1, pos.getZ());
								if(ent.getLevel().getBlockState(pos1).getExplosionResistance(ent.getLevel(), pos, ImprovedExplosion.dummyExplosion(ent.getLevel())) <= 100) {
									ent.getLevel().setBlock(pos1, Blocks.AIR.defaultBlockState(), 3);
								}
							}
						}
						if(distance <= 10 && distance > 9) {
							if(Math.random() > 0.9D) {
								BlockPos pos1 = new BlockPos(pos.getX(), ent.getLevel().getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, pos.getX(), pos.getZ()) - 1, pos.getZ());
								if(ent.getLevel().getBlockState(pos1).getExplosionResistance(ent.getLevel(), pos, ImprovedExplosion.dummyExplosion(ent.getLevel())) <= 100) {
									ent.getLevel().setBlock(pos1, Blocks.AIR.defaultBlockState(), 3);
								}
							}
						}
					}
				}
			}
			
			for(double i = 0; i < 60D; i += 1D) {
				for(int offX = -10; offX <= 10; offX++) {
					for(int offZ = -10; offZ <= 10; offZ++) {
						double distance = Math.sqrt(offX * offX + offZ * offZ);
						BlockPos pos = start2.offset(toBlockPos(new Vec3(i * vec2.x + offX, 0, i * vec2.z + offZ)));
						if(distance <= 7) {
							if(Math.random() > 0.1D) {
								BlockPos pos1 = new BlockPos(pos.getX(), ent.getLevel().getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, pos.getX(), pos.getZ()) - 1, pos.getZ());
								if(ent.getLevel().getBlockState(pos1).getExplosionResistance(ent.getLevel(), pos, ImprovedExplosion.dummyExplosion(ent.getLevel())) <= 100) {
									ent.getLevel().setBlock(pos1, Blocks.AIR.defaultBlockState(), 3);
								}
							}
						}
						if(distance <= 9 && distance > 7) {
							if(Math.random() > 0.5D) {
								BlockPos pos1 = new BlockPos(pos.getX(), ent.getLevel().getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, pos.getX(), pos.getZ()) - 1, pos.getZ());
								if(ent.getLevel().getBlockState(pos1).getExplosionResistance(ent.getLevel(), pos, ImprovedExplosion.dummyExplosion(ent.getLevel())) <= 100) {
									ent.getLevel().setBlock(pos1, Blocks.AIR.defaultBlockState(), 3);
								}
							}
						}
						if(distance <= 10 && distance > 9) {
							if(Math.random() > 0.9D) {
								BlockPos pos1 = new BlockPos(pos.getX(), ent.getLevel().getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, pos.getX(), pos.getZ()) - 1, pos.getZ());
								if(ent.getLevel().getBlockState(pos1).getExplosionResistance(ent.getLevel(), pos, ImprovedExplosion.dummyExplosion(ent.getLevel())) <= 100) {
									ent.getLevel().setBlock(pos1, Blocks.AIR.defaultBlockState(), 3);
								}
							}
						}
					}
				}
			}
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.CONTINENTAL_DRIFT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 480;
	}
}
