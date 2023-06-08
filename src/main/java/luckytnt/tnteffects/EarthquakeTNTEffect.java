package luckytnt.tnteffects;

import java.util.List;
import java.util.Random;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class EarthquakeTNTEffect extends PrimedTNTEffect{

	@Override
	public void explosionTick(IExplosiveEntity entity) {	
		if(entity.getTNTFuse() == 200) {
			double vecx = Math.random() * new Random().nextInt(11);
			double vecz = Math.random() * new Random().nextInt(11);
			vecx *= new Random().nextBoolean() ? 1D : -1D;
			vecz *= new Random().nextBoolean() ? 1D : -1D;
			Vec3 vec = new Vec3(vecx, 0, vecz).normalize();
			entity.getPersistentData().putDouble("vecx", vec.x);
			entity.getPersistentData().putDouble("vecz", vec.z);
			
			entity.getPersistentData().putDouble("x", entity.x());
	      	entity.getPersistentData().putDouble("y", entity.y());
	      	entity.getPersistentData().putDouble("z", entity.z());
	      	
	      	List<Player> list = entity.getLevel().getEntitiesOfClass(Player.class, new AABB(entity.x() - 100, entity.y() - 100, entity.z() - 100, entity.x() + 100, entity.y() + 100, entity.z() + 100));
	      	for(Player player : list) {
	      		player.getPersistentData().putInt("shakeTime", 200);
	      	}
		}
		
		if(entity.getTNTFuse() <= 200 && entity.getTNTFuse() % 20 == 0 && !entity.getLevel().isClientSide()) {
			BlockPos origin = toBlockPos(new Vec3(entity.getPersistentData().getDouble("x"), entity.getPersistentData().getDouble("y"), entity.getPersistentData().getDouble("z")));
			BlockPos start = origin.offset(toBlockPos(new Vec3(entity.getPersistentData().getDouble("vecx") * -40, 0, entity.getPersistentData().getDouble("vecz") * -40)));
			Vec3 vec = new Vec3(entity.getPersistentData().getDouble("vecx"), 0, entity.getPersistentData().getDouble("vecz"));
			
			for(double i = 0; i < 80D; i += 1D) {
				for(int offX = -6; offX <= 6; offX++) {
					for(int offZ = -6; offZ <= 6; offZ++) {
						double distance = Math.sqrt(offX * offX + offZ * offZ);
						BlockPos pos = start.offset(toBlockPos(new Vec3(i * vec.x + offX, 0, i * vec.z + offZ)));
						if(distance <= 3) {
							if(Math.random() > 0.1D) {
								BlockPos pos1 = new BlockPos(pos.getX(), entity.getLevel().getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, pos.getX(), pos.getZ()) - 1, pos.getZ());
								if(entity.getLevel().getBlockState(pos1).getExplosionResistance(entity.getLevel(), pos, ImprovedExplosion.dummyExplosion(entity.getLevel())) <= 100) {
									entity.getLevel().setBlock(pos1, Blocks.AIR.defaultBlockState(), 3);
								}
							}
						}
						if(distance <= 5 && distance > 3) {
							if(Math.random() > 0.5D) {
								BlockPos pos1 = new BlockPos(pos.getX(), entity.getLevel().getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, pos.getX(), pos.getZ()) - 1, pos.getZ());
								if(entity.getLevel().getBlockState(pos1).getExplosionResistance(entity.getLevel(), pos, ImprovedExplosion.dummyExplosion(entity.getLevel())) <= 100) {
									entity.getLevel().setBlock(pos1, Blocks.AIR.defaultBlockState(), 3);
								}
							}
						}
						if(distance <= 6 && distance > 5) {
							if(Math.random() > 0.9D) {
								BlockPos pos1 = new BlockPos(pos.getX(), entity.getLevel().getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, pos.getX(), pos.getZ()) - 1, pos.getZ());
								if(entity.getLevel().getBlockState(pos1).getExplosionResistance(entity.getLevel(), pos, ImprovedExplosion.dummyExplosion(entity.getLevel())) <= 100) {
									entity.getLevel().setBlock(pos1, Blocks.AIR.defaultBlockState(), 3);
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
		return BlockRegistry.EARTHQUAKE_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 280;
	}
}
