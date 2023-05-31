package luckytnt.block;

import luckytnt.registry.EntityRegistry;
import luckytntlib.block.LTNTBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class XRayTNTBlock extends LTNTBlock{

	public XRayTNTBlock(BlockBehaviour.Properties properties) {
		super(properties, EntityRegistry.XRAY_TNT, true);
	}
	
    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
    	return Shapes.empty();
    }
    
    @Override
    public float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
    	return 1f;
    }
    
    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
    	return true;
    }
    
    @SuppressWarnings("deprecation")
	@Override
    public boolean skipRendering(BlockState state, BlockState state2, Direction direction) {
    	return state2.is(this) ? true : super.skipRendering(state, state2, direction);
    }
}
