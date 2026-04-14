package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class TetrahedronTNTEffect extends PrimedTNTEffect {

	private static final float RADIUS = (float)Math.sqrt(2d) * 0.5f;
	private static final float HEIGHT = (float)Math.sqrt(2d) * RADIUS;
	private static final double DEG_TO_RAD = Math.PI / 180d;
	private static final double THETA = (Math.PI * 2d) / 3d;
	private static final Vector3f COLOR = new Vector3f(1f, 0.42f, 0f);

	@SuppressWarnings("deprecation")
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		BlockPos pos = toBlockPos(entity.getPos());

		double heigth = (Math.sqrt(3d) / 2d) * 60d;
		double sideHeigth = Math.sqrt(60d * 60d - 30d * 30d);

		BlockPos A = pos.offset(-30, -30, (int)-Math.round((1d / 3d) * sideHeigth));
		BlockPos B = pos.offset(30, -30, (int)-Math.round((1d / 3d) * sideHeigth));
		BlockPos C = pos.offset(0, -30, (int)Math.round((2d / 3d) * sideHeigth));
		BlockPos D = pos.offset(0, (int)Math.round(heigth - 30d), 0);

		Vec3 DA = new Vec3(A.getX() - D.getX(), A.getY() - D.getY(), A.getZ() - D.getZ());
		Vec3 DB = new Vec3(B.getX() - D.getX(), B.getY() - D.getY(), B.getZ() - D.getZ());
		Vec3 DC = new Vec3(C.getX() - D.getX(), C.getY() - D.getY(), C.getZ() - D.getZ());
		Vec3 AB = new Vec3(B.getX() - A.getX(), B.getY() - A.getY(), B.getZ() - A.getZ());
		Vec3 AC = new Vec3(C.getX() - A.getX(), C.getY() - A.getY(), C.getZ() - A.getZ());

		Vec3 NDAB = DB.cross(DA);
		Vec3 NDAC = DA.cross(DC);
		Vec3 NDCB = DC.cross(DB);
		Vec3 NABC = AB.cross(AC);

		ImprovedExplosion dummy = ImprovedExplosion.dummyExplosion(level);
		ExplosionHelper.customCubicalExplosion(level, entity.getPos(), 40, (l, c, blockpos, state) -> {
			Vec3 vec = Vec3.atCenterOf(blockpos);
			if (distance(vec, NDAB, D) <= 0d && distance(vec, NDAC, D) <= 0d && distance(vec, NDCB, D) <= 0d && distance(vec, NABC, A) <= 0d) {
				if (Math.max(state.getBlock().getExplosionResistance(), state.getFluidState().getExplosionResistance()) <= 200f) {
					level.setBlockAndUpdate(blockpos, Blocks.AIR.defaultBlockState());
					state.getBlock().wasExploded(level, blockpos, dummy);
				}
			}
		});
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		Level level = entity.getLevel();

		double degrees = (entity.getTNTFuse() / 100d) * 360d * DEG_TO_RAD;
		Vector3f origin = entity.getPos().toVector3f().add(0f, 1.1f, 0f);
		
		Vector3f a = new Vector3f();
		Vector3f b = new Vector3f();
		Vector3f c = new Vector3f();
		Vector3f d = new Vector3f();
		
		origin.add(RADIUS * (float)Math.cos(degrees), 0f, RADIUS * (float)Math.sin(degrees), a);
		origin.add(RADIUS * (float)Math.cos(THETA + degrees), 0f, RADIUS * (float)Math.sin(THETA + degrees), b);
		origin.add(RADIUS * (float)Math.cos(THETA * 2d + degrees), 0f, RADIUS * (float)Math.sin(THETA * 2d + degrees), c);
		origin.add(0f, HEIGHT, 0f, d);
		
		Vector3f ab = new Vector3f();
		Vector3f ac = new Vector3f();
		Vector3f ad = new Vector3f();
		Vector3f bc = new Vector3f();
		Vector3f bd = new Vector3f();
		Vector3f cd = new Vector3f();
		
		b.sub(a, ab);
		c.sub(a, ac);
		d.sub(a, ad);
		c.sub(b, bc);
		d.sub(b, bd);
		d.sub(c, cd);
		
		for (float f = 0f; f <= 1f; f += 0.05f) {
			level.addParticle(new DustParticleOptions(COLOR, 0.5f), a.x + f * ab.x, a.y + f * ab.y, a.z + f * ab.z, 0d, 0d, 0d);
			level.addParticle(new DustParticleOptions(COLOR, 0.5f), a.x + f * ac.x, a.y + f * ac.y, a.z + f * ac.z, 0d, 0d, 0d);
			level.addParticle(new DustParticleOptions(COLOR, 0.5f), a.x + f * ad.x, a.y + f * ad.y, a.z + f * ad.z, 0d, 0d, 0d);
			level.addParticle(new DustParticleOptions(COLOR, 0.5f), b.x + f * bc.x, b.y + f * bc.y, b.z + f * bc.z, 0d, 0d, 0d);
			level.addParticle(new DustParticleOptions(COLOR, 0.5f), b.x + f * bd.x, b.y + f * bd.y, b.z + f * bd.z, 0d, 0d, 0d);
			level.addParticle(new DustParticleOptions(COLOR, 0.5f), c.x + f * cd.x, c.y + f * cd.y, c.z + f * cd.z, 0d, 0d, 0d);
		}
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
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
