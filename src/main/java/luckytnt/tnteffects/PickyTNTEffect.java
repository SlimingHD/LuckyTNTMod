package luckytnt.tnteffects;

import java.util.List;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class PickyTNTEffect extends PrimedTNTEffect{

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Block template = entity.level().getBlockState(new BlockPos(entity.getPos()).below()).getBlock();
		ExplosionHelper.doSphericalExplosion(entity.level(), entity.getPos(), 10, new IForEachBlockExplosionEffect() {		
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion()) < 100 && !state.isAir() && state.getBlock() == template) {
					List<ItemStack> drops = Block.getDrops(state, (ServerLevel)level, pos, level.getBlockEntity(pos));
					for(ItemStack stack : drops) {
						ItemEntity item = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), stack);
						level.addFreshEntity(item);
					}
					state.onBlockExploded(level, pos, ImprovedExplosion.dummyExplosion());
				}
			}
		});
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.PICKY_TNT.get();
	}
}
