package luckytnt.block;

import javax.annotation.Nullable;

import org.joml.Vector3f;

import luckytnt.registry.EntityRegistry;
import luckytntlib.block.LTNTBlock;
import luckytntlib.entity.PrimedLTNT;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class SmokeTNTBlock extends LTNTBlock implements EntityBlock{

	public SmokeTNTBlock(BlockBehaviour.Properties properties) {
		super(properties, EntityRegistry.SMOKE_TNT, false);
	}

	@Override
	public PrimedLTNT explode(Level level, boolean exploded, double x, double y, double z, @Nullable LivingEntity igniter) throws NullPointerException {
		if(TNT != null) {
			BlockEntity blockEntity = level.getBlockEntity(new BlockPos(x, y, z));
			PrimedLTNT tnt = TNT.get().create(level);
			tnt.setFuse(exploded && shouldRandomlyFuse() ? tnt.getEffect().getDefaultFuse(tnt) / 8 + random.nextInt(Mth.clamp(tnt.getEffect().getDefaultFuse(tnt) / 4, 1, Integer.MAX_VALUE)) : tnt.getEffect().getDefaultFuse(tnt));
			tnt.setPos(x + 0.5f, y, z + 0.5f);
			tnt.setOwner(igniter);
			if(blockEntity != null) {
				tnt.getPersistentData().putFloat("r", blockEntity.getPersistentData().getFloat("r"));
				tnt.getPersistentData().putFloat("g", blockEntity.getPersistentData().getFloat("g"));
				tnt.getPersistentData().putFloat("b", blockEntity.getPersistentData().getFloat("b"));
			}
			level.addFreshEntity(tnt);
			level.playSound(null, new BlockPos(x, y, z), SoundEvents.TNT_PRIMED, SoundSource.MASTER, 1, 1);
			if(level.getBlockState(new BlockPos(x, y, z)).getBlock() == this) {
				level.setBlock(new BlockPos(x, y, z), Blocks.AIR.defaultBlockState(), 3);
			}
			return tnt;
		}
		throw new NullPointerException("No TNT entity present. Make sure it is registered before the block is registered");
	}
	
	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        BlockEntity blockEntity = level.getBlockEntity(pos);	
		if(player.getItemInHand(hand).getItem() instanceof DyeItem dye && blockEntity != null) {
			if(dye == Items.BLACK_DYE) {
				if(blockEntity.getPersistentData().getFloat("r") > 0 || blockEntity.getPersistentData().getFloat("g") > 0 || blockEntity.getPersistentData().getFloat("b") > 0) {
					if(!player.isCreative()) {
						player.getItemInHand(hand).shrink(1);
					}
				}
				blockEntity.getPersistentData().putFloat("r", Mth.clamp(blockEntity.getPersistentData().getFloat("r") - 0.1f, 0f, 1f));
				blockEntity.getPersistentData().putFloat("g", Mth.clamp(blockEntity.getPersistentData().getFloat("g") - 0.1f, 0f, 1f));
				blockEntity.getPersistentData().putFloat("b", Mth.clamp(blockEntity.getPersistentData().getFloat("b") - 0.1f, 0f, 1f));
				if(level instanceof ServerLevel sLevel) {
					sLevel.sendParticles(new DustParticleOptions(new Vector3f(blockEntity.getPersistentData().getFloat("r"), blockEntity.getPersistentData().getFloat("g"), blockEntity.getPersistentData().getFloat("b")), 1f), pos.getX() + 0.5f, pos.getY() + 1f, pos.getZ() + 0.5f, 1, 0, 0, 0, 0);
				}
				return InteractionResult.SUCCESS;
			}
			if(dye == Items.WHITE_DYE) {
				if(blockEntity.getPersistentData().getFloat("r") < 1 || blockEntity.getPersistentData().getFloat("g") < 1 || blockEntity.getPersistentData().getFloat("b") < 1) {
					if(!player.isCreative()) {
						player.getItemInHand(hand).shrink(1);
					}
				}
				blockEntity.getPersistentData().putFloat("r", Mth.clamp(blockEntity.getPersistentData().getFloat("r") + 0.1f, 0f, 1f));
				blockEntity.getPersistentData().putFloat("g", Mth.clamp(blockEntity.getPersistentData().getFloat("g") + 0.1f, 0f, 1f));
				blockEntity.getPersistentData().putFloat("b", Mth.clamp(blockEntity.getPersistentData().getFloat("b") + 0.1f, 0f, 1f));
				if(level instanceof ServerLevel sLevel) {
					sLevel.sendParticles(new DustParticleOptions(new Vector3f(blockEntity.getPersistentData().getFloat("r"), blockEntity.getPersistentData().getFloat("g"), blockEntity.getPersistentData().getFloat("b")), 1f), pos.getX() + 0.5f, pos.getY() + 1f, pos.getZ() + 0.5f, 1, 0, 0, 0, 0);
				}
				return InteractionResult.SUCCESS;
			}
			if(dye == Items.RED_DYE) {
				if(blockEntity.getPersistentData().getFloat("r") < 1) {
					if(!player.isCreative()) {
						player.getItemInHand(hand).shrink(1);
					}
				}
				blockEntity.getPersistentData().putFloat("r", Mth.clamp(blockEntity.getPersistentData().getFloat("r") + 0.1f, 0f, 1f));
				if(level instanceof ServerLevel sLevel) {
					sLevel.sendParticles(new DustParticleOptions(new Vector3f(blockEntity.getPersistentData().getFloat("r"), blockEntity.getPersistentData().getFloat("g"), blockEntity.getPersistentData().getFloat("b")), 1f), pos.getX() + 0.5f, pos.getY() + 1f, pos.getZ() + 0.5f, 1, 0, 0, 0, 0);
				}
				return InteractionResult.SUCCESS;
			}
			if(dye == Items.GREEN_DYE) {
				if(blockEntity.getPersistentData().getFloat("g") < 1) {
					if(!player.isCreative()) {
						player.getItemInHand(hand).shrink(1);
					}
				}
				blockEntity.getPersistentData().putFloat("g", Mth.clamp(blockEntity.getPersistentData().getFloat("g") + 0.1f, 0f, 1f));
				if(level instanceof ServerLevel sLevel) {
					sLevel.sendParticles(new DustParticleOptions(new Vector3f(blockEntity.getPersistentData().getFloat("r"), blockEntity.getPersistentData().getFloat("g"), blockEntity.getPersistentData().getFloat("b")), 1f), pos.getX() + 0.5f, pos.getY() + 1f, pos.getZ() + 0.5f, 1, 0, 0, 0, 0);
				}
				return InteractionResult.SUCCESS;
			}
			if(dye == Items.BLUE_DYE) {
				if(blockEntity.getPersistentData().getFloat("b") < 1) {
					if(!player.isCreative()) {
						player.getItemInHand(hand).shrink(1);
					}
				}
				blockEntity.getPersistentData().putFloat("b", Mth.clamp(blockEntity.getPersistentData().getFloat("b") + 0.1f, 0f, 1f));
				if(level instanceof ServerLevel sLevel) {
					sLevel.sendParticles(new DustParticleOptions(new Vector3f(blockEntity.getPersistentData().getFloat("r"), blockEntity.getPersistentData().getFloat("g"), blockEntity.getPersistentData().getFloat("b")), 1f), pos.getX() + 0.5f, pos.getY() + 1f, pos.getZ() + 0.5f, 1, 0, 0, 0, 0);
				}
				return InteractionResult.SUCCESS;
			}
		}
		return super.use(state, level, pos, player, hand, result);
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return EntityRegistry.SMOKE_TNT_BLOCK_ENTITY.get().create(pos, state);
	}	
}
