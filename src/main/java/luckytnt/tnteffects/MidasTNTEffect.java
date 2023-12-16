package luckytnt.tnteffects;

import java.util.List;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EffectRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class MidasTNTEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity ent) {
		if(ent.getTNTFuse() < 80 && ent.getTNTFuse() % 2 == 0 && !ent.getLevel().isClientSide()) {
			ExplosionHelper.doSphericalExplosion(ent.getLevel(), ent.getPos(), ent.getPersistentData().getInt("size"), new IForEachBlockExplosionEffect() {
				
				@Override
				public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
					if(state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(ent.getLevel())) < 100 && !state.isAir() && state.getBlock() != Blocks.GOLD_BLOCK) {
						level.setBlock(pos, Blocks.GOLD_BLOCK.defaultBlockState(), 3);
					}
				}
			});
			
			ent.getPersistentData().putInt("size", ent.getPersistentData().getInt("size") + 1);
			
			int i = ent.getPersistentData().getInt("size");
			BlockPos min = toBlockPos(ent.getPos()).offset(-i, -i, -i);
			BlockPos max = toBlockPos(ent.getPos()).offset(i, i, i);
			List<LivingEntity> list = ent.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(min.getX(), min.getY(), min.getZ(), max.getX(), max.getY(), max.getZ()));
			for(LivingEntity lent : list) {
				lent.addEffect(new MobEffectInstance(EffectRegistry.MIDAS_TOUCH_EFFECT.get(), 2000, 0));
			}
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 1f, 0.4f), 1f), ent.x(), ent.y() + 1f, ent.z(), 0, 0, 0);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.MIDAS_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 160;
	}
}
