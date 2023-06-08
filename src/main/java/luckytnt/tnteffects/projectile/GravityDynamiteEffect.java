package luckytnt.tnteffects.projectile;

import java.util.List;

import luckytnt.registry.ItemRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class GravityDynamiteEffect extends PrimedTNTEffect{

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		((Entity)entity).setDeltaMovement(((Entity)entity).getDeltaMovement().add(0f, 0.08f, 0f));
		List<Entity> ents = entity.getLevel().getEntities((Entity)entity, new AABB(entity.getPos().add(-10f, -10f, -10f), entity.getPos().add(10f, 10f, 10f)));
		for(Entity ent : ents) {
			if(!ent.equals(entity.owner()) && !(ent instanceof IExplosiveEntity)) {
				Vec3 direction = entity.getPos().subtract(ent.getPosition(1f)).normalize();
				ent.setDeltaMovement(direction.scale(1.5f));
			}
		}
	}
	
	@Override
	public boolean explodesOnImpact() {
		return false;
	}
	
	@Override
	public boolean airFuse() {
		return true;
	}
	
	@Override
	public Item getItem() {
		return ItemRegistry.GRAVITY_DYNAMITE.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 60;
	}
}
