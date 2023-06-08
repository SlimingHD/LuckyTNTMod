package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
import luckytntlib.entity.PrimedLTNT;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.Block;

public class AsteroidBeltEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		for(double angle = 0; angle < 360; angle += 12D) {
			PrimedLTNT tnt = EntityRegistry.GLOBAL_DISASTER.get().create(ent.getLevel());
			tnt.setTNTFuse(100);
			tnt.setOwner(ent.owner());
			double x = ent.x() + 160 * Math.cos(angle * Math.PI / 180);
			double z = ent.z() + 160 * Math.sin(angle * Math.PI / 180);
			double y = RingTNTEffect.getFirstMotionBlockingBlock(ent.getLevel(), x, z);
			tnt.setPos(x, y + 1D, z);
			ent.getLevel().addFreshEntity(tnt);
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		for(double angle = 0; angle < 360; angle += 4D) {
			ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x() + 2 * Math.cos(angle * Math.PI / 180), ent.y() + 0.5d, ent.z() + 2 * Math.sin(angle * Math.PI / 180), 0d, 0d, 0d);
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.ASTEROID_BELT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 160;
	}
}
