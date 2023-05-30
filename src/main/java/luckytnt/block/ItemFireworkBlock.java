package luckytnt.block;

import javax.annotation.Nullable;

import luckytnt.block.entity.ItemFireworkBlockEntity;
import luckytnt.entity.PrimedItemFirework;
import luckytnt.registry.EntityRegistry;
import luckytntlib.block.LTNTBlock;
import luckytntlib.entity.PrimedLTNT;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class ItemFireworkBlock extends LTNTBlock implements EntityBlock {

	public ItemFireworkBlock(Properties properties) {
		super(properties, EntityRegistry.ITEM_FIREWORK, false);
	}
	
	@Override
	public PrimedLTNT explode(Level level, boolean exploded, double x, double y, double z, @Nullable LivingEntity igniter) throws NullPointerException {
		if(TNT != null) {
			BlockEntity blockEntity = level.getBlockEntity(new BlockPos(x, y, z));
			PrimedItemFirework tnt = new PrimedItemFirework(EntityRegistry.ITEM_FIREWORK.get(), level);
			tnt.setFuse(40);
			tnt.setPos(x + 0.5f, y, z + 0.5f);
			tnt.setOwner(igniter);
			if(blockEntity != null && blockEntity instanceof ItemFireworkBlockEntity block) {
				tnt.item = block.item;
				tnt.stack = block.stack;
				tnt.getPersistentData().putInt("itemID", block.getPersistentData().getInt("itemID"));
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
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return EntityRegistry.ITEM_FIREWORK_BLOCK_ENTITY.get().create(pos, state);
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		ItemStack stack = player.getItemInHand(hand);
		Item item = stack.getItem();
		if(stack != ItemStack.EMPTY && item != Items.FLINT_AND_STEEL && level.getBlockEntity(pos) != null && level.getBlockEntity(pos) instanceof ItemFireworkBlockEntity block) {
			block.item = item;
			block.stack = stack;
			block.getPersistentData().putInt("itemID", Item.getId(item));
			if(!player.isCreative()) {
				stack.shrink(1);
			}
			player.awardStat(Stats.ITEM_USED.get(item));
			return InteractionResult.SUCCESS;
		}
		return super.use(state, level, pos, player, hand, result);
	}
}
