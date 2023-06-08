package luckytnt.tnteffects.projectile;

import luckytnt.registry.ItemRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class TunnelingDynamiteEffect extends PrimedTNTEffect{

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Vec3 direction = entity.getPos().subtract(((Entity)entity).xOld, ((Entity)entity).yOld, ((Entity)entity).zOld).normalize();
		for(float length = 0; length <= 40; length += 1f) {
			BlockPos pos = toBlockPos(entity.getPos().add(direction.scale(length)));
			ExplosionHelper.doSphericalExplosion(entity.getLevel(), new Vec3(pos.getX(), pos.getY(), pos.getZ()), 4, new IForEachBlockExplosionEffect() {
				
				@Override
				public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
					if(distance < 4) {
						if(state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(entity.getLevel())) < 100) {
							state.onBlockExploded(level, pos, ImprovedExplosion.dummyExplosion(entity.getLevel()));
						}
					}
				}
			});

		}
	}
	
	@Override
	public Item getItem() {
		return ItemRegistry.TUNNELING_DYNAMITE.get();
	}
}
