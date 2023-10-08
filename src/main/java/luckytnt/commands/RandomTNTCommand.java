package luckytnt.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import luckytnt.LuckyTNTMod;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class RandomTNTCommand {

	public static int executeGiveItems(CommandSourceStack command, int amount, boolean allowDuplicate, String key) {
		if(command.getEntity() instanceof Player player) {
			if(allowDuplicate) {
				int j = 0;
				for(int i = 0; i < amount; i++) {
					if(player.addItem(new ItemStack(LuckyTNTMod.RH.creativeTabItemLists.get(key).get(new Random().nextInt(LuckyTNTMod.RH.creativeTabItemLists.get(key).size())).get()))) {
						j++;
					}
				}
				int l = j;
				command.sendSuccess(() -> Component.translatable("command.randomtnt.success1").append(Component.literal(Integer.toString(l))).append(Component.translatable("command.randomtnt.success2")), false);
			} else {
				int j = 0;
				int tries = 0;
				List<Item> list = new ArrayList<>();
				while(j < amount && tries < 1000) {
					Item item = LuckyTNTMod.RH.creativeTabItemLists.get(key).get(new Random().nextInt(LuckyTNTMod.RH.creativeTabItemLists.get(key).size())).get();
					if(!list.contains(item) && player.addItem(new ItemStack(item))) {
						j++;
						list.add(item);
					}
					tries++;
				}
				int l = j;
				command.sendSuccess(() -> Component.translatable("command.randomtnt.success1").append(Component.literal(Integer.toString(l))).append(Component.translatable("command.randomtnt.success2")), false);
			}
		}
		return 1;
	}
}
