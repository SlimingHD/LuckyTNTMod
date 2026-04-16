package luckytnt.tnteffects.projectile;

import luckytnt.registry.ItemRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class DiggingDynamiteEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion particleExplosion = new ImprovedExplosion(entity.getLevel(), entity.getPos(), 2);
		particleExplosion.spawnExplosionParticles();
		Vec3 direction = entity.getPos().subtract(((Entity)entity).xOld, ((Entity)entity).yOld, ((Entity)entity).zOld).normalize();
		float vectorLength = 480f;
		BlockPos lastPos = null;
		for (float step = 0; step <= vectorLength; step += 0.225f) {
			BlockPos pos = toBlockPos(entity.getPos().add(direction.scale(step)));
			if (pos == lastPos) {
				continue;
			}
			lastPos = pos;
			BlockState state = entity.getLevel().getBlockState(pos);
			vectorLength -= state.getExplosionResistance(entity.getLevel(), pos, particleExplosion);
			if (step > vectorLength) {
				break;
			}
			state.getBlock().wasExploded(entity.getLevel(), pos, particleExplosion);
			entity.getLevel().setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
		}
	}
	
	@Override
	public Item getItem() {
		return ItemRegistry.DIGGING_DYNAMITE.get();
	}
}
