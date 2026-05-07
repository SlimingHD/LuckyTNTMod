package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.keys.AdvancementKeys;
import luckytnt.util.AdvancementHelper;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.rules.AlwaysExplosionRule;
import luckytntlib.util.explosions.rules.BlockExplosionRule;
import luckytntlib.util.explosions.rules.FilterAirExplosionRule;
import luckytntlib.util.explosions.rules.FilterBlockExplosionRule;
import luckytntlib.util.explosions.rules.LogicExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;

public class XRayTNTEffect extends PrimedTNTEffect {

	private final int radius;
	
	public XRayTNTEffect(int radius) {
		this.radius = radius;
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ExplosionHelper.createSphericalCrater(entity.getLevel(), entity.getPos(), radius, 100f, new FilterAirExplosionRule(
			LogicExplosionRule.not(
				FilterBlockExplosionRule.applyOnlyWhen(Tags.Blocks.ORES, new AlwaysExplosionRule()), 
				new BlockExplosionRule(Blocks.GLASS.defaultBlockState())	
			)
		));
		AdvancementHelper.grantAdvancementToOwnerOrNearby(entity, AdvancementKeys.I_CAN_SEE_CLEARLY_NOW);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.XRAY_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 160;
	}
}
