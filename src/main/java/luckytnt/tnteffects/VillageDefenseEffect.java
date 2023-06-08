package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.level.block.Block;

public class VillageDefenseEffect extends PrimedTNTEffect{

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		for(int count = 0; count <= 15; count++) {
			IronGolem golem = new IronGolem(EntityType.IRON_GOLEM, entity.getLevel());
			golem.setPos(entity.getPos());
			golem.setPlayerCreated(true);
			entity.getLevel().addFreshEntity(golem);
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.VILLAGE_DEFENSE.get();
	}
}
