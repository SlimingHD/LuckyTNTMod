package luckytnt.tnteffects.projectile;

import org.joml.Vector3f;

import luckytnt.registry.ItemRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.item.Item;

public class LightningDynamiteEffect extends PrimedTNTEffect{

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		double x = entity.getPos().x;
		double z = entity.getPos().z;
		if (entity.level() instanceof ServerLevel S_Level) {
			double offX = Math.random() * 20 - 10;
			double offZ = Math.random() * 20 - 10;
			for (double offY = 320; offY > -64; offY--) {
				if (!entity.level().getBlockState(new BlockPos(Mth.floor(x + offX), Mth.floor(offY), Mth.floor(z + offZ))).isAir()) {
					Entity lighting = new LightningBolt(EntityType.LIGHTNING_BOLT, entity.level());
					lighting.setPos(x + offX, offY, z + offZ);
					entity.level().addFreshEntity(lighting);
					break;
				}
			}
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.level().addParticle(new DustParticleOptions(new Vector3f(1f, 1f, 0.5f), 1), entity.x(), entity.y(), entity.z(), 0, 0, 0);
	}
	
	@Override
	public boolean explodesOnImpact() {
		return false;
	}
	
	@Override
	public Item getItem() {
		return ItemRegistry.LIGHTNING_DYNAMITE.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 40;
	}
}
