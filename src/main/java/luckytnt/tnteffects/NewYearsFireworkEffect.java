package luckytnt.tnteffects;

import java.util.Random;

import javax.annotation.Nullable;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
import luckytntlib.entity.PrimedLTNT;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class NewYearsFireworkEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		if(ent.getPersistentData().getInt("type") == 0) {
			for(int count = 0; count < 10; count++) {
				Vec3 vel = ((Entity)ent).getViewVector(1).normalize();
				PrimedLTNT firework = EntityRegistry.NEW_YEARS_FIREWORK.get().create(ent.getLevel());
				firework.setTNTFuse(40);
				firework.setPos(ent.getPos());
				firework.setDeltaMovement(vel.scale(2));
				firework.getPersistentData().putInt("type", 1);
				ent.getLevel().addFreshEntity(firework);
				((Entity)ent).setYRot(((Entity)ent).getYRot() + 36);
			}
		} else {
			Block block = getRandomConcrete();
			Shape shape = Shape.byName(ent.getPersistentData().getString("shape"));
			switch(shape) {
				case SPHERE: 	if(Math.random() < 0.75) {
									double phi = Math.PI * (3D - Math.sqrt(5D));
									for(int i = 0; i < 300; i++) {
										double y = 1D - ((double)i / (300D - 1D)) * 2D;
										double radius = Math.sqrt(1D - y * y);
									
										double theta = phi * i;
									
										double x = Math.cos(theta) * radius;
										double z = Math.sin(theta) * radius;
									
										Vec3 vec = new Vec3((ent.x() + (x * 20)) - ent.x(), (ent.y() + (y * 20)) - ent.y(), (ent.z() + (z * 20)) - ent.z()).normalize().scale(1D + Math.random() * 0.5D);
										addFallingBlock(ent.x(), ent.y(), ent.z(), vec.x, vec.y, vec.z, block.defaultBlockState(), ent);
									}
								}
								break;
				case CREEPER:	createShape(1, new double[][]{{0.0D, 0.2D}, {0.2D, 0.2D}, {0.2D, 0.6D}, {0.6D, 0.6D}, {0.6D, 0.2D}, {0.2D, 0.2D}, {0.2D, 0.0D}, {0.4D, 0.0D}, {0.4D, -0.6D}, {0.2D, -0.6D}, {0.2D, -0.4D}, {0.0D, -0.4D}}, true, block.defaultBlockState(), ent); break;
				case STAR:		createShape(1, new double[][]{{0.0D, 1.0D}, {0.3455D, 0.309D}, {0.9511D, 0.309D}, {0.3795918367346939D, -0.12653061224489795D}, {0.6122448979591837D, -0.8040816326530612D}, {0.0D, -0.35918367346938773D}}, false, block.defaultBlockState(), ent); break;
				default: break;
			}
		}
	}
	
	@Override
	public void explosionTick(IExplosiveEntity ent) {
		((Entity)ent).setDeltaMovement(((Entity)ent).getDeltaMovement().x, 0.8f, ((Entity)ent).getDeltaMovement().z);
		if(ent.getPersistentData().getString("shape").equals("")) {
			String string = "";
			int rand = new Random().nextInt(5);
			switch(rand) {
				case 0: string = Shape.SPHERE.getSerializedName(); break;
				case 1: string = Shape.SPHERE.getSerializedName(); break;
				case 2: string = Shape.STAR.getSerializedName(); break;
				case 3: string = Shape.STAR.getSerializedName(); break;
				case 4: string = Shape.CREEPER.getSerializedName(); break;
				default: break;
			}
			ent.getPersistentData().putString("shape", string);
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x(), ent.y(), ent.z(), 0, 0, 0);
	}
	
	public void addFallingBlock(double x, double y, double z, double mX, double mY, double mZ, BlockState state, IExplosiveEntity ent) {
		FallingBlockEntity block = FallingBlockEntity.fall(ent.getLevel(), new BlockPos(Mth.floor(x), Mth.floor(y), Mth.floor(z)), state);
		block.dropItem = false;
		block.setDeltaMovement(mX, mY, mZ);
		ent.getLevel().addFreshEntity(block);
		if(ent.getLevel() instanceof ServerLevel sl) {
			for(ServerPlayer player : sl.players()) {
				if(player.distanceTo((Entity)ent) <= 100f) {
					player.connection.send(new ClientboundSetEntityMotionPacket(block));
				}
			}
		}
	}
	
    public void createShape(double speed, double[][] shape, boolean flag, BlockState state, IExplosiveEntity ent) {
     	double d0 = shape[0][0];
     	double d1 = shape[0][1];
     	addFallingBlock(ent.x(), ent.y(), ent.z(), d0 * speed, d1 * speed, 0.0D, state, ent);
     	float f = new Random().nextFloat() * (float)Math.PI;
     	double d2 = flag ? 0.034D : 0.34D;
     	int i = flag ? 1 : 3;

     	for(int k = 0; k < i; ++k) {
        	double d3 = (double)f + (double)((float)k * (float)Math.PI) * d2;
        	double d4 = d0;
        	double d5 = d1;

        	for(int j = 1; j < shape.length; ++j) {
           		double d6 = shape[j][0];
           		double d7 = shape[j][1];

           		for(double d8 = 0.25D; d8 <= 1.0D; d8 += 0.25D) {
              		double d9 = Mth.lerp(d8, d4, d6) * speed;
              		double d10 = Mth.lerp(d8, d5, d7) * speed;
              		double d11 = d9 * Math.sin(d3);
              		d9 = d9 * Math.cos(d3);

              		for(double d12 = -1.0D; d12 <= 1.0D; d12 += 2.0D) {
                 		addFallingBlock(ent.x(), ent.y(), ent.z(), d9 * d12, d10, d11 * d12, state, ent);
              		}
           		}

           		d4 = d6;
           		d5 = d7;
        	}
  		}
  	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.NEW_YEARS_FIREWORK.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 40;
	}
	
	public Block getRandomConcrete() {
		Block template = null;
		int rand = new Random().nextInt(12);
		switch (rand) {
			case 0: template = Blocks.RED_CONCRETE; break;
			case 1: template = Blocks.GREEN_CONCRETE; break;
			case 2: template = Blocks.BLUE_CONCRETE; break;
			case 3: template = Blocks.YELLOW_CONCRETE; break;
			case 4: template = Blocks.BROWN_CONCRETE; break;
			case 5: template = Blocks.CYAN_CONCRETE; break;
			case 6: template = Blocks.LIME_CONCRETE; break;
			case 7: template = Blocks.PURPLE_CONCRETE; break;
			case 8: template = Blocks.PINK_CONCRETE; break;
			case 9: template = Blocks.MAGENTA_CONCRETE; break;
			case 10: template = Blocks.ORANGE_CONCRETE; break;
			case 11: template = Blocks.LIGHT_BLUE_CONCRETE; break;
		}
		return template;
	}
	
	public static enum Shape implements StringRepresentable {
		SPHERE("sphere"),
		STAR("star"),
		CREEPER("creeper");
		
		@SuppressWarnings("deprecation")
		private static final StringRepresentable.EnumCodec<Shape> CODEC = StringRepresentable.fromEnum(Shape::values);
		private final String name;
		
		private Shape(String name) {
			this.name = name;
		}

		@Override
		public String getSerializedName() {
			return name;
		}
		
		@SuppressWarnings("deprecation")
		@Nullable
		public static Shape byName(@Nullable String name) {
			return CODEC.byName(name);
		}
		
	}
}
