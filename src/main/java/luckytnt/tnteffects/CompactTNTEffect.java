package luckytnt.tnteffects;

import java.util.function.Supplier;

import luckytntlib.block.LTNTBlock;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;

public class CompactTNTEffect extends PrimedTNTEffect{
	private final double chance;
	private final float size;
	private final Supplier<RegistryObject<LTNTBlock>> place;

	public CompactTNTEffect(double chance, float size, Supplier<RegistryObject<LTNTBlock>> place) {
		this.chance = chance;
		this.size = size;
		this.place = place;
	}

	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion explosion = new ImprovedExplosion(entity.level(), (Entity)entity, entity.getPos(), size);
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
}
