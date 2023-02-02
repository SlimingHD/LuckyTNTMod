package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class GraveyardTNTEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		for(int offX = -20; offX <= 20; offX++) {
			for(int offY = 0; offY <= 10; offY++) {
				for(int offZ = -20; offZ <= 20; offZ++) {
					double distance = Math.sqrt(offX * offX + offY * offY + offZ * offZ);
					BlockPos pos = new BlockPos(entity.x() + offX, entity.y() + offY - 10, entity.z() + offZ);
					if(distance <= 20 && entity.level().getBlockState(pos).getExplosionResistance(entity.level(), pos, ImprovedExplosion.dummyExplosion()) <= 100 && !entity.level().getBlockState(pos).isCollisionShapeFullBlock(entity.level(), pos)) {
						entity.level().getBlockState(pos).onBlockExploded(entity.level(), pos, ImprovedExplosion.dummyExplosion());
						entity.level().setBlockAndUpdate(pos, Blocks.GRASS_BLOCK.defaultBlockState());
					}
				}
			}
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		for(int count = 0; count <= 20; count++) {
			entity.level().addParticle(new DustParticleOptions(new Vector3f(0.5f, 0.2f, 0f), 0.75f), entity.x(), entity.y() + 1D + 0.05D * count, entity.z(), 0, 0, 0);
			entity.level().addParticle(new DustParticleOptions(new Vector3f(0.5f, 0.2f, 0f), 0.75f), entity.x() - 0.5D + count * 0.05D, entity.y() + 1D + (2D / 3D), entity.z(), 0, 0, 0);
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.GRAVEYARD_TNT.get();
	}
}
