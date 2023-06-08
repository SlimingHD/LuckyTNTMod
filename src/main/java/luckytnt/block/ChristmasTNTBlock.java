package luckytnt.block;

import luckytnt.registry.EntityRegistry;
import luckytntlib.block.LTNTBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.MapColor;

public class ChristmasTNTBlock extends LTNTBlock{

	public static final BooleanProperty ONLY_PRESENT = BooleanProperty.create("only_present");
	
	public ChristmasTNTBlock() {
		super(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).sound(SoundType.GRASS), EntityRegistry.CHRISTMAS_TNT, false);
        registerDefaultState(defaultBlockState().setValue(ONLY_PRESENT, false));
	}
    
    @Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> definition) {
    	super.createBlockStateDefinition(definition);
    	definition.add(ONLY_PRESENT);
    }
}
