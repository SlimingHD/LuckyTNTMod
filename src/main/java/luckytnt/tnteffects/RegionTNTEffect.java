package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class RegionTNTEffect extends CubicTNTEffect {

	private static final float RADIUS = (float)Math.sqrt(18d) * 0.5f;
	private static final double THETA = (Math.PI * 2d) / 4d;
	
	public RegionTNTEffect() {
		super(250, 100000f);
	}
	
	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if (entity.getTNTFuse() == 400) {
			Entity lighting = new LightningBolt(EntityType.LIGHTNING_BOLT, entity.getLevel());
			lighting.setPos(entity.x(), entity.y(), entity.z());
			entity.getLevel().addFreshEntity(lighting);
			ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), 5);
			explosion.doEntityExplosion(2f, true);
			explosion.doImprovedBlockExplosion(1f, 1f, false, false, null);
			explosion.spawnExplosionParticles();
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		Level level = entity.getLevel();

		double degrees = (entity.getTNTFuse() / 400d) * 720d * TetrahedronTNTEffect.DEG_TO_RAD;
		Vector3f origin = entity.getPos().toVector3f().add(0f, 0.5f, 0f);
		
		Vector3f a = new Vector3f();
		Vector3f b = new Vector3f();
		Vector3f c = new Vector3f();
		Vector3f d = new Vector3f();
		Vector3f e = new Vector3f();
		Vector3f f = new Vector3f();
		Vector3f g = new Vector3f();
		Vector3f h = new Vector3f();
		
		origin.add(RADIUS * (float)Math.cos(degrees), -1.5f, RADIUS * (float)Math.sin(degrees), a);
		origin.add(RADIUS * (float)Math.cos(THETA + degrees), -1.5f, RADIUS * (float)Math.sin(THETA + degrees), b);
		origin.add(RADIUS * (float)Math.cos(THETA * 2d + degrees), -1.5f, RADIUS * (float)Math.sin(THETA * 2d + degrees), c);
		origin.add(RADIUS * (float)Math.cos(THETA * 3d + degrees), -1.5f, RADIUS * (float)Math.sin(THETA * 3d + degrees), d);
		origin.add(RADIUS * (float)Math.cos(degrees), 1.5f, RADIUS * (float)Math.sin(degrees), e);
		origin.add(RADIUS * (float)Math.cos(THETA + degrees), 1.5f, RADIUS * (float)Math.sin(THETA + degrees), f);
		origin.add(RADIUS * (float)Math.cos(THETA * 2d + degrees), 1.5f, RADIUS * (float)Math.sin(THETA * 2d + degrees), g);
		origin.add(RADIUS * (float)Math.cos(THETA * 3d + degrees), 1.5f, RADIUS * (float)Math.sin(THETA * 3d + degrees), h);
		
		Vector3f ab = new Vector3f();
		Vector3f ad = new Vector3f();
		Vector3f ae = new Vector3f();
		Vector3f bc = new Vector3f();
		Vector3f bf = new Vector3f();
		Vector3f cd = new Vector3f();
		Vector3f cg = new Vector3f();
		Vector3f dh = new Vector3f();
		Vector3f ef = new Vector3f();
		Vector3f eh = new Vector3f();
		Vector3f fg = new Vector3f();
		Vector3f gh = new Vector3f();
		
		b.sub(a, ab);
		d.sub(a, ad);
		e.sub(a, ae);
		c.sub(b, bc);
		f.sub(b, bf);
		d.sub(c, cd);
		g.sub(c, cg);
		h.sub(d, dh);
		f.sub(e, ef);
		h.sub(e, eh);
		g.sub(f, fg);
		h.sub(g, gh);
		
		for (float amount = 0f; amount <= 1f; amount += 0.05f) {
			level.addParticle(HypernovaEffect.PARTICLE, a.x + amount * ab.x, a.y + amount * ab.y, a.z + amount * ab.z, 0d, 0d, 0d);
			level.addParticle(HypernovaEffect.PARTICLE, a.x + amount * ad.x, a.y + amount * ad.y, a.z + amount * ad.z, 0d, 0d, 0d);
			level.addParticle(HypernovaEffect.PARTICLE, a.x + amount * ae.x, a.y + amount * ae.y, a.z + amount * ae.z, 0d, 0d, 0d);
			level.addParticle(HypernovaEffect.PARTICLE, b.x + amount * bc.x, b.y + amount * bc.y, b.z + amount * bc.z, 0d, 0d, 0d);
			level.addParticle(HypernovaEffect.PARTICLE, b.x + amount * bf.x, b.y + amount * bf.y, b.z + amount * bf.z, 0d, 0d, 0d);
			level.addParticle(HypernovaEffect.PARTICLE, c.x + amount * cd.x, c.y + amount * cd.y, c.z + amount * cd.z, 0d, 0d, 0d);
			level.addParticle(HypernovaEffect.PARTICLE, c.x + amount * cg.x, c.y + amount * cg.y, c.z + amount * cg.z, 0d, 0d, 0d);
			level.addParticle(HypernovaEffect.PARTICLE, d.x + amount * dh.x, d.y + amount * dh.y, d.z + amount * dh.z, 0d, 0d, 0d);
			level.addParticle(HypernovaEffect.PARTICLE, e.x + amount * ef.x, e.y + amount * ef.y, e.z + amount * ef.z, 0d, 0d, 0d);
			level.addParticle(HypernovaEffect.PARTICLE, e.x + amount * eh.x, e.y + amount * eh.y, e.z + amount * eh.z, 0d, 0d, 0d);
			level.addParticle(HypernovaEffect.PARTICLE, f.x + amount * fg.x, f.y + amount * fg.y, f.z + amount * fg.z, 0d, 0d, 0d);
			level.addParticle(HypernovaEffect.PARTICLE, g.x + amount * gh.x, g.y + amount * gh.y, g.z + amount * gh.z, 0d, 0d, 0d);
		}
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.CHUNK_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 400;
	}
}
