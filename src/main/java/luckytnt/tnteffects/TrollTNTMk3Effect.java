package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.rules.AlwaysExplosionRule;
import luckytntlib.util.explosions.rules.FilterBlockExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.world.level.block.Block;

public class TrollTNTMk3Effect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ExplosionHelper.legacySphericalExplosion(entity.getLevel(), entity.getPos(), 10, 10, FilterBlockExplosionRule.applyOnlyWhen(BlockRegistry.TROLL_TNT_MK3.get(), new AlwaysExplosionRule()));
		ImprovedExplosion particleExplosion = new ImprovedExplosion(entity.getLevel(), entity.getPos(), 4);
		particleExplosion.spawnExplosionParticles();
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.TROLL_TNT_MK3.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 1;
	}
}
