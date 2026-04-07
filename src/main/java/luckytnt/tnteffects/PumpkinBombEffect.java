package luckytnt.tnteffects;

import java.util.Map;

import org.joml.Vector3f;

import com.google.common.collect.ImmutableMap;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.ItemRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class PumpkinBombEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		RandomSource random = level.getRandom();

		ImprovedExplosion explosion = new ImprovedExplosion(level, (Entity) entity, entity.getPos(), 10);
		explosion.doEntityExplosion(1.5f, true);
		explosion.doImprovedBlockExplosion(1f, 1.25f, false, false, null);
		explosion.spawnExplosionParticles();

		Map<Item, Integer> items = ImmutableMap.of(
			ItemRegistry.RED_CANDY.get(), 30 + random.nextInt(11),
			ItemRegistry.GREEN_CANDY.get(), 60 + random.nextInt(21), 
			ItemRegistry.BLUE_CANDY.get(), 40 + random.nextInt(16), 
			ItemRegistry.PURPLE_CANDY.get(), 20 + random.nextInt(6),
			ItemRegistry.YELLOW_CANDY.get(), 70 + random.nextInt(31)
		);

		for (Item item : items.keySet()) {
			for (int i = 0; i < items.get(item); i++) {
				ItemEntity candy = new ItemEntity(level, entity.x(), entity.y(), entity.z(), new ItemStack(item));
				candy.setDeltaMovement(random.nextDouble() - 0.5f, random.nextDouble() - 0.5f, random.nextDouble() - 0.5f);
				level.addFreshEntity(candy);
			}
		}
	}

	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 0.5f, 0f), 1f), entity.x(), entity.y() + 1d, entity.z(), 0d, 0d, 0d);
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.PUMPKIN_BOMB.get();
	}
}
