package luckytnt.tnteffects;

import luckytnt.block.GotthardTunnelBlock;
import luckytnt.network.ClientboundBooleanNBTPacket;
import luckytnt.network.ClientboundStringNBTPacket;
import luckytnt.network.PacketHandler;
import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraftforge.network.PacketDistributor;

public class GotthardTunnelEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if (!entity.getLevel().isClientSide() && entity.getTNTFuse() == 200 && entity instanceof Entity ent) {
      		PacketHandler.CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> ent), new ClientboundStringNBTPacket("direction", ent.getPersistentData().getString("direction"), ent.getId()));
      		PacketHandler.CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> ent), new ClientboundBooleanNBTPacket("streets", ent.getPersistentData().getBoolean("streets"), ent.getId()));
      	}
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		
		boolean streets = entity.getPersistentData().getBoolean("streets");
		Direction dir = Direction.byName(entity.getPersistentData().getString("direction"));
		if (dir == null) {
			dir = Direction.NORTH;
		}
		Vec3i forward = dir.getNormal();
		Vec3i up = Direction.UP.getNormal();
		Vec3i down = Direction.DOWN.getNormal();
		Vec3i right = dir.getClockWise().getNormal();
		Vec3i left = dir.getCounterClockWise().getNormal();
		
		boolean xForward = dir.getAxis() == Axis.X;
		ImprovedExplosion dummy = ImprovedExplosion.dummyExplosion(level);
		BlockPos origin = BlockPos.containing(entity.getPos());
		BoundingBox box = BoundingBox.fromCorners(left.multiply(11).offset(down), forward.multiply(200).offset(right.multiply(11)).offset(up.multiply(16)));
		for (int offX = box.minX(); offX <= box.maxX(); offX++) {
			for (int offY = box.minY(); offY <= box.maxY(); offY++) {
				for (int offZ = box.minZ(); offZ <= box.maxZ(); offZ++) {
					BlockPos pos = origin.offset(offX, offY, offZ);
					BlockState state = level.getBlockState(pos);
					if (Math.max(state.getBlock().getExplosionResistance(), state.getFluidState().getExplosionResistance()) <= 200f) {
						if (offY >= 0 && offY <= 15 && leftRightOffset(offX, offZ, xForward) <= 10) {
							level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
							state.getBlock().wasExploded(level, pos, dummy);
						} else {
							if (offY == -1) {
								level.setBlockAndUpdate(pos, chooseFloorBlock(offX, offY, offZ, xForward, streets).defaultBlockState());
								state.getBlock().wasExploded(level, pos, dummy);
							} else {
								if (state.isCollisionShapeFullBlock(level, pos) || level.getBrightness(LightLayer.SKY, pos) < 15) {
									level.setBlockAndUpdate(pos, chooseWallBlock(offX, offY, offZ, xForward).defaultBlockState());
									state.getBlock().wasExploded(level, pos, dummy);
								}
							}
						}
					}
				}
			}
		}
		
		ImprovedExplosion particleExplosion = new ImprovedExplosion(entity.getLevel(), entity.getPos(), 15);
		particleExplosion.spawnExplosionParticles();
	}
	
	@Override
	public BlockState getBlockState(IExplosiveEntity entity) {
		Direction dir = Direction.byName(entity.getPersistentData().getString("direction"));
		if (dir == null) {
			dir = Direction.NORTH;
		} 
		return BlockRegistry.GOTTHARD_TUNNEL.get().defaultBlockState().setValue(GotthardTunnelBlock.STREETS, entity.getPersistentData().getBoolean("streets")).setValue(GotthardTunnelBlock.FACING, dir);
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 200;
	}
	
	private static int mod(int number, int mod) {
		int ret = number % mod;
		if (ret < 0) {
			ret += mod;
		}
		return ret;
	}
	
	private static int leftRightOffset(int offX, int offZ, boolean xForward) {
		return xForward ? Math.abs(offZ) : Math.abs(offX);
	}
	
	private static Block chooseFloorBlock(int offX, int offY, int offZ, boolean xForward, boolean streets) {
		int leftRightOffset = leftRightOffset(offX, offZ, xForward);
		int forwardOffset = xForward ? offX : offZ;
		if (streets) {
			if (leftRightOffset == 11) {
				return Blocks.STONE;
			} else if (leftRightOffset != 0 && leftRightOffset != 5 && leftRightOffset != 10) {
				return Blocks.GRAY_CONCRETE;
			} else if (leftRightOffset == 5) {
				return mod(forwardOffset - 1, 5) <= 2 ? Blocks.YELLOW_CONCRETE : Blocks.GRAY_CONCRETE;
			} else {
				return mod(forwardOffset - 2, 4) == 0 ? Blocks.SEA_LANTERN : Blocks.SMOOTH_STONE;
			}
		} else {
			if (leftRightOffset != 0 && leftRightOffset != 10) {
				return Blocks.STONE;
			} else {
				return mod(forwardOffset - 2, 4) == 0 ? Blocks.SEA_LANTERN : Blocks.STONE;
			}
		}
	}
	
	private static Block chooseWallBlock(int offX, int offY, int offZ, boolean xForward) {
		if (offY != 5 && offY != 10 && offY != 16) {
			return Blocks.STONE;
		} else {
			int leftRightOffset = leftRightOffset(offX, offZ, xForward);
			int forwardOffset = xForward ? offX : offZ;
			if (offY == 16 && leftRightOffset != 5) {
				return Blocks.STONE;
			} else {
				return mod(forwardOffset - 2, 4) == 0 ? Blocks.SEA_LANTERN : Blocks.STONE;
			}
		}
	}
}