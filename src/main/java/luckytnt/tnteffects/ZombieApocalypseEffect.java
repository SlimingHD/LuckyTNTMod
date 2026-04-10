package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.horse.ZombieHorse;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class ZombieApocalypseEffect extends PrimedTNTEffect {
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		RandomSource random = level.getRandom();
		
		for (int count = 0; count <= 30 + random.nextInt(16); count++) {
			Zombie zombie = new Zombie(EntityType.ZOMBIE, level);
			zombie.setPos(entity.getPos());
			level.addFreshEntity(zombie);
		}
		for (int count = 0; count <= 10 + random.nextInt(6); count++) {
			ZombieHorse zombie = new ZombieHorse(EntityType.ZOMBIE_HORSE, level);
			zombie.setPos(entity.getPos());
			level.addFreshEntity(zombie);
		}
		for (int count = 0; count <= 15 + random.nextInt(11); count++) {
			ZombieVillager zombie = new ZombieVillager(EntityType.ZOMBIE_VILLAGER, level);
			zombie.setPos(entity.getPos());
			level.addFreshEntity(zombie);
		}
		
		if (level instanceof ServerLevel server) {
			server.setDayTime(18000);
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0f, 0.7f, 0f), 1f), entity.x(), entity.y() + 1d, entity.z(), 0d, 0d, 0d);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.ZOMBIE_APOCALYPSE.get();
	}
}
