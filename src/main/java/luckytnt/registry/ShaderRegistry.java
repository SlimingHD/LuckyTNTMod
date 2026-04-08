package luckytnt.registry;

import java.io.IOException;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;

import luckytnt.LuckyTNTMod;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(bus = Bus.MOD, value = Dist.CLIENT)
public class ShaderRegistry {

	public static ShaderInstance DEATH_RAY;
	public static ShaderInstance CHARGE_UP;
	
	@SubscribeEvent
	public static void registerShaders(RegisterShadersEvent event) {
		try {
			event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation(LuckyTNTMod.MODID, "death_ray"), DefaultVertexFormat.POSITION_TEX), shader -> DEATH_RAY = shader);
			event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation(LuckyTNTMod.MODID, "charge_up"), DefaultVertexFormat.POSITION_TEX), shader -> CHARGE_UP = shader);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
