package luckytnt.tnteffects.projectile;

import luckytnt.registry.ItemRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.rules.CraterExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class TunnelingDynamiteEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Entity ent = (Entity)entity;
		Vec3 direction = entity.getPos().subtract(ent.getPosition(0f)).normalize();
		ImprovedExplosion dummyExplosion = ImprovedExplosion.dummyExplosion(entity.getLevel());
		float vectorLength = 120f;
		for (float step = 0; step <= vectorLength; step += 1f) {
			BlockPos pos = toBlockPos(entity.getPos().add(direction.scale(step)));
			BlockState state = entity.getLevel().getBlockState(pos);
			vectorLength -= state.getExplosionResistance(entity.getLevel(), pos, dummyExplosion);
			if (step > vectorLength) {
				break;
			}
			ExplosionHelper.legacySphericalExplosion(entity.getLevel(), pos.getCenter(), 4, 100, new CraterExplosionRule());
		}
	}
	
	@Override
	public Item getItem() {
		return ItemRegistry.TUNNELING_DYNAMITE.get();
	}
}
