package luckytnt.tnteffects;

import java.util.Random;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.ItemRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public class PumpkinBombEffect extends PrimedTNTEffect{

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), 10);
		explosion.doEntityExplosion(1.5f, true);
		explosion.doBlockExplosion(1f, 1f, 1f, 1.25f, false, false);
		Random random = new Random();
		for(int count = 0; count < 30 + random.nextInt(11); count++) {
			ItemEntity candy = new ItemEntity(entity.getLevel(), entity.x(), entity.y(), entity.z(), new ItemStack(ItemRegistry.RED_CANDY.get()));
			candy.setDeltaMovement(Math.random() - 0.5f, Math.random() - 0.5f, Math.random() - 0.5f);
			entity.getLevel().addFreshEntity(candy);
		}
		for(int count = 0; count < 60 + random.nextInt(21); count++) {
			ItemEntity candy = new ItemEntity(entity.getLevel(), entity.x(), entity.y(), entity.z(), new ItemStack(ItemRegistry.GREEN_CANDY.get()));
			candy.setDeltaMovement(Math.random() - 0.5f, Math.random() - 0.5f, Math.random() - 0.5f);
			entity.getLevel().addFreshEntity(candy);
		}
		for(int count = 0; count < 40 + random.nextInt(16); count++) {
			ItemEntity candy = new ItemEntity(entity.getLevel(), entity.x(), entity.y(), entity.z(), new ItemStack(ItemRegistry.BLUE_CANDY.get()));
			candy.setDeltaMovement(Math.random() - 0.5f, Math.random() - 0.5f, Math.random() - 0.5f);
			entity.getLevel().addFreshEntity(candy);
		}
		for(int count = 0; count < 20 + random.nextInt(6); count++) {
			ItemEntity candy = new ItemEntity(entity.getLevel(), entity.x(), entity.y(), entity.z(), new ItemStack(ItemRegistry.PURPLE_CANDY.get()));
			candy.setDeltaMovement(Math.random() - 0.5f, Math.random() - 0.5f, Math.random() - 0.5f);
			entity.getLevel().addFreshEntity(candy);
		}
		for(int count = 0; count < 70 + random.nextInt(31); count++) {
			ItemEntity candy = new ItemEntity(entity.getLevel(), entity.x(), entity.y(), entity.z(), new ItemStack(ItemRegistry.YELLOW_CANDY.get()));
			candy.setDeltaMovement(Math.random() - 0.5f, Math.random() - 0.5f, Math.random() - 0.5f);
			entity.getLevel().addFreshEntity(candy);
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 0.5f, 0f), 1f), entity.x(), entity.y() + 1f, entity.z(), 0f, 0f, 0f);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.PUMPKIN_BOMB.get();
	}
}
