package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.keys.AdvancementKeys;
import luckytnt.util.AdvancementHelper;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;

public class TimerTNTEffect extends PrimedTNTEffect {
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion explosion = new ImprovedExplosion((ServerLevel)entity.getLevel(), (Entity) entity, entity.getPos().x, entity.getPos().y + 0.5f, entity.getPos().z, 10);
		explosion.doEntityExplosion((ent, dist) -> {
			if (ent instanceof Player player) {
				AdvancementHelper.grantAdvancementOnePlayer(player, AdvancementKeys.BAD_TIMING);
			}
		});
		explosion.doEntityExplosion(1.5f, true);
		explosion.doImprovedBlockExplosion(1f, 1.25f, false, false, null);
		explosion.spawnExplosionParticles();
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		float r = entity.getTNTFuse() < 400 ? 1f : 2f - 0.0025f * entity.getTNTFuse();
		float g = entity.getTNTFuse() >= 400 ? 1f : 0.0025f * entity.getTNTFuse();
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(r, g, 0), 1f), entity.x(), entity.y() + 1f, entity.z(), 0, 0, 0);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.TIMER_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 800;
	}
}
