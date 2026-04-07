package luckytnt.entity;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.mojang.datafixers.util.Pair;

import luckytnt.tnteffects.ReplayTNTEffect;
import luckytntlib.entity.PrimedLTNT;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class PrimedReplayTNT extends PrimedLTNT {

	public Queue<List<Pair<BlockPos, BlockState>>> replayQueue = new LinkedList<>();
	
	public PrimedReplayTNT(EntityType<PrimedLTNT> type, Level level) {
		super(type, level, new ReplayTNTEffect());
	}
}
