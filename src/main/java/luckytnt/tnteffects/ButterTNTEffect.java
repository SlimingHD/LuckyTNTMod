package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.rules.BlockExplosionRule;
import luckytntlib.util.explosions.rules.FilterAirExplosionRule;
import luckytntlib.util.explosions.rules.FilterCollidableExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class ButterTNTEffect extends PrimedTNTEffect {
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ExplosionHelper.legacySphericalExplosion(entity.getLevel(), entity.getPos(), 9, 100, new FilterAirExplosionRule(
				new FilterCollidableExplosionRule(new BlockExplosionRule(Blocks.GOLD_BLOCK.defaultBlockState()))
		));
		ImprovedExplosion particleExplosion = new ImprovedExplosion(entity.getLevel(), entity.getPos(), 4);
		particleExplosion.spawnExplosionParticles();
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.BUTTER_TNT.get();
	}
}
