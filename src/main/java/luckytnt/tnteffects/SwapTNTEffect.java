package luckytnt.tnteffects;

import java.util.List;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class SwapTNTEffect extends PrimedTNTEffect{

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if (!entity.getLevel().isClientSide() && entity instanceof Entity ent) {
			Level level = entity.getLevel();
			RandomSource random = level.getRandom();
			
			int[] ids = ent.getPersistentData().getIntArray("entities");
			
			if (entity.getTNTFuse() < 40 && ids.length == 0) {
				List<Entity> entList = level.getEntities(ent, new AABB(entity.getPos().add(-70,  -70, -70), entity.getPos().add(70,  70, 70)));
				ids = new int[entList.size()];
				for (int i = 0; i < entList.size(); i++) {
					ids[i] = entList.get(i).getId();
				}
				entity.setTNTFuse(entList.size() * 2);
				ent.getPersistentData().putIntArray("entities", ids);
			}
			
			if (ids.length != 0 && entity.getTNTFuse() % 2 == 0) {
				if (ent.getPersistentData().getInt("count") < ids.length) {
					Entity ent1 = level.getEntity(ids[ent.getPersistentData().getInt("count")]);
					Entity ent2 = level.getEntity(ids[random.nextInt(ids.length)]);
					if (ent1 != null && ent2 != null) {
						Vec3 pos1 = ent1.getPosition(1);
						Vec3 pos2 = ent2.getPosition(1);
						
						ent1.setPos(pos2);
						level.playSound(null, toBlockPos(pos2), SoundEvents.ENDERMAN_TELEPORT, SoundSource.MASTER, 2, 1);
						for (int count = 0; count < 40; count++) {
							level.addParticle(new DustParticleOptions(new Vector3f(1f, 0f, 1f), 1f), pos2.x + random.nextDouble() * ent1.getBbWidth() - random.nextDouble() * ent1.getBbWidth(), pos2.y + random.nextDouble() * ent1.getBbHeight(), pos2.z + random.nextDouble() * ent1.getBbWidth() - random.nextDouble() * ent1.getBbWidth(), 0, 0, 0);
						}
						
						ent2.setPos(pos1);
						level.playSound(null, toBlockPos(pos1), SoundEvents.ENDERMAN_TELEPORT, SoundSource.MASTER, 2, 1);
						for (int count = 0; count < 40; count++) {
							level.addParticle(new DustParticleOptions(new Vector3f(1f, 0f, 1f), 1f), pos1.x + random.nextDouble() * ent2.getBbWidth() - random.nextDouble() * ent2.getBbWidth(), pos1.y + random.nextDouble() * ent2.getBbHeight(), pos1.z + random.nextDouble() * ent2.getBbWidth() - random.nextDouble() * ent2.getBbWidth(), 0, 0, 0);
						}
					}
					ent.getPersistentData().putInt("count", ent.getPersistentData().getInt("count") + 1);
				} else {
					entity.setTNTFuse(0);
				}
			}
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.SWAP_TNT.get();
	}
	 
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 120;
	}
}
