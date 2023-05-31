package luckytnt.block;

import luckytnt.registry.EntityRegistry;
import luckytntlib.block.LTNTBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class RedstoneTNTBlock extends LTNTBlock{

	public RedstoneTNTBlock(BlockBehaviour.Properties properties) {
		super(properties, EntityRegistry.REDSTONE_TNT, true);
	}
	
    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState state2, boolean keep) {    	
    }
    
    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos pos2, boolean keep) {
    }
    
    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
    	return true;
    }
    
    @Override
    public int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
    	return 15;
    }
}
