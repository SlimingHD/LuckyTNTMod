package luckytnt.tnteffects;

import java.util.ArrayList;
import java.util.List;

import luckytnt.event.LevelEvents;
import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.registries.RegistryManager;

public class AnimalKingdomEffect extends PrimedTNTEffect {
	
	private static final List<EntityType<?>> ENTITY_TYPES = new ArrayList<EntityType<?>>();
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ServerLevel serverLevel = (ServerLevel)entity.getLevel();
		if (ENTITY_TYPES.isEmpty()) {
			RegistryManager.FROZEN.getRegistry(Registries.ENTITY_TYPE).forEach(entityType -> {
				Entity ent = entityType.create(serverLevel);
				if (ent instanceof Mob mob && mob.getMaxHealth() < 180) {
					ENTITY_TYPES.add(entityType);
				}
			});
		}
		RandomSource random = serverLevel.getRandom();
		List<Mob> mobList = new ArrayList<>();
		for (EntityType<?> entityType : ENTITY_TYPES) {
			int rand = 3 + random.nextInt(3);
			for (int i = 0; i < rand; i++) {
				mobList.add((Mob)entityType.create(serverLevel));
			}
		}
		for (Mob mob : mobList) {
			int x = random.nextInt(101) - 50 + Mth.floor(entity.x());
			int z = random.nextInt(101) - 50 + Mth.floor(entity.z());
			BlockPos pos = new BlockPos(x, LevelEvents.getTopBlock(serverLevel, x, z, false), z);
			mob.setPos(Vec3.atBottomCenterOf(pos));
			ForgeEventFactory.onFinalizeSpawn(mob, (ServerLevel)serverLevel, serverLevel.getCurrentDifficultyAt(pos), MobSpawnType.MOB_SUMMONED, null, null);
			serverLevel.addFreshEntity(mob);
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.ANIMAL_KINGDOM.get();
	}
}
