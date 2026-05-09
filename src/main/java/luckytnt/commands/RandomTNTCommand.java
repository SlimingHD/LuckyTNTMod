package luckytnt.commands;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import luckytnt.LuckyTNTMod;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;

public class RandomTNTCommand {

	public static int executeGiveItems(CommandSourceStack command, int amount, boolean allowDuplicate, String key) {
		if (command.getEntity() instanceof Player player) {
			int j = 0;
			List<RegistryObject<? extends Item>> items = LuckyTNTMod.RH.creativeTabItemLists.get(key);
			if (allowDuplicate) {
				for (int i = 0; i < amount; ++i) {
					if (player.addItem(new ItemStack(items.get(player.getRandom().nextInt(items.size())).get()))) {
						++j;
					}
				}
			} else {
				int tries = 0;
				Set<Item> set = new HashSet<>();
				while (j < amount && j < items.size() && tries < items.size() * 10) {
					Item item = items.get(player.getRandom().nextInt(items.size())).get();
					if (!set.contains(item) && player.addItem(new ItemStack(item))) {
						set.add(item);
						++j;
					}
					++tries;
				}
			}
			int l = j;
			command.sendSuccess(() -> Component.translatable("command.luckytntmod.randomtnt.success", Integer.valueOf(l)), false);
		}
		return 1;
	}
}
