package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.npc.*;
import net.minecraft.world.level.block.Block;

public class AnimalTNTEffect extends PrimedTNTEffect{

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		for(int count = 0; count < 2; count++) {
			Entity ent = new Bat(EntityType.BAT, entity.level());
			ent.setPos(entity.getPos());
			entity.level().addFreshEntity(ent);
		}
		for(int count = 0; count < 2; count++) {
			Entity ent = new Spider(EntityType.SPIDER, entity.level());
			ent.setPos(entity.getPos());
			if(entity.level() instanceof ServerLevel S_Level) {
				((Mob)ent).finalizeSpawn(S_Level, entity.level().getCurrentDifficultyAt(new BlockPos(entity.getPos())), MobSpawnType.MOB_SUMMONED, null, null);
			}
			entity.level().addFreshEntity(ent);
		}
		for(int count = 0; count < 2; count++) {
			Entity ent = new Skeleton(EntityType.SKELETON, entity.level());
			ent.setPos(entity.getPos());
			if(entity.level() instanceof ServerLevel S_Level) {
				((Mob)ent).finalizeSpawn(S_Level, entity.level().getCurrentDifficultyAt(new BlockPos(entity.getPos())), MobSpawnType.MOB_SUMMONED, null, null);
			}
			entity.level().addFreshEntity(ent);
		}
		for(int count = 0; count < 2; count++) {
			Entity ent = new Zombie(EntityType.ZOMBIE, entity.level());
			ent.setPos(entity.getPos());
			if(entity.level() instanceof ServerLevel S_Level) {
				((Mob)ent).finalizeSpawn(S_Level, entity.level().getCurrentDifficultyAt(new BlockPos(entity.getPos())), MobSpawnType.MOB_SUMMONED, null, null);
			}
			entity.level().addFreshEntity(ent);
		}
		for(int count = 0; count < 2; count++) {
			Entity ent = new Creeper(EntityType.CREEPER, entity.level());
			ent.setPos(entity.getPos());
			if(entity.level() instanceof ServerLevel S_Level) {
				((Mob)ent).finalizeSpawn(S_Level, entity.level().getCurrentDifficultyAt(new BlockPos(entity.getPos())), MobSpawnType.MOB_SUMMONED, null, null);
			}
			entity.level().addFreshEntity(ent);
		}
		for(int count = 0; count < 2; count++) {
			Entity ent = new Pillager(EntityType.PILLAGER, entity.level());
			ent.setPos(entity.getPos());
			if(entity.level() instanceof ServerLevel S_Level) {
				((Mob)ent).finalizeSpawn(S_Level, entity.level().getCurrentDifficultyAt(new BlockPos(entity.getPos())), MobSpawnType.MOB_SUMMONED, null, null);
			}
			entity.level().addFreshEntity(ent);
		}
		for(int count = 0; count < 2; count++) {
			Entity ent = new Villager(EntityType.VILLAGER, entity.level());
			ent.setPos(entity.getPos());
			entity.level().addFreshEntity(ent);
		}
		for(int count = 0; count < 2; count++) {
			Entity ent = new EnderMan(EntityType.ENDERMAN, entity.level());
			ent.setPos(entity.getPos());
			if(entity.level() instanceof ServerLevel S_Level) {
				((Mob)ent).finalizeSpawn(S_Level, entity.level().getCurrentDifficultyAt(new BlockPos(entity.getPos())), MobSpawnType.MOB_SUMMONED, null, null);
			}
			entity.level().addFreshEntity(ent);
		}
		for(int count = 0; count < 2; count++) {
			Entity ent = new Evoker(EntityType.EVOKER, entity.level());
			ent.setPos(entity.getPos());
			entity.level().addFreshEntity(ent);
		}
		for(int count = 0; count < 2; count++) {
			Entity ent = new IronGolem(EntityType.IRON_GOLEM, entity.level());
			ent.setPos(entity.getPos());
			entity.level().addFreshEntity(ent);
		}
		for(int count = 0; count < 2; count++) {
			Entity ent = new WitherSkeleton(EntityType.WITHER_SKELETON, entity.level());
			ent.setPos(entity.getPos());
			if(entity.level() instanceof ServerLevel S_Level) {
				((Monster)ent).finalizeSpawn(S_Level, entity.level().getCurrentDifficultyAt(new BlockPos(entity.getPos())), MobSpawnType.MOB_SUMMONED, null, null);
			}
			entity.level().addFreshEntity(ent);
		}
		for(int count = 0; count < 2; count++) {
			Entity ent = new Sheep(EntityType.SHEEP, entity.level());
			ent.setPos(entity.getPos());
			entity.level().addFreshEntity(ent);
		}
		for(int count = 0; count < 2; count++) {
			Entity ent = new Cow(EntityType.COW, entity.level());
			ent.setPos(entity.getPos());
			entity.level().addFreshEntity(ent);
		}
		for(int count = 0; count < 2; count++) {
			Entity ent = new Pig(EntityType.PIG, entity.level());
			ent.setPos(entity.getPos());
			entity.level().addFreshEntity(ent);
		}
		for(int count = 0; count < 2; count++) {
			Entity ent = new Chicken(EntityType.CHICKEN, entity.level());
			ent.setPos(entity.getPos());
			entity.level().addFreshEntity(ent);
		}
		for(int count = 0; count < 2; count++) {
			Entity ent = new Giant(EntityType.GIANT, entity.level());
			ent.setPos(entity.getPos());
			entity.level().addFreshEntity(ent);
		}
		for(int count = 0; count < 2; count++) {
			Entity ent = new Axolotl(EntityType.AXOLOTL, entity.level());
			ent.setPos(entity.getPos());
			entity.level().addFreshEntity(ent);
		}
		for(int count = 0; count < 2; count++) {
			Entity ent = new Wolf(EntityType.WOLF, entity.level());
			ent.setPos(entity.getPos());
			entity.level().addFreshEntity(ent);
		}
		for(int count = 0; count < 2; count++) {
			Entity ent = new Witch(EntityType.WITCH, entity.level());
			ent.setPos(entity.getPos());
			if(entity.level() instanceof ServerLevel S_Level) {
				((Mob)ent).finalizeSpawn(S_Level, entity.level().getCurrentDifficultyAt(new BlockPos(entity.getPos())), MobSpawnType.MOB_SUMMONED, null, null);
			}
			entity.level().addFreshEntity(ent);
		}
		for(int count = 0; count < 2; count++) {
			Entity ent = new Slime(EntityType.SLIME, entity.level());
			ent.setPos(entity.getPos());
			if(entity.level() instanceof ServerLevel S_Level) {
				((Mob)ent).finalizeSpawn(S_Level, entity.level().getCurrentDifficultyAt(new BlockPos(entity.getPos())), MobSpawnType.MOB_SUMMONED, null, null);
			}
			entity.level().addFreshEntity(ent);
		}
		for(int count = 0; count < 2; count++) {
			Entity ent = new MagmaCube(EntityType.MAGMA_CUBE, entity.level());
			ent.setPos(entity.getPos());
			if(entity.level() instanceof ServerLevel S_Level) {
				((Mob)ent).finalizeSpawn(S_Level, entity.level().getCurrentDifficultyAt(new BlockPos(entity.getPos())), MobSpawnType.MOB_SUMMONED, null, null);
			}
			entity.level().addFreshEntity(ent);
		}
		for(int count = 0; count < 2; count++) {
			Entity ent = new Guardian(EntityType.GUARDIAN, entity.level());
			ent.setPos(entity.getPos());
			entity.level().addFreshEntity(ent);
		}
		for(int count = 0; count < 2; count++) {
			Entity ent = new ElderGuardian(EntityType.ELDER_GUARDIAN, entity.level());
			ent.setPos(entity.getPos());
			entity.level().addFreshEntity(ent);
		}
		for(int count = 0; count < 2; count++) {
			Entity ent = new Cat(EntityType.CAT, entity.level());
			ent.setPos(entity.getPos());
			entity.level().addFreshEntity(ent);
		}
		for(int count = 0; count < 2; count++) {
			Entity ent = new Strider(EntityType.STRIDER, entity.level());
			ent.setPos(entity.getPos());
			entity.level().addFreshEntity(ent);
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.ANIMAL_TNT.get();
	}
}
