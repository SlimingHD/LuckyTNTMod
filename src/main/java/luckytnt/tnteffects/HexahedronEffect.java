package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.level.block.Block;

public class HexahedronEffect extends CubicTNTEffect {

	public HexahedronEffect() {
		super(12);
	}

	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		for(float i = 0; i < 3.25f; i += 0.25f) {
			ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 0.75f), ent.x() + 1.5f, ent.y() - 1 + i, ent.z() + 1.5f, 0, 0, 0);
		}		
		for(float i = 0; i < 3.25f; i += 0.25f) {
			ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 0.75f), ent.x() - 1.5f, ent.y() - 1 + i, ent.z() - 1.5f, 0, 0, 0);
		}		
		for(float i = 0; i < 3.25f; i += 0.25f) {
			ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 0.75f), ent.x() + 1.5f, ent.y() - 1 + i, ent.z() - 1.5f, 0, 0, 0);
		}
		for(float i = 0; i < 3.25f; i += 0.25f) {
			ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 0.75f), ent.x() - 1.5f, ent.y() - 1 + i, ent.z() + 1.5f, 0, 0, 0);
		}
		
		for(float i = 0; i < 3.25f; i += 0.25f) {
			ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 0.75f), ent.x() - 1.5f + i, ent.y() - 1, ent.z() + 1.5f, 0, 0, 0);
		}
		for(float i = 0; i < 3.25f; i += 0.25f) {
			ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 0.75f), ent.x() - 1.5f, ent.y() - 1, ent.z() - 1.5f + i, 0, 0, 0);
		}
		for(float i = 0; i < 3.25f; i += 0.25f) {
			ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 0.75f), ent.x() - 1.5f + i, ent.y() + 2, ent.z() + 1.5f, 0, 0, 0);
		}
		for(float i = 0; i < 3.25f; i += 0.25f) {
			ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 0.75f), ent.x() - 1.5f, ent.y() + 2, ent.z() - 1.5f + i, 0, 0, 0);
		}
		
		for(float i = 0; i < 3.25f; i += 0.25f) {
			ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 0.75f), ent.x() + 1.5f - i, ent.y() - 1, ent.z() - 1.5f, 0, 0, 0);
		}
		for(float i = 0; i < 3.25f; i += 0.25f) {
			ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 0.75f), ent.x() + 1.5f, ent.y() - 1, ent.z() + 1.5f - i, 0, 0, 0);
		}
		for(float i = 0; i < 3.25f; i += 0.25f) {
			ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 0.75f), ent.x() + 1.5f - i, ent.y() + 2, ent.z() - 1.5f, 0, 0, 0);
		}
		for(float i = 0; i < 3.25f; i += 0.25f) {
			ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 0.75f), ent.x() + 1.5f, ent.y() + 2, ent.z() + 1.5f - i, 0, 0, 0);
		}
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 140;
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.HEXAHEDRON.get();
	}
}
