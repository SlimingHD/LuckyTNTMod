package luckytnt.block;

import javax.annotation.Nullable;

import luckytnt.network.ClientboundStringNBTPacket;
import luckytnt.network.PacketHandler;
import luckytnt.registry.EntityRegistry;
import luckytntlib.block.LTNTBlock;
import luckytntlib.entity.PrimedLTNT;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.PacketDistributor;

public class TunnelingTNTBlock extends LTNTBlock{
	
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;    
	
	public TunnelingTNTBlock(BlockBehaviour.Properties properties) {
		super(properties, EntityRegistry.TUNNELING_TNT, true);
	}
	
    @Override
    public BlockState rotate(BlockState state, LevelAccessor level, BlockPos pos, Rotation rotation) {  	
    	return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }
    
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
    	return defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> definition) {
    	super.createBlockStateDefinition(definition);
    	definition.add(FACING);
    }
    
	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		ItemStack itemstack = player.getItemInHand(hand);
		if (!itemstack.is(Items.FLINT_AND_STEEL) && !itemstack.is(Items.FIRE_CHARGE)) {
			return super.use(state, level, pos, player, hand, result);
		} else {
			onCaughtFire(state, level, pos, result.getDirection(), player);
			Item item = itemstack.getItem();
			if (!player.isCreative()) {
				if (itemstack.is(Items.FLINT_AND_STEEL)) {
					itemstack.hurtAndBreak(1, player, (p) -> {
						p.broadcastBreakEvent(hand);
					});
				} else {
					itemstack.shrink(1);
				}
			}

			player.awardStat(Stats.ITEM_USED.get(item));
			return InteractionResult.sidedSuccess(level.isClientSide);
		}
	}

    @Override
    public PrimedLTNT explode(Level level, boolean exploded, double x, double y, double z, @Nullable LivingEntity igniter) throws NullPointerException {
		if(TNT != null) {
			PrimedLTNT tnt = TNT.get().create(level);
			tnt.setFuse(exploded && randomizedFuseUponExploded() ? tnt.getEffect().getDefaultFuse(tnt) / 8 + random.nextInt(Mth.clamp(tnt.getEffect().getDefaultFuse(tnt) / 4, 1, Integer.MAX_VALUE)) : tnt.getEffect().getDefaultFuse(tnt));
			tnt.setPos(x + 0.5f, y, z + 0.5f);
			tnt.setOwner(igniter);
			tnt.getPersistentData().putString("direction", level.getBlockState(new BlockPos(Mth.floor(x), Mth.floor(y), Mth.floor(z))).getBlock() instanceof TunnelingTNTBlock ? level.getBlockState(new BlockPos(Mth.floor(x), Mth.floor(y), Mth.floor(z))).getValue(FACING).getName() : "east");
			level.addFreshEntity(tnt);
			if(!level.isClientSide) {
				PacketHandler.CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> tnt), new ClientboundStringNBTPacket("direction", tnt.getPersistentData().getString("direction"), tnt.getId()));
			}
			level.playSound(null, new BlockPos(Mth.floor(x), Mth.floor(y), Mth.floor(z)), SoundEvents.TNT_PRIMED, SoundSource.MASTER, 1, 1);
			if(level.getBlockState(new BlockPos(Mth.floor(x), Mth.floor(y), Mth.floor(z))).getBlock() == this) {
				level.setBlock(new BlockPos(Mth.floor(x), Mth.floor(y), Mth.floor(z)), Blocks.AIR.defaultBlockState(), 3);
			}
			return tnt;
		}
		throw new NullPointerException("No TNT entity present. Make sure it is registered before the block is registered");
    }
}
