package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class SensorTNTEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		if (!level.isClientSide()) {
			Player player = entity.getLevel().getNearestPlayer(entity.x(), entity.y(), entity.z(), 10d, p -> p != entity.owner());
			if (player != null) {
				ImprovedExplosion explosion = new ImprovedExplosion(level, entity.getPos(), 10);
				explosion.doEntityExplosion(1f, true);
				explosion.doImprovedBlockExplosion(1f, 1.25f, false, false, null);
				explosion.spawnExplosionParticles();
				playExplosionSound(entity);
				entity.destroy();
			}
		}
	}

	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 0f, 0f), 1f), entity.x(), entity.y() + 1f, entity.z(), 0f, 0f, 0f);
	}

	@Override
	public boolean playsSound() {
		return false;
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.SENSOR_TNT.get();
	}

	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 5000;
	}
}
