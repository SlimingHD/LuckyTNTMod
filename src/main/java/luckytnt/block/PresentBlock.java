package luckytnt.block;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import luckytnt.registry.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.loot.LootParams;

public class PresentBlock extends Block {
	
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	public static final IntegerProperty TYPE = IntegerProperty.create("type", 0, 4);
	
	public PresentBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}	

	@Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> definition) {
    	super.createBlockStateDefinition(definition);
    	definition.add(FACING);
    	definition.add(TYPE);
    }
	
	@Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
    	return defaultBlockState();
    }
	
	@Override
	public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
		return Collections.singletonList(ItemStack.EMPTY);
	}
	
	@Override
	public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluidState) {
		if(!player.isCreative() && super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluidState)) {
			Random random = new Random();
			Item item = Items.COAL;
			int itemCount = random.nextInt(6, 24);
			int xpCount = 0;
			int rand = new Random().nextInt(128);
			if(rand > 70 && rand <= 100) {
				item = Items.SNOWBALL;
				itemCount = random.nextInt(8,16);
				xpCount = random.nextInt(itemCount / 4, itemCount / 2 + 1);
			}
			else if(rand > 100 && rand <= 110) {
				item = BlockRegistry.SNOW_TNT.get().asItem();
				itemCount = 1;
				xpCount = random.nextInt(8, 12);
			}
			else if(rand > 110 && rand <= 119) {
				item = Items.DIAMOND;
				itemCount = random.nextInt(1, 4);
				xpCount = random.nextInt(8 * itemCount, 12 * itemCount);
			}
			else if(rand > 119 && rand <= 123) {
				item = BlockRegistry.CHRISTMAS_TNT.get().asItem();
				itemCount = 1;
				xpCount = random.nextInt(32, 48);
			}
			else if(rand > 123 && rand <= 125) {
				item = BlockRegistry.SNOWSTORM_TNT.get().asItem();
				itemCount = 1;
				xpCount = random.nextInt(48, 64);
			}
			else if(rand > 125 && rand <= 127) {
				item = Items.TOTEM_OF_UNDYING;
				itemCount = 1;
				xpCount = random.nextInt(64, 96);
			}
			ItemEntity itemEntity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(item, itemCount));
			level.addFreshEntity(itemEntity);
			rand = random.nextInt(1, 6);
			for(int i = 0; i < rand; i++) {
				ExperienceOrb xp = new ExperienceOrb(level, pos.getX(), pos.getY(), pos.getZ(), xpCount / rand);
				level.addFreshEntity(xp);
			}
			return true;
		}
		return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluidState);
	}
}
