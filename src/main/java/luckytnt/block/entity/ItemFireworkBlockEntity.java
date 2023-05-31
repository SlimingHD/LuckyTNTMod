package luckytnt.block.entity;

import luckytnt.registry.EntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ItemFireworkBlockEntity extends BlockEntity {

	public Item item;
	public ItemStack stack;
	
	public ItemFireworkBlockEntity(BlockEntityType<ItemFireworkBlockEntity> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}
	
	public ItemFireworkBlockEntity(BlockPos pos, BlockState state) {
		this(EntityRegistry.ITEM_FIREWORK_BLOCK_ENTITY.get(), pos, state);
	}
}
