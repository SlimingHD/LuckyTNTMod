package luckytnt.item;

import javax.annotation.Nullable;

import luckytnt.registry.EntityRegistry;
import luckytnt.registry.LuckyTNTTabs;
import luckytntlib.entity.LExplosiveProjectile;
import luckytntlib.item.LDynamiteItem;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class DeathRayRayItem extends LDynamiteItem{

	public DeathRayRayItem() {
		super(new Item.Properties().tab(LuckyTNTTabs.OTHER), EntityRegistry.DEATH_RAY_RAY);
	}
	
	@Override
	public LExplosiveProjectile shoot(Level level, double x, double y, double z, Vec3 direction, float power, @Nullable LivingEntity thrower){
		LExplosiveProjectile dyn = dynamite.get().create(level);
		dyn.setPos(x, y, z);
		dyn.shoot(direction.x, direction.y, direction.z, 4, 0);
		dyn.setOwner(thrower);
		level.addFreshEntity(dyn);
		level.playSound(null, new BlockPos(x, y, z), SoundEvents.GENERIC_EXPLODE, SoundSource.MASTER, 1, 0.5f);
		return dyn;
	}
}
