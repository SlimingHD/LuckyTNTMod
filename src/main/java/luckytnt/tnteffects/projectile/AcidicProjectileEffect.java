package luckytnt.tnteffects.projectile;

import java.util.List;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;

public class AcidicProjectileEffect extends ChemicalProjectileEffect {
	
	public AcidicProjectileEffect() {
		super(7, 5);
	}
	
	@Override
	public void explosionTick(IExplosiveEntity entity) {
		super.explosionTick(entity);
		if(entity.getTNTFuse() % 20 == 0) {
			List<LivingEntity> list = entity.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(toBlockPos(entity.getPos()).offset(-3, -3, -3), toBlockPos(entity.getPos()).offset(3, 3, 3)));			
			DamageSources sources = entity.getLevel().damageSources();
			for(LivingEntity lent : list) {
				lent.hurt(sources.magic(), 3f);
			}
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 1f, 1f), 1f), ent.x() + 0.2f, ent.y() + 1f, ent.z(), 0, 0, 0);
		ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 1f, 1f), 1f), ent.x() - 0.2f, ent.y() + 1f, ent.z(), 0, 0, 0);
		ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 0f, 0f), 1f), ent.x(), ent.y() + 1f, ent.z() + 0.2f, 0, 0, 0);
		ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 0f, 0f), 1f), ent.x(), ent.y() + 1f, ent.z() - 0.2f, 0, 0, 0);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.ACIDIC_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 120;
	}
}
