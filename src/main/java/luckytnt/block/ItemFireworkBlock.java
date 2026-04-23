package luckytnt.block;

import javax.annotation.Nullable;

import luckytnt.registry.EntityRegistry;
import luckytntlib.block.LTNTBlock;
import luckytntlib.entity.PrimedLTNT;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
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
		if (TNT != null) {
			BlockEntity blockEntity = level.getBlockEntity(new BlockPos(Mth.floor(x), Mth.floor(y), Mth.floor(z)));
			PrimedLTNT tnt = EntityRegistry.ITEM_FIREWORK.get().create(level);
			tnt.setFuse(40);
			tnt.setPos(x + 0.5f, y, z + 0.5f);
			tnt.setOwner(igniter);
			if (blockEntity != null) {
				Tag tag = blockEntity.getPersistentData().get("stack");
				if (tag == null) {
					tag = new ItemStack(Items.AIR).save(new CompoundTag());
				}
				tnt.getPersistentData().put("stack", tag);
			}
			level.addFreshEntity(tnt);
			level.playSound(null, new BlockPos(Mth.floor(x), Mth.floor(y), Mth.floor(z)), SoundEvents.TNT_PRIMED, SoundSource.MASTER, 1, 1);
			if (level.getBlockState(new BlockPos(Mth.floor(x), Mth.floor(y), Mth.floor(z))).getBlock() == this) {
				level.setBlock(new BlockPos(Mth.floor(x), Mth.floor(y), Mth.floor(z)), Blocks.AIR.defaultBlockState(), 3);
			}
			return tnt;
		}
		throw new NullPointerException("No TNT entity present. Make sure it is registered before the block is registered");
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return EntityRegistry.TNT_BLOCK_ENTITY.get().create(pos, state);
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		ItemStack stack = player.getItemInHand(hand);
		Item item = stack.getItem();
		BlockEntity blockEntity = level.getBlockEntity(pos);
		if (stack != ItemStack.EMPTY && item != Items.FLINT_AND_STEEL && blockEntity != null) {
			blockEntity.getPersistentData().put("stack", stack.save(new CompoundTag()));
			if (!player.isCreative()) {
				stack.shrink(1);
			}
			player.awardStat(Stats.ITEM_USED.get(item));
			return InteractionResult.SUCCESS;
		}
		return super.use(state, level, pos, player, hand, result);
	}
}
