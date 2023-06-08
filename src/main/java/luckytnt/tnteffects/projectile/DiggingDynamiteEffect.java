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
			BlockPos pos = toBlockPos(entity.getPos().add(direction.scale(length))); 
			BlockState state = entity.getLevel().getBlockState(pos);
			if(state.getExplosionResistance(entity.getLevel(), pos, ImprovedExplosion.dummyExplosion(entity.getLevel())) < 100) {
				state.onBlockExploded(entity.getLevel(), pos, ImprovedExplosion.dummyExplosion(entity.getLevel()));
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
