package luckytnt.tnteffects;

import java.util.List;
import java.util.Random;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class SwapTNTEffect extends PrimedTNTEffect{

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		int[] ids = ((Entity)entity).getPersistentData().getIntArray("entities");
		if(entity.getTNTFuse() < 40 && ids.length == 0) {
			List<Entity> entList = entity.getLevel().getEntities((Entity)entity, new AABB(entity.x() - 70, entity.y() - 70, entity.z() - 70, entity.x() + 70, entity.y() + 70, entity.z() + 70));
			ids = new int[entList.size()];
			entity.setTNTFuse(0);
			for(int i = 0; i < entList.size(); i++) {
				entity.setTNTFuse(entity.getTNTFuse() + 2);
				ids[i] = entList.get(i).getId();
			}
			((Entity)entity).getPersistentData().putIntArray("entities", ids);
		}
		if(ids.length != 0 && entity.getTNTFuse() % 2 == 0) {
			if(((Entity)entity).getPersistentData().getInt("count") < ids.length) {
				Entity ent1 = entity.getLevel().getEntity(ids[((Entity)entity).getPersistentData().getInt("count")]);
				Entity ent2 = entity.getLevel().getEntity(ids[new Random().nextInt(ids.length)]);
				if(ent1 != null && ent2 != null) {
					Vec3 pos1 = ent1.getPosition(1);
					Vec3 pos2 = ent2.getPosition(1);
					
					ent1.setPos(pos2);
					entity.getLevel().playSound(null, toBlockPos(pos2), SoundEvents.ENDERMAN_TELEPORT, SoundSource.MASTER, 2, 1);
					for(int count = 0; count < 40; count++) {
						entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 0f, 1f), 1f), pos2.x + Math.random() * ent1.getBbWidth() - Math.random() * ent1.getBbWidth(), pos2.y + Math.random() * ent1.getBbHeight(), pos2.z + Math.random() * ent1.getBbWidth() - Math.random() * ent1.getBbWidth(), 0, 0, 0);
					}
					
					ent2.setPos(pos1);
					entity.getLevel().playSound(null, toBlockPos(pos1), SoundEvents.ENDERMAN_TELEPORT, SoundSource.MASTER, 2, 1);
					for(int count = 0; count < 40; count++) {
						entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 0f, 1f), 1f), pos1.x + Math.random() * ent2.getBbWidth() - Math.random() * ent2.getBbWidth(), pos1.y + Math.random() * ent2.getBbHeight(), pos1.z + Math.random() * ent2.getBbWidth() - Math.random() * ent2.getBbWidth(), 0, 0, 0);
					}
				}
				((Entity)entity).getPersistentData().putInt("count", ((Entity)entity).getPersistentData().getInt("count") + 1);
			}
			else {
				((Entity)entity).getPersistentData().putInt("fuse", 0);
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
