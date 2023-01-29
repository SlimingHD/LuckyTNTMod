package luckytnt.event;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EntityLivingEvent {

	@SubscribeEvent
	public static void playerLivingTick(TickEvent.PlayerTickEvent event) {
		if(event.phase == TickEvent.Phase.END) {
			Player player = event.player;
			if(player.getPersistentData().getInt("shakeTime") > 0) {
				player.getPersistentData().putInt("shakeTime", player.getPersistentData().getInt("shakeTime") - 1);
			}
		}
	}
}
