package luckytnt.tnteffects;

import java.lang.reflect.InvocationTargetException;

import javax.annotation.Nullable;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
import luckytntlib.entity.PrimedLTNT;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class NewYearsFireworkEffect extends PrimedTNTEffect {

	private static final double[][] CREEPER_VELOCITIES = new double[][]{{0d, 0.2d}, {0.2d, 0.2d}, {0.2d, 0.6d}, {0.6d, 0.6d}, {0.6d, 0.2d}, {0.2d, 0.2d}, {0.2d, 0.0d}, {0.4d, 0d}, {0.4d, -0.6d}, {0.2d, -0.6d}, {0.2d, -0.4d}, {0d, -0.4d}};
	private static final double[][] STAR_VELOCITIES = new double[][]{{0d, 1d}, {0.3455d, 0.309d}, {0.9511d, 0.309d}, {0.3795918367346939d, -0.12653061224489795d}, {0.6122448979591837d, -0.8040816326530612d}, {0d, -0.35918367346938773d}};
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		RandomSource random = level.getRandom();
		
		if (entity.getPersistentData().getInt("type") == 0 && entity instanceof Entity ent) {
			for (int count = 0; count < 10; count++) {
				Vec3 vec = ent.getViewVector(1f).normalize();
				PrimedLTNT firework = EntityRegistry.NEW_YEARS_FIREWORK.get().create(level);
				firework.setTNTFuse(40);
				firework.setPos(entity.getPos());
				firework.setDeltaMovement(vec.scale(2d));
				firework.getPersistentData().putInt("type", 1);
				level.addFreshEntity(firework);
				ent.setYRot(ent.getYRot() + 36f);
			}
		} else {
			BlockState state = getRandomConcrete(random);
			Shape shape = Shape.byName(entity.getPersistentData().getString("shape"));
			
			if (shape == Shape.SPHERE) {
				double phi = Math.PI * (3d - Math.sqrt(5d));
				for (int i = 0; i < 300; i++) {
					double y = 1d - (i / 299d) * 2d;
					double radius = Math.sqrt(1d - y * y);

					double theta = phi * i;

					double x = Math.cos(theta) * radius;
					double z = Math.sin(theta) * radius;

					Vec3 vec = new Vec3(x, y, z).normalize().scale(1d + random.nextDouble() * 0.5d);
					addFallingBlock(level, entity.x() + 0.5d, entity.y(), entity.z() + 0.5d, vec.x, vec.y, vec.z, state);
				}
			} else if (shape == Shape.CREEPER) {
				createShape(entity, 1d, CREEPER_VELOCITIES, true, state);
			} else {
				createShape(entity, 1d, STAR_VELOCITIES, false, state);
			}
		}
	}
	
	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if (!entity.getLevel().isClientSide() && entity instanceof Entity ent) {
			ent.setDeltaMovement(ent.getDeltaMovement().x, 0.8d, ent.getDeltaMovement().z);
			if (entity.getTNTFuse() == 20) {
				ent.getPersistentData().putString("shape", Shape.getRandomShape(entity.getLevel().getRandom()).getSerializedName());
			}
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x() + 0.5d, entity.y(), entity.z() + 0.5d, 0d, 0d, 0d);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.NEW_YEARS_FIREWORK.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 40;
	}
	
	private static void addFallingBlock(Level level, double x, double y, double z, double mX, double mY, double mZ, BlockState state) {
		try {
			FallingBlockEntity block = CustomFireworkEffect.CONSTRUCTOR.newInstance(level, x, y, z, state);
			block.setDeltaMovement(mX, mY, mZ);
			block.dropItem = false;
			level.addFreshEntity(block);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	private static void createShape(IExplosiveEntity entity, double speed, double[][] shape, boolean flag, BlockState state) {
		Level level = entity.getLevel();
		
		double d0 = shape[0][0];
		double d1 = shape[0][1];
		addFallingBlock(level, entity.x() + 0.5d, entity.y(), entity.z() + 0.5d, d0 * speed, d1 * speed, 0d, state);
		double d2 = level.getRandom().nextFloat() * Math.PI;
		double d3 = flag ? 0.034d : 0.34d;
		int i = flag ? 1 : 3;

		for (int k = 0; k < i; ++k) {
			double d4 = d2 + k * Math.PI * d3;
			double d5 = d0;
			double d6 = d1;

			for (int j = 1; j < shape.length; ++j) {
				double d7 = shape[j][0];
				double d8 = shape[j][1];

				for (double d9 = 0.25d; d9 <= 1.0d; d9 += 0.25d) {
					double d10 = Mth.lerp(d9, d5, d7) * speed;
					double d11 = Mth.lerp(d9, d6, d8) * speed;
					double d12 = d10 * Math.sin(d4);
					d10 = d10 * Math.cos(d4);

					for (double d13 = -1.0d; d13 <= 1.0d; d13 += 2.0d) {
						addFallingBlock(level, entity.x() + 0.5d, entity.y(), entity.z() + 0.5d, d10 * d13, d11, d12 * d13, state);
					}
				}

				d5 = d7;
				d6 = d8;
			}
		}
	}
	
	private static BlockState getRandomConcrete(RandomSource random) {
		return RainbowFireworkEffect.CONCRETE[random.nextInt(RainbowFireworkEffect.CONCRETE.length)].defaultBlockState();
	}
	
	public static enum Shape implements StringRepresentable {
		SPHERE("sphere"),
		STAR("star"),
		CREEPER("creeper");
		
		private final String name;
		
		private Shape(String name) {
			this.name = name;
		}

		@Override
		public String getSerializedName() {
			return name;
		}
		
		public static Shape getRandomShape(RandomSource random) {
			return values()[random.nextInt(values().length)];
		}
		
		@Nullable
		public static Shape byName(String name) {
			for (Shape shape : Shape.values()) {
				if (name.equals(shape.getSerializedName())) {
					return shape;
				}
			}
			return SPHERE;
		}
	}
}
