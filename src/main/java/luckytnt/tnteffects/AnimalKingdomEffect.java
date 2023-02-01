package luckytnt.tnteffects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.animal.Panda;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.animal.horse.Donkey;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.animal.horse.Mule;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.entity.animal.horse.ZombieHorse;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.CaveSpider;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.monster.Evoker;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.Giant;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.monster.Strider;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.monster.Zoglin;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class AnimalKingdomEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		List<Mob> list = new ArrayList<>();
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new Allay(EntityType.ALLAY, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new Axolotl(EntityType.AXOLOTL, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new Bat(EntityType.BAT, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new Bee(EntityType.BEE, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new Blaze(EntityType.BLAZE, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new Cat(EntityType.CAT, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new CaveSpider(EntityType.CAVE_SPIDER, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new Chicken(EntityType.CHICKEN, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new Cow(EntityType.COW, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new Creeper(EntityType.CREEPER, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new Donkey(EntityType.DONKEY, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new Drowned(EntityType.DROWNED, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 2 + new Random().nextInt(2); i++) {
			Mob mob = new ElderGuardian(EntityType.ELDER_GUARDIAN, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new EnderMan(EntityType.ENDERMAN, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new Endermite(EntityType.ENDERMITE, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 2 + new Random().nextInt(2); i++) {
			Mob mob = new Evoker(EntityType.EVOKER, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new Fox(EntityType.FOX, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new Frog(EntityType.FROG, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new Ghast(EntityType.GHAST, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new Giant(EntityType.GIANT, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new Goat(EntityType.GOAT, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new Guardian(EntityType.GUARDIAN, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 2 + new Random().nextInt(2); i++) {
			Mob mob = new Hoglin(EntityType.HOGLIN, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new Horse(EntityType.HORSE, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new Husk(EntityType.HUSK, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new IronGolem(EntityType.IRON_GOLEM, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new Llama(EntityType.LLAMA, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new MagmaCube(EntityType.MAGMA_CUBE, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new MushroomCow(EntityType.MOOSHROOM, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new Mule(EntityType.MULE, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new Ocelot(EntityType.OCELOT, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new Panda(EntityType.PANDA, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new Parrot(EntityType.PARROT, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new Phantom(EntityType.PHANTOM, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new Pig(EntityType.PIG, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new Piglin(EntityType.PIGLIN, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new PiglinBrute(EntityType.PIGLIN_BRUTE, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new Pillager(EntityType.PILLAGER, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new PolarBear(EntityType.POLAR_BEAR, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new Rabbit(EntityType.RABBIT, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 2; i++) {
			Mob mob = new Ravager(EntityType.RAVAGER, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new Sheep(EntityType.SHEEP, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new Shulker(EntityType.SHULKER, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new Silverfish(EntityType.SILVERFISH, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new Skeleton(EntityType.SKELETON, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new SkeletonHorse(EntityType.SKELETON_HORSE, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new Slime(EntityType.SLIME, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new SnowGolem(EntityType.SNOW_GOLEM, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new Spider(EntityType.SPIDER, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new Stray(EntityType.STRAY, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new Strider(EntityType.STRIDER, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new Turtle(EntityType.TURTLE, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new Vex(EntityType.VEX, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new Villager(EntityType.VILLAGER, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new Vindicator(EntityType.VINDICATOR, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new Witch(EntityType.WITCH, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new WitherSkeleton(EntityType.WITHER_SKELETON, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new Wolf(EntityType.WOLF, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new Zoglin(EntityType.ZOGLIN, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new Zombie(EntityType.ZOMBIE, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new ZombieHorse(EntityType.ZOMBIE_HORSE, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new ZombieVillager(EntityType.ZOMBIE_VILLAGER, ent.level());
			list.add(mob);
		}
		for(int i = 0; i < 4 + new Random().nextInt(3); i++) {
			Mob mob = new ZombifiedPiglin(EntityType.ZOMBIFIED_PIGLIN, ent.level());
			list.add(mob);
		}
		
		for(Mob mob : list) {
			int offX = new Random().nextInt(101) - 50;
			int offZ = new Random().nextInt(101) - 50;
			for(int y = ent.level().getMaxBuildHeight(); y > ent.level().getMinBuildHeight(); y--) {
				BlockPos pos = new BlockPos(ent.x() + offX, y, ent.z() + offZ);
				BlockState state = ent.level().getBlockState(pos);
				if(Block.isFaceFull(ent.level().getBlockState(pos.below()).getCollisionShape(ent.level(), pos.below()), Direction.UP) && !Block.isFaceFull(state.getCollisionShape(ent.level(), pos), Direction.UP)) {
					mob.setPos(pos.getX(), pos.getY(), pos.getZ());
					if(ent.level() instanceof ServerLevel sl) {
						mob.finalizeSpawn(sl, ent.level().getCurrentDifficultyAt(pos), MobSpawnType.MOB_SUMMONED, null, null);
					}
					ent.level().addFreshEntity(mob);
					break;
				}
			}
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.ANIMAL_KINGDOM.get();
	}
}
