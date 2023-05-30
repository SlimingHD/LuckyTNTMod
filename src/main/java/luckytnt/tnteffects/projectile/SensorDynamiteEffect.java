package luckytnt.tnteffects.projectile;

import java.util.List;

import com.mojang.math.Vector3f;

import luckytnt.registry.ItemRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class SensorDynamiteEffect extends PrimedTNTEffect{

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		Level level = entity.level();
		if(level instanceof ServerLevel) {
			List<Player> players = level.getEntitiesOfClass(Player.class, new AABB(entity.getPos().add(-5f, -5f, -5f), entity.getPos().add(5f, 5f, 5f)));
			for(Player player : players) {
				if(!player.equals(entity.owner())) {
					ImprovedExplosion explosion = new ImprovedExplosion(level, entity.getPos(), 5f);
					explosion.doEntityExplosion(1f, true);
					explosion.doBlockExplosion(1f, 1f, 1f, 1.25f, false, false);
					level.playSound((Entity)entity, new BlockPos(entity.getPos()), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 4f, (1f + (level.random.nextFloat() - level.random.nextFloat()) * 0.2f) * 0.7f);
					entity.destroy();
				}
			}
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.level().addParticle(new DustParticleOptions(new Vector3f(1f, 0f, 0f), 1f), entity.x(), entity.y(), entity.z(), 0f, 0f, 0f);
	}
	
	@Override
	public boolean explodesOnImpact() {
		return false;
	}
	
	@Override
	public boolean playsSound() {
		return false;
	}
	
	@Override
	public Item getItem() {
		return ItemRegistry.SENSOR_DYNAMITE.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 5000;
	}
}
