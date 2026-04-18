package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class IlluminatiTNTEffect extends PrimedTNTEffect {

	private static final double THETA = (Math.PI * 2d) / 4d;
	private static final Vector3f COLOR = new Vector3f(1f, 0.85f, 0f);
	
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
		
		ImprovedExplosion particleExplosion = new ImprovedExplosion(level, ent.getPos(), 60);
		particleExplosion.spawnExplosionParticles();
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		Level level = entity.getLevel();

		double degrees = (entity.getTNTFuse() / 120d) * 360d * TetrahedronTNTEffect.DEG_TO_RAD;
		Vector3f origin = entity.getPos().toVector3f().add(0f, 1.1f, 0f);
		
		Vector3f a = new Vector3f();
		Vector3f b = new Vector3f();
		Vector3f c = new Vector3f();
		Vector3f d = new Vector3f();
		Vector3f e = new Vector3f();
		
		origin.add(TetrahedronTNTEffect.RADIUS * (float)Math.cos(degrees), 0f, TetrahedronTNTEffect.RADIUS * (float)Math.sin(degrees), a);
		origin.add(TetrahedronTNTEffect.RADIUS * (float)Math.cos(THETA + degrees), 0f, TetrahedronTNTEffect.RADIUS * (float)Math.sin(THETA + degrees), b);
		origin.add(TetrahedronTNTEffect.RADIUS * (float)Math.cos(THETA * 2d + degrees), 0f, TetrahedronTNTEffect.RADIUS * (float)Math.sin(THETA * 2d + degrees), c);
		origin.add(TetrahedronTNTEffect.RADIUS * (float)Math.cos(THETA * 3d + degrees), 0f, TetrahedronTNTEffect.RADIUS * (float)Math.sin(THETA * 3d + degrees), d);
		origin.add(0f, 1f, 0f, e);
		
		Vector3f ab = new Vector3f();
		Vector3f ad = new Vector3f();
		Vector3f ae = new Vector3f();
		Vector3f bc = new Vector3f();
		Vector3f be = new Vector3f();
		Vector3f cd = new Vector3f();
		Vector3f ce = new Vector3f();
		Vector3f de = new Vector3f();
		
		b.sub(a, ab);
		d.sub(a, ad);
		e.sub(a, ae);
		c.sub(b, bc);
		e.sub(b, be);
		d.sub(c, cd);
		e.sub(c, ce);
		e.sub(d, de);
		
		for (float f = 0f; f <= 1f; f += 0.05f) {
			level.addParticle(new DustParticleOptions(COLOR, 0.5f), a.x + f * ab.x, a.y + f * ab.y, a.z + f * ab.z, 0d, 0d, 0d);
			level.addParticle(new DustParticleOptions(COLOR, 0.5f), a.x + f * ad.x, a.y + f * ad.y, a.z + f * ad.z, 0d, 0d, 0d);
			level.addParticle(new DustParticleOptions(COLOR, 0.5f), a.x + f * ae.x, a.y + f * ae.y, a.z + f * ae.z, 0d, 0d, 0d);
			level.addParticle(new DustParticleOptions(COLOR, 0.5f), b.x + f * bc.x, b.y + f * bc.y, b.z + f * bc.z, 0d, 0d, 0d);
			level.addParticle(new DustParticleOptions(COLOR, 0.5f), b.x + f * be.x, b.y + f * be.y, b.z + f * be.z, 0d, 0d, 0d);
			level.addParticle(new DustParticleOptions(COLOR, 0.5f), c.x + f * cd.x, c.y + f * cd.y, c.z + f * cd.z, 0d, 0d, 0d);
			level.addParticle(new DustParticleOptions(COLOR, 0.5f), c.x + f * ce.x, c.y + f * ce.y, c.z + f * ce.z, 0d, 0d, 0d);
			level.addParticle(new DustParticleOptions(COLOR, 0.5f), d.x + f * de.x, d.y + f * de.y, d.z + f * de.z, 0d, 0d, 0d);
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
