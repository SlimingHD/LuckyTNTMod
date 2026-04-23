package luckytnt.tnteffects.projectile;

import java.util.List;

import luckytnt.registry.ItemRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.ForgeEventFactory;

public class AnimalDynamiteEffect extends PrimedTNTEffect {
	
	private static final List<EntityType<?>> ENTITIES = List.of(EntityType.SPIDER, EntityType.SKELETON, EntityType.ZOMBIE,
																EntityType.CREEPER, EntityType.PILLAGER, EntityType.VILLAGER,
																EntityType.ENDERMAN, EntityType.SHEEP, EntityType.COW,
																EntityType.PIG, EntityType.CHICKEN, EntityType.SLIME);

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		for (EntityType<?> entType : ENTITIES) {
			for (int count = 0; count < 2; count++) {
				Entity ent = entType.create(entity.getLevel());
				ent.setPos(entity.getPos());
				if (entity.getLevel() instanceof ServerLevel sLevel && ent instanceof Mob mob) {
					ForgeEventFactory.onFinalizeSpawn(mob, sLevel, entity.getLevel().getCurrentDifficultyAt(toBlockPos(entity.getPos())), MobSpawnType.MOB_SUMMONED, null, null);
				}
				entity.getLevel().addFreshEntity(ent);
			}
		}
	}
	
	@Override
	public Item getItem() {
		return ItemRegistry.ANIMAL_DYNAMITE.get();
	}
}
