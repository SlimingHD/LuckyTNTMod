package luckytnt.tnteffects;

import java.util.List;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
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

public class EarthquakeTNTEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity entity) {	
		Entity ent = (Entity)entity;
		Level level = ent.level();
		RandomSource random = level.getRandom();
		CompoundTag data = entity.getPersistentData();
		if (entity.getTNTFuse() == 200) {
			Vec3 vec = new Vec3(random.nextDouble() * 2d - 1d, 0d, random.nextDouble() * 2d - 1d).normalize();
			data.putDouble("vecx", vec.x);
			data.putDouble("vecz", vec.z);
			
			data.putDouble("x", entity.x());
	      	data.putDouble("y", entity.y());
	      	data.putDouble("z", entity.z());
	      	
	      	List<Player> list = entity.getLevel().getEntitiesOfClass(Player.class, new AABB(entity.x() - 100, entity.y() - 100, entity.z() - 100, entity.x() + 100, entity.y() + 100, entity.z() + 100));
	      	for (Player player : list) {
	      		player.getPersistentData().putInt("shakeTime", 200);
	      	}
		}
		
		if (entity.getTNTFuse() <= 200 && entity.getTNTFuse() % 20 == 0 && !entity.getLevel().isClientSide()) {
			BlockPos origin = toBlockPos(new Vec3(data.getDouble("x"), data.getDouble("y"), data.getDouble("z")));
			BlockPos start = origin.offset(toBlockPos(new Vec3(data.getDouble("vecx") * -40d, 0, data.getDouble("vecz") * -40d)));
			Vec3 vec = new Vec3(data.getDouble("vecx"), 0, data.getDouble("vecz"));
			carveRavine(80, vec, start, entity.getLevel());
		}
	}
	
	private void carveRavine(int vectorLength, Vec3 vec, BlockPos start, Level level) {
		ImprovedExplosion dummyExplosion = ImprovedExplosion.dummyExplosion(level);
		RandomSource random = level.getRandom();
		for (int i = 0; i < vectorLength; i += 1) {
			for (int offX = -6; offX <= 6; offX++) {
				for (int offZ = -6; offZ <= 6; offZ++) {
					int distanceSqr = offX * offX + offZ * offZ;
					BlockPos pos = start.offset(toBlockPos(new Vec3(i * vec.x + offX, 0, i * vec.z + offZ)));
					double rand = random.nextDouble();
					if (shouldBlockExplode(rand, distanceSqr)) {
						BlockPos topPos = new BlockPos(pos.getX(), level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, pos.getX(), pos.getZ()) - 1, pos.getZ());
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
		if ((distanceSqr <= 9 && rand < 0.9d) ||
			(distanceSqr <= 25 && rand < 0.5d) ||
			(distanceSqr <= 36 && rand < 0.1d)) {
			return true;
		}
		return false;
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
