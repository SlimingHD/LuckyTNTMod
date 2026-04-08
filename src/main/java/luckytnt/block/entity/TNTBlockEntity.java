package luckytnt.block.entity;

import luckytnt.registry.EntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TNTBlockEntity extends BlockEntity {
	
	public TNTBlockEntity(BlockPos pos, BlockState state) {
		super(EntityRegistry.TNT_BLOCK_ENTITY.get(), pos, state);
	}
}
