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
import net.minecraft.world.phys.Vec3;

public class GraveyardTNTEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		for(int offX = -20; offX <= 20; offX++) {
			for(int offY = 0; offY <= 10; offY++) {
				for(int offZ = -20; offZ <= 20; offZ++) {
					double distance = Math.sqrt(offX * offX + offY * offY + offZ * offZ);
					BlockPos pos = toBlockPos(new Vec3(entity.x() + offX, entity.y() + offY - 10, entity.z() + offZ));
					if(distance <= 20 && entity.getLevel().getBlockState(pos).getExplosionResistance(entity.getLevel(), pos, ImprovedExplosion.dummyExplosion(entity.getLevel())) <= 100 && !entity.getLevel().getBlockState(pos).isCollisionShapeFullBlock(entity.getLevel(), pos)) {
						entity.getLevel().getBlockState(pos).onBlockExploded(entity.getLevel(), pos, ImprovedExplosion.dummyExplosion(entity.getLevel()));
						entity.getLevel().setBlockAndUpdate(pos, Blocks.GRASS_BLOCK.defaultBlockState());
					}
				}
			}
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		for(int count = 0; count <= 20; count++) {
			entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.5f, 0.2f, 0f), 0.75f), entity.x(), entity.y() + 1D + 0.05D * count, entity.z(), 0, 0, 0);
			entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.5f, 0.2f, 0f), 0.75f), entity.x() - 0.5D + count * 0.05D, entity.y() + 1D + (2D / 3D), entity.z(), 0, 0, 0);
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.GRAVEYARD_TNT.get();
	}
}
