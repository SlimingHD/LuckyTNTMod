package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;

public class IlluminatiTNTEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		BlockPos pos = toBlockPos(ent.getPos());
		
		BlockPos A = pos.offset(-60, -60, -60);
		BlockPos B = pos.offset(60, -60, -60);
		BlockPos C = pos.offset(60, -60, 60);
		BlockPos D = pos.offset(-60, -60, 60);
		BlockPos E = pos.offset(0, 60, 0);
		
		Vec3 EA = new Vec3(A.getX() - E.getX(), A.getY() - E.getY(), A.getZ() - E.getZ());
		Vec3 EB = new Vec3(B.getX() - E.getX(), B.getY() - E.getY(), B.getZ() - E.getZ());
		Vec3 EC = new Vec3(C.getX() - E.getX(), C.getY() - E.getY(), C.getZ() - E.getZ());
		Vec3 ED = new Vec3(D.getX() - E.getX(), D.getY() - E.getY(), D.getZ() - E.getZ());
		
		Vec3 NEAB = EB.cross(EA);
		Vec3 NEAD = EA.cross(ED);
		Vec3 NEDC = ED.cross(EC);
		Vec3 NECB = EC.cross(EB);
		Vec3 NABCD = new Vec3(0, -1, 0);
		
		for (int offX = -70; offX <= 70; offX++) {
			for (int offY = -70; offY <= 70; offY++) {
				for (int offZ = -70; offZ <= 70; offZ++) {
					Vec3 vec = new Vec3(Math.round(ent.x() + offX), Math.round(ent.y() + offY), Math.round(ent.z() + offZ));

					if (distance(vec, NEAB, E) <= 0 && distance(vec, NEAD, E) <= 0 && distance(vec, NEDC, E) <= 0 && distance(vec, NECB, E) <= 0 && distance(vec, NABCD, A) <= 0) {
						BlockPos pos5 = toBlockPos(ent.getPos()).offset(offX, offY, offZ);

						if (ent.getLevel().getBlockState(pos5).getExplosionResistance(ent.getLevel(), pos5, ImprovedExplosion.dummyExplosion(ent.getLevel())) <= 200) {
							ent.getLevel().getBlockState(pos5).onBlockExploded(ent.getLevel(), pos5, ImprovedExplosion.dummyExplosion(ent.getLevel()));
						}
					}
				}
			}
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		for(double i = 0D; i <= 1D; i += 0.2D) {
			ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x() - 0.5D + i, ent.y() + 1D, ent.z() - 0.5D, 0, 0, 0);
			ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x() - 0.5D + i, ent.y() + 1D, ent.z() + 0.5D, 0, 0, 0);
			ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x() - 0.5D, ent.y() + 1D, ent.z() - 0.5D + i, 0, 0, 0);
			ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x() + 0.5D, ent.y() + 1D, ent.z() - 0.5D + i, 0, 0, 0);
		}
		
		Vec3 vec1 = new Vec3(0.5D, 1D, 0.5D);
		Vec3 vec2 = new Vec3(-0.5D, 1D, 0.5D);
		Vec3 vec3 = new Vec3(0.5D, 1D, -0.5D);
		Vec3 vec4 = new Vec3(-0.5D, 1D, -0.5D);
		
		for(double i = 0D; i < vec1.length(); i += vec1.length() / 5D) {
			ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x() - 0.5D + vec1.x * i, ent.y() + 1D + vec1.y * i, ent.z() - 0.5D + vec1.z * i, 0, 0, 0);
			ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x() + 0.5D + vec2.x * i, ent.y() + 1D + vec2.y * i, ent.z() - 0.5D + vec2.z * i, 0, 0, 0);
			ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x() - 0.5D + vec3.x * i, ent.y() + 1D + vec3.y * i, ent.z() + 0.5D + vec3.z * i, 0, 0, 0);
			ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x() + 0.5D + vec4.x * i, ent.y() + 1D + vec4.y * i, ent.z() + 0.5D + vec4.z * i, 0, 0, 0);
		}
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 120;
	}
	
	@Override
	public Block getBlock()  {
		return BlockRegistry.ILLUMINATI_TNT.get();
	}
	
	public double distance(Vec3 point, Vec3 normal, BlockPos pointOnSide) {
		return TetrahedronTNTEffect.distance(point, normal, pointOnSide);
	}
}
