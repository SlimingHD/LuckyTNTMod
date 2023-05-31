package luckytnt.block;

import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class GunpowderOreBlock extends DropExperienceBlock{
	
	public GunpowderOreBlock(BlockBehaviour.Properties properties) {
		super(properties, UniformInt.of(2, 5));
	}
	
	@SuppressWarnings("deprecation")
	@Override
    public boolean canHarvestBlock(BlockState state, BlockGetter world, BlockPos pos, Player player) {
        if (player.getInventory().getSelected().getItem() instanceof TieredItem tieredItem)
            return tieredItem.getTier().getLevel() > 1;
        return false;
    }
}
