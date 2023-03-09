package luckytnt.block;

import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.ticks.TickPriority;

public class ToxicStoneBlock extends Block {
	private int timer = 100;
	
	public ToxicStoneBlock(Properties properties) {
		super(properties);
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldstate, boolean moving) {
		super.onPlace(state, level, pos, oldstate, moving);
		level.scheduleTick(pos, this, 1, TickPriority.EXTREMELY_HIGH);
		if(level instanceof ServerLevel slevel) {
			tick(state, slevel, pos, RandomSource.create());
		}
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
		super.tick(state, level, pos, rand);
		level.scheduleTick(pos, this, 1, TickPriority.EXTREMELY_HIGH);
		if(timer >= 0) {
			timer--;
		}
		if(timer == 0) {	
			List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, new AABB(pos.offset(-5, -5, -5), pos.offset(5, 5, 5)));
			for(LivingEntity living : list) {
				if(living instanceof Player player) {
					if(!player.isCreative() && !player.isSpectator()) {
						player.hurt(DamageSource.MAGIC, 8f);
					}
				} else {
					living.hurt(DamageSource.MAGIC, 8f);
				}
			}
			timer = 100;
		}
	}
}
