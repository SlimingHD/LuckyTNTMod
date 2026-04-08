package luckytnt.registry;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;

import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderType.CompositeState;
import net.minecraft.client.renderer.RenderType.CompositeState.CompositeStateBuilder;

public abstract class RenderTypeRegistry extends RenderStateShard {

	private RenderTypeRegistry(String name, Runnable setupState, Runnable clearState) {
		super(name, setupState, clearState);
	}
	
	private static final CompositeStateBuilder LIGHTNING_TRANSPARENT = CompositeState.builder()
		 	.setTransparencyState(LIGHTNING_TRANSPARENCY)
		 	.setWriteMaskState(COLOR_WRITE)
		 	.setCullState(NO_CULL)
		 	.setLightmapState(NO_LIGHTMAP)
		 	.setDepthTestState(LEQUAL_DEPTH_TEST)
		 	.setOutputState(MAIN_TARGET);

	public static final RenderType DEATH_RAY = RenderType.create("death_ray",
																 DefaultVertexFormat.POSITION_TEX,
																 Mode.QUADS,
																 256, false, true,
																 LIGHTNING_TRANSPARENT.setShaderState(new ShaderStateShard(() -> ShaderRegistry.DEATH_RAY)).createCompositeState(false));

	public static final RenderType CHARGE_UP = RenderType.create("charge_up",
																 DefaultVertexFormat.POSITION_TEX,
																 Mode.QUADS,
																 256, false, true,
																 LIGHTNING_TRANSPARENT.setShaderState(new ShaderStateShard(() -> ShaderRegistry.CHARGE_UP)).createCompositeState(false));
}
