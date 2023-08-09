package luckytnt.block;

import java.util.Collections;
import java.util.List;

import luckytntlib.util.explosions.ImprovedExplosion;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.Vec3;

public class UraniumOreBlock extends Block{
	
	public UraniumOreBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}
	
	@SuppressWarnings("deprecation")
	@Override
    public boolean canHarvestBlock(BlockState state, BlockGetter world, BlockPos pos, Player player) {
        if (player.getInventory().getSelected().getItem() instanceof TieredItem tieredItem)
            return tieredItem.getTier().getLevel() >= 2;
        return false;
    }
	
	@Override
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder){
		return Collections.singletonList(new ItemStack(this, 1));
	}
	
    @Override
    public void wasExploded(Level level, BlockPos pos, Explosion explosion) {
    	ImprovedExplosion explo = new ImprovedExplosion(level, new Vec3(pos.getX() + 0.5f, pos.getY(), pos.getZ() + 0.5f), 10);
    	explo.doEntityExplosion(1.5f, true);
    	explo.doBlockExplosion();
    }
}
