package luckytnt.entity;

import java.util.List;

import com.mojang.datafixers.util.Pair;

import luckytnt.tnteffects.ResetTNTEffect;
import luckytntlib.entity.PrimedLTNT;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class PrimedResetTNT extends PrimedLTNT {

	public List<Pair<BlockPos, BlockState>> blocks;
	public List<Pair<Vec3, Entity>> entities;
	
	public PrimedResetTNT(EntityType<PrimedLTNT> type, Level level) {
		super(type, level, new ResetTNTEffect());
	}
}
