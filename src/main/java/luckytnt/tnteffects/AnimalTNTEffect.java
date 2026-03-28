package luckytnt.tnteffects;

import java.util.List;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.ForgeEventFactory;

public class AnimalTNTEffect extends PrimedTNTEffect {

	private static final List<EntityType<? extends Mob>> ENTITY_TYPES = List.of(EntityType.BAT, EntityType.SPIDER, EntityType.SKELETON,
																				EntityType.ZOMBIE, EntityType.CREEPER, EntityType.PILLAGER,
																				EntityType.VILLAGER, EntityType.ENDERMAN, EntityType.EVOKER,
																				EntityType.IRON_GOLEM, EntityType.WITHER_SKELETON, EntityType.SHEEP,
																				EntityType.COW, EntityType.PIG, EntityType.CHICKEN,
																				EntityType.GIANT, EntityType.AXOLOTL, EntityType.WOLF,
																				EntityType.WITCH, EntityType.SLIME, EntityType.MAGMA_CUBE,
																				EntityType.GUARDIAN, EntityType.ELDER_GUARDIAN, EntityType.CAT,
																				EntityType.STRIDER);
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ServerLevel serverLevel = (ServerLevel)entity.getLevel();
		for (EntityType<? extends Mob> entityType : ENTITY_TYPES) {
			for (int count = 0; count < 2; count++){
				Mob mob = entityType.create(entity.getLevel());
				mob.setPos(entity.getPos());
				ForgeEventFactory.onFinalizeSpawn(mob, (ServerLevel)serverLevel, serverLevel.getCurrentDifficultyAt(toBlockPos(entity.getPos())), MobSpawnType.MOB_SUMMONED, null, null);
				serverLevel.addFreshEntity(mob);
			}
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.ANIMAL_TNT.get();
	}
}
