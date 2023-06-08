package luckytnt.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;

public class SnowySnowball extends Snowball {

	public SnowySnowball(EntityType<? extends Snowball> type, Level level) {
		super(type, level);
	}
	
	public SnowySnowball(Level level, LivingEntity type) {
		super(level, type);
	}
	
	public SnowySnowball(Level level, double x, double y, double z) {
		super(level, x, y, z);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onHitBlock(BlockHitResult result) {
		super.onHitBlock(result);
		BlockPos pos = result.getBlockPos();
		if(Blocks.SNOW.canSurvive(level.getBlockState(pos.above()), level, pos.above()) && level.getBlockState(pos.above()).getBlock().getExplosionResistance() < 100 && level.getBlockState(pos.above()).getMaterial() != Material.WATER_PLANT && level.getBlockState(pos.above()).getMaterial() != Material.REPLACEABLE_WATER_PLANT) {
			level.setBlock(pos.above(), Blocks.SNOW.defaultBlockState(), 3);
		}
	}
}
