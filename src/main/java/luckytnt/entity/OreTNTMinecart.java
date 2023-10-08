package luckytnt.entity;

import java.util.ArrayList;
import java.util.List;

import luckytnt.registry.EntityRegistry;
import luckytnt.registry.ItemRegistry;
import luckytntlib.entity.LTNTMinecart;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class OreTNTMinecart extends LTNTMinecart{

	public List<BlockPos> availablePos = new ArrayList<>();
	
	public OreTNTMinecart(EntityType<LTNTMinecart> type, Level level) {
		super(type, level, EntityRegistry.ORE_TNT, () -> ItemRegistry.ORE_TNT_MINECART, false);
	}
}
