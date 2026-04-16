package luckytnt.tnteffects;

import java.util.List;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ContinentalDriftEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		Entity ent = (Entity)entity;
		Level level = ent.level();
		RandomSource random = level.getRandom();
		CompoundTag data = entity.getPersistentData();
		if (entity.getTNTFuse() == 420) {
			Vec3 vec = new Vec3(random.nextDouble() * 2d - 1d, 0d, random.nextDouble() * 2d - 1d).normalize();
			data.putDouble("vecx", vec.x);
			data.putDouble("vecz", vec.z);
			vec = new Vec3(random.nextDouble() * 2d - 1d, 0d, random.nextDouble() * 2d - 1d).normalize();
			data.putDouble("vecx2", vec.x);
			data.putDouble("vecz2", vec.z);
			
			data.putInt("x", Mth.floor(entity.x()));
			data.putInt("y", Mth.floor(entity.y()));
			data.putInt("z", Mth.floor(entity.z()));
	      	
			ent.getPersistentData().putInt("second", 30 + random.nextInt(101));
	      	
	      	List<Player> list = level.getEntitiesOfClass(Player.class, new AABB(entity.x() - 200d, entity.y() - 200d, entity.z() - 200d, entity.x() + 200d, entity.y() + 200d, entity.z() + 200d));
	      	for (Player player : list) {
	      		player.getPersistentData().putInt("shakeTime", 420);
	      	}
		}
		
		if (!level.isClientSide() && entity.getTNTFuse() <= 420 && entity.getTNTFuse() % 60 == 0) {
			BlockPos origin = new BlockPos(data.getInt("x"), data.getInt("y"), data.getInt("z"));
			Vec3 vec = new Vec3(data.getDouble("vecx"), 0d, data.getDouble("vecz"));
			BlockPos start = origin.offset(toBlockPos(new Vec3(vec.x * -80d, 0, vec.z * -80d)));
			Vec3 vec2 = new Vec3(data.getDouble("vecx2"), 0d, data.getDouble("vecz2"));
			BlockPos start2 = start.offset(toBlockPos(new Vec3(vec.x * data.getInt("second") + vec2.x * 8d, 0, vec.z * data.getInt("second") + vec2.z * 8d)));
			
			carveRavine(160, vec, start, level);
			carveRavine(60, vec2, start2, level);
		}
	}
	
	private void carveRavine(int vectorLength, Vec3 vec, BlockPos start, Level level) {
		ImprovedExplosion dummyExplosion = ImprovedExplosion.dummyExplosion(level);
		RandomSource random = level.getRandom();
		for (int i = 0; i < vectorLength; i += 1) {
			for (int offX = -10; offX <= 10; offX++) {
				for (int offZ = -10; offZ <= 10; offZ++) {
					int distanceSqr = offX * offX + offZ * offZ;
					BlockPos pos = start.offset(toBlockPos(new Vec3(i * vec.x + offX, 0, i * vec.z + offZ)));
					double rand = random.nextDouble();
					if (shouldBlockExplode(rand, distanceSqr)) {
						BlockPos topPos = new BlockPos(pos.getX(), level.getHeight(Heightmap.Types.MOTION_BLOCKING, pos.getX(), pos.getZ()) - 1, pos.getZ());
						BlockState state = level.getBlockState(topPos);
						if (state.getExplosionResistance(level, pos, dummyExplosion) <= 100) {
							state.getBlock().wasExploded(level, topPos, dummyExplosion);
							level.setBlock(topPos, Blocks.AIR.defaultBlockState(), 3);
						}
					}
				}
			}
		}
	}
	
	private boolean shouldBlockExplode(double rand, int distanceSqr) {
		if ((distanceSqr <= 49 && rand < 0.9d) ||
			(distanceSqr <= 81 && rand < 0.5d) ||
			(distanceSqr <= 100 && rand < 0.1d)) {
			return true;
		}
		return false;
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.CONTINENTAL_DRIFT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 500;
	}
}
