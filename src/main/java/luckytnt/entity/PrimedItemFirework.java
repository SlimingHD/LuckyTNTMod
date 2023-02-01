package luckytnt.entity;

import luckytnt.tnteffects.ItemFireworkEffect;
import luckytntlib.entity.PrimedLTNT;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PrimedItemFirework extends PrimedLTNT{

	public Item item;
	public ItemStack stack;
	
	public PrimedItemFirework(EntityType<PrimedLTNT> type, Level level) {
		super(type, level, new ItemFireworkEffect());
	}
}
