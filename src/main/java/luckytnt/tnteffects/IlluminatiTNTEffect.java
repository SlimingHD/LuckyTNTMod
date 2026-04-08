package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class IlluminatiTNTEffect extends PrimedTNTEffect {

	@SuppressWarnings("deprecation")
	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		Level level = ent.getLevel();
		ImprovedExplosion dummy = ImprovedExplosion.dummyExplosion(level);
		BlockPos centerPos = BlockPos.containing(ent.getPos());
		for (int offY = 60; offY >= -60; offY--) {
			int xzRadius = Mth.ceil(60f * (1f - ((offY + 60f) / 121f)));
			for (int offX = -xzRadius; offX <= xzRadius; offX++) {
				for (int offZ = -xzRadius; offZ <= xzRadius; offZ++) {
					BlockPos pos = centerPos.offset(offX, offY, offZ);
					BlockState state = level.getBlockState(pos);
					if (!state.isAir() && Math.max(state.getBlock().getExplosionResistance(), state.getFluidState().getExplosionResistance()) <= 200f) {
						level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
						state.getBlock().wasExploded(level, pos, dummy);
					}
				}
			}
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		Level level = ent.getLevel();
		
		for (double d = 0d; d <= 1d; d += 0.2d) {
			level.addParticle(ParticleTypes.FLAME, ent.x() - 0.5d + d, ent.y() + 1d, ent.z() - 0.5d, 0d, 0d, 0d);
			level.addParticle(ParticleTypes.FLAME, ent.x() - 0.5d + d, ent.y() + 1d, ent.z() + 0.5d, 0d, 0d, 0d);
			level.addParticle(ParticleTypes.FLAME, ent.x() - 0.5d, ent.y() + 1d, ent.z() - 0.5d + d, 0d, 0d, 0d);
			level.addParticle(ParticleTypes.FLAME, ent.x() + 0.5d, ent.y() + 1d, ent.z() - 0.5d + d, 0d, 0d, 0d);
		}
		
		Vec3 vec1 = new Vec3(0.5d, 1d, 0.5d);
		Vec3 vec2 = new Vec3(-0.5d, 1d, 0.5d);
		Vec3 vec3 = new Vec3(0.5d, 1d, -0.5d);
		Vec3 vec4 = new Vec3(-0.5d, 1d, -0.5d);
		
		for (double d = 0d; d < vec1.length(); d += vec1.length() / 5d) {
			level.addParticle(ParticleTypes.FLAME, ent.x() - 0.5d + vec1.x * d, ent.y() + 1d + vec1.y * d, ent.z() - 0.5d + vec1.z * d, 0d, 0d, 0d);
			level.addParticle(ParticleTypes.FLAME, ent.x() + 0.5d + vec2.x * d, ent.y() + 1d + vec2.y * d, ent.z() - 0.5d + vec2.z * d, 0d, 0d, 0d);
			level.addParticle(ParticleTypes.FLAME, ent.x() - 0.5d + vec3.x * d, ent.y() + 1d + vec3.y * d, ent.z() + 0.5d + vec3.z * d, 0d, 0d, 0d);
			level.addParticle(ParticleTypes.FLAME, ent.x() + 0.5d + vec4.x * d, ent.y() + 1d + vec4.y * d, ent.z() + 0.5d + vec4.z * d, 0d, 0d, 0d);
		}
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 120;
	}
	
	@Override
	public Block getBlock()  {
		return BlockRegistry.ILLUMINATI_TNT.get();
	}
}
