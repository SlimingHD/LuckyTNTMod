package luckytnt.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import luckytnt.LuckyTNTMod;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;

public class RandomTNTCommand {

	@SuppressWarnings("deprecation")
	public static int executeGiveItems(CommandSourceStack command, int amount, boolean allowDuplicate, String key) {
		if (command.getEntity() instanceof ServerPlayer player) {
			int j = 0;
			List<RegistryObject<? extends Item>> items = LuckyTNTMod.RH.creativeTabItemLists.get(key);
			HashMap<Item, Integer> itemsToDistribute = new HashMap<>();
			
			if (allowDuplicate) {
				for (int i = 0; i < amount; ++i) {
					Item item = items.get(player.getRandom().nextInt(items.size())).get();
					itemsToDistribute.put(item, itemsToDistribute.getOrDefault(item, 0) + 1);
					++j;
				}
			} else {
				int triesLeft = items.size() * 10;
				while (j < Math.min(amount, items.size()) && triesLeft > 0) {
					Item item = items.get(player.getRandom().nextInt(items.size())).get();
					if (!itemsToDistribute.containsKey(item)) {
						itemsToDistribute.put(item, 1);
						++j;
					}
					--triesLeft;
				}
			}
			
			for (Entry<Item, Integer> entry : itemsToDistribute.entrySet()) {
				Item item = entry.getKey();
				int totalAmount = entry.getValue();
				int maxStackSize = item.getMaxStackSize();
				
				while (totalAmount > 0) {
					int individualAmount = Math.min(maxStackSize, totalAmount);
					totalAmount -= individualAmount;
					ItemStack stack = new ItemStack(item, individualAmount);
					boolean addedToInv = player.getInventory().add(stack);
					if (addedToInv && stack.isEmpty()) {
						stack.setCount(1);
						ItemEntity itementity = player.drop(stack, false);
						if (itementity != null) {
							itementity.makeFakeItem();
						}

						player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2f, ((player.getRandom().nextFloat() - player.getRandom().nextFloat()) * 0.7f + 1f) * 2f);
						player.containerMenu.broadcastChanges();
					} else {
						ItemEntity itementity = player.drop(stack, false);
						if (itementity != null) {
							itementity.setNoPickUpDelay();
							itementity.setTarget(player.getUUID());
						}
					}
				}
			}
			
			int l = j;
			command.sendSuccess(() -> Component.translatable("command.luckytntmod.randomtnt.success", l), false);
		}
		return 1;
	}
}
