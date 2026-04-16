package luckytnt.tnteffects.projectile;

import java.util.List;

import org.joml.Vector3f;

import luckytnt.registry.ItemRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class SensorDynamiteEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		if (!level.isClientSide()) {
			List<Player> players = level.getEntitiesOfClass(Player.class, new AABB(entity.getPos().add(-5d, -5d, -5d), entity.getPos().add(5d, 5d, 5d)));
			for (Player player : players) {
				if (!player.equals(entity.owner())) {
					entity.setTNTFuse(0);
				}
			}
		}
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		List<Player> players = level.getEntitiesOfClass(Player.class, new AABB(entity.getPos().add(-5d, -5d, -5d), entity.getPos().add(5d, 5d, 5d)));
		for (Player player : players) {
			if (!player.equals(entity.owner())) {
				ImprovedExplosion explosion = new ImprovedExplosion(level, entity.getPos(), 5);
				explosion.doEntityExplosion(1f, true);
				explosion.doImprovedBlockExplosion(1f, 1.25f, false, false, null);
				explosion.spawnExplosionParticles();
				playExplosionSound(entity);
			}
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 0f, 0f), 1f), entity.x(), entity.y(), entity.z(), 0f, 0f, 0f);
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
		return ItemRegistry.SENSOR_DYNAMITE.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 5000;
	}
}
