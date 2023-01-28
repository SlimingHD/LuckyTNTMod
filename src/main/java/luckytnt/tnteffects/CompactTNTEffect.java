package luckytnt.tnteffects;

import java.util.function.Supplier;

import luckytntlib.block.LTNTBlock;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;

public class CompactTNTEffect extends PrimedTNTEffect{
	private final double chance;
	private final float size;
	private final Supplier<RegistryObject<LTNTBlock>> place;
	private final Supplier<RegistryObject<LTNTBlock>> block;
	
	public CompactTNTEffect(Supplier<RegistryObject<LTNTBlock>> block, double chance, float size, Supplier<RegistryObject<LTNTBlock>> place) {
		this.chance = chance;
		this.size = size;
		this.place = place;
		this.block = block;
	}

	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion explosion = new ImprovedExplosion(entity.level(), entity.getPos(), size);
		explosion.doBlockExplosion(new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(Math.random() < chance && !state.isAir() && state.getExplosionResistance(level, pos, explosion) < 100) {
					state.onBlockExploded(level, pos, explosion);
					level.setBlockAndUpdate(pos, place.get().get().defaultBlockState());
				}
			}
		});
	}
	
	@Override
	public Block getBlock() {
		return block.get().get();
	}
}
