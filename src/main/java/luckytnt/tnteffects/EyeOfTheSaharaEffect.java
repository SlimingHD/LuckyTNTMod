package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.event.LevelEvents;
import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
import luckytntlib.entity.PrimedLTNT;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.level.block.Block;

public class EyeOfTheSaharaEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		for (double angle = 0; angle < 360; angle += 6D) {
			PrimedLTNT tnt = EntityRegistry.TNT_X20.get().create(entity.getLevel());
			tnt.setTNTFuse(160);
			tnt.setOwner(entity.owner());
			double x = entity.x() + 80 * Math.cos(angle * Math.PI / 180);
			double z = entity.z() + 80 * Math.sin(angle * Math.PI / 180);
			double y = LevelEvents.getTopBlock(entity.getLevel(), x, z, false);
			tnt.setPos(x, y + 1d, z);
			entity.getLevel().addFreshEntity(tnt);
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		for (double angle = 0; angle < 360; angle += 4D) {
			entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0f, 0f, 0f), 1f), entity.x() + 2d * Math.cos(angle * Math.PI / 180d), entity.y() + 0.5d, entity.z() + 2d * Math.sin(angle * Math.PI / 180d), 0d, 0d, 0d);
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.EYE_OF_THE_SAHARA.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 120;
	}
}
