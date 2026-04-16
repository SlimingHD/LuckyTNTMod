package luckytnt.registry;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;

import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderType.CompositeState;

public abstract class RenderTypeRegistry extends RenderStateShard {

	private RenderTypeRegistry(String name, Runnable setupState, Runnable clearState) {
		super(name, setupState, clearState);
	}

	public static final RenderType DEATH_RAY = RenderType.create("death_ray",
																 DefaultVertexFormat.POSITION_TEX,
																 Mode.QUADS,
																 256, false, true,
																 CompositeState.builder().setTransparencyState(LIGHTNING_TRANSPARENCY).setCullState(NO_CULL).setOutputState(WEATHER_TARGET).setShaderState(new ShaderStateShard(() -> ShaderRegistry.DEATH_RAY)).createCompositeState(false));

	public static final RenderType CHARGE_UP = RenderType.create("charge_up",
																 DefaultVertexFormat.POSITION_TEX,
																 Mode.QUADS,
																 256, false, true,
																 CompositeState.builder().setTransparencyState(LIGHTNING_TRANSPARENCY).setCullState(NO_CULL).setOutputState(WEATHER_TARGET).setShaderState(new ShaderStateShard(() -> ShaderRegistry.CHARGE_UP)).createCompositeState(false));
}
