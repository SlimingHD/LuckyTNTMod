package luckytnt.entity;

import java.util.ArrayList;
import java.util.List;

import luckytnt.tnteffects.OreTNTEffect;
import luckytntlib.entity.PrimedLTNT;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class PrimedOreTNT extends PrimedLTNT{

	public List<BlockPos> availablePos = new ArrayList<>();
	
	public PrimedOreTNT(EntityType<PrimedLTNT> type, Level level) {
		super(type, level, new OreTNTEffect());
	}
}
