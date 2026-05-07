package luckytnt.tnteffects;

import java.util.List;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
import luckytnt.registry.keys.AdvancementKeys;
import luckytnt.util.AdvancementHelper;
import luckytntlib.entity.PrimedLTNT;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class CityFireworkEffect extends PrimedTNTEffect {

	private static List<RegistryObject<EntityType<PrimedLTNT>>> ENTITY_TYPES = List.of(EntityRegistry.COBBLESTONE_HOUSE_TNT, EntityRegistry.WOOD_HOUSE_TNT, EntityRegistry.BRICK_HOUSE_TNT, EntityRegistry.MANKINDS_MARK);
	
	@Override
	public void explosionTick(IExplosiveEntity entity) {
		Entity ent = (Entity)entity;
		ent.setDeltaMovement(ent.getDeltaMovement().x, 0.8f, ent.getDeltaMovement().z);
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		RandomSource random = level.getRandom();
		for (int count = 0; count < 50; count++) {
			RegistryObject<EntityType<PrimedLTNT>> type = ENTITY_TYPES.get(random.nextInt(ENTITY_TYPES.size()));
			PrimedLTNT tnt = type.get().create(level);
			tnt.setPos(entity.getPos());
			tnt.setOwner(entity.owner());
			tnt.setDeltaMovement(random.nextDouble() * 3d - 1.5d, random.nextDouble() * 3d - 1.5d, random.nextDouble() * 3d - 1.5d);
			level.addFreshEntity(tnt);
		}
		
		AdvancementHelper.grantAdvancementToOwnerOrNearby(entity, AdvancementKeys.ORGANIZED_CHAOS);
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x(), entity.y(), entity.z(), 0, 0, 0);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.CITY_FIREWORK.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 40;
	}
}
