package luckytnt.tnteffects.projectile;

import luckytnt.registry.ItemRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class DiggingDynamiteEffect extends PrimedTNTEffect{

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Vec3 direction = entity.getPos().subtract(((Entity)entity).xOld, ((Entity)entity).yOld, ((Entity)entity).zOld).normalize();
		explosion: for(float length = 0; length <= 40; length += 0.25f) {
			BlockPos pos = new BlockPos(entity.getPos().add(direction.scale(length)));
			BlockState state = entity.level().getBlockState(pos);
			if(state.getExplosionResistance(entity.level(), pos, ImprovedExplosion.dummyExplosion()) < 100) {
				state.onBlockExploded(entity.level(), pos, ImprovedExplosion.dummyExplosion());
			}
			else {
				break explosion;
			}
		}
	}
	
	@Override
	public Item getItem() {
		return ItemRegistry.DIGGING_DYNAMITE.get();
	}
}
