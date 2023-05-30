package luckytnt.registry;

import luckytnt.client.model.BombModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class ModelRegistry {

	@SubscribeEvent
	public static void registerModels(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(BombModel.LAYER_LOCATION, BombModel::createBodyLayer);
	}
}
