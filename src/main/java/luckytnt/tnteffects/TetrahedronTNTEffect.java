package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;

public class TetrahedronTNTEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		BlockPos pos = toBlockPos(ent.getPos());
		
		double heigth = (Math.sqrt(3D) / 2D) * 60D;
		double sideHeigth = Math.sqrt(60D * 60D - 30D * 30D);
		
		BlockPos A = pos.offset(-30, -30, (int)-Math.round((1D / 3D) * sideHeigth));
		BlockPos B = pos.offset(30, -30, (int)-Math.round((1D / 3D) * sideHeigth));
		BlockPos C = pos.offset(0, -30, (int)Math.round((2D / 3D) * sideHeigth));
		BlockPos D = pos.offset(0, (int)Math.round(heigth - 30D), 0);
		
		Vec3 DA = new Vec3(A.getX() - D.getX(), A.getY() - D.getY(), A.getZ() - D.getZ());
		Vec3 DB = new Vec3(B.getX() - D.getX(), B.getY() - D.getY(), B.getZ() - D.getZ());
		Vec3 DC = new Vec3(C.getX() - D.getX(), C.getY() - D.getY(), C.getZ() - D.getZ());
		Vec3 AB = new Vec3(B.getX() - A.getX(), B.getY() - A.getY(), B.getZ() - A.getZ());
		Vec3 AC = new Vec3(C.getX() - A.getX(), C.getY() - A.getY(), C.getZ() - A.getZ());
		
		Vec3 NDAB = DB.cross(DA);
		Vec3 NDAC = DA.cross(DC);
		Vec3 NDCB = DC.cross(DB);
		Vec3 NABC = AB.cross(AC);
		
		for (int offX = -40; offX <= 40; offX++) {
			for (int offY = -40; offY <= 40; offY++) {
				for (int offZ = -40; offZ <= 40; offZ++) {
					Vec3 vec = new Vec3(Math.round(ent.x() + offX), Math.round(ent.y() + offY), Math.round(ent.z() + offZ));

					if (distance(vec, NDAB, D) <= 0 && distance(vec, NDAC, D) <= 0 && distance(vec, NDCB, D) <= 0 && distance(vec, NABC, A) <= 0) {
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
		for(double i = 0D; i <= 1D; i += 0.05D) {
			ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0f, 0f, 0f), 0.5f), ent.x() - 0.5D + i, ent.y() + 1D, ent.z() - 0.5D, 0, 0, 0);
		}
		
		Vec3 vec1 = new Vec3(0.5D, 0D, 1D);
		Vec3 vec2 = new Vec3(-0.5D, 0D, 1D);
		
		for(double i = 0; i <= vec1.length(); i += vec1.length() / 20D) {
			ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0f, 0f, 0f), 0.5f), ent.x() - 0.5D + vec1.x * i, ent.y() + 1D, ent.z() - 0.5D + vec1.z * i, 0, 0, 0);
			ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0f, 0f, 0f), 0.5f), ent.x() + 0.5D + vec2.x * i, ent.y() + 1D, ent.z() - 0.5D + vec2.z * i, 0, 0, 0);
		}
		
		Vec3 vec3 = new Vec3(0.5D, 0.75D, 0.5D);
		Vec3 vec4 = new Vec3(-0.5D, 0.75D, 0.5D);
		Vec3 vec5 = new Vec3(0D, 0.75D, -0.5D);
		
		for(double i = 0; i <= vec3.length(); i += vec3.length() / 20D) {
			ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0f, 0f, 0f), 0.5f), ent.x() - 0.5D + vec3.x * i, ent.y() + 1D + vec3.y * i, ent.z() - 0.5D + vec3.z * i, 0, 0, 0);
			ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0f, 0f, 0f), 0.5f), ent.x() + 0.5D + vec4.x * i, ent.y() + 1D + vec3.y * i, ent.z() - 0.5D + vec4.z * i, 0, 0, 0);
		}
		
		for(double i = 0; i <= vec5.length(); i += vec5.length() / 20D) {
			ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0f, 0f, 0f), 0.5f), ent.x(), ent.y() + 1D + vec5.y * i, ent.z() + 0.5D + vec5.z * i, 0, 0, 0);
		}
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 100;
	}
	
	@Override
	public Block getBlock()  {
		return BlockRegistry.TETRAHEDRON_TNT.get();
	}
	
	public static double distance(Vec3 point, Vec3 normal, BlockPos pointOnSide) {
		double n0 = -(normal.x * pointOnSide.getX() + normal.y * pointOnSide.getY() + normal.z * pointOnSide.getZ());
		double divisor = normal.x * point.x + normal.y * point.y + normal.z * point.z;
		return (divisor + n0) / normal.length();
	}
}
