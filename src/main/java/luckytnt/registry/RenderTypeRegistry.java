package luckytnt.registry;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;

import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderType.CompositeState;

public abstract class RenderTypeRegistry extends RenderStateShard {

	public static final RenderType DEATH_RAY = RenderType.create(
			"death_ray",
			DefaultVertexFormat.POSITION_TEX,
			Mode.QUADS,
			256, false, true,
			CompositeState.builder().setTransparencyState(LIGHTNING_TRANSPARENCY).setWriteMaskState(COLOR_WRITE).setCullState(NO_CULL).setOutputState(MAIN_TARGET).setShaderState(new ShaderStateShard(() -> ShaderRegistry.DEATH_RAY)).createCompositeState(false));

	public static final RenderType CHARGE_UP = RenderType.create(
			"charge_up",
			DefaultVertexFormat.POSITION_TEX,
			Mode.QUADS,
			256, false, true,
			CompositeState.builder().setTransparencyState(LIGHTNING_TRANSPARENCY).setWriteMaskState(COLOR_WRITE).setCullState(NO_CULL).setOutputState(MAIN_TARGET).setShaderState(new ShaderStateShard(() -> ShaderRegistry.CHARGE_UP)).createCompositeState(false));

	public static final RenderType DEPTH_WRITER = RenderType.create(
			"depth_writer",
			DefaultVertexFormat.POSITION,
			Mode.QUADS,
			256, false, true,
			CompositeState.builder().setWriteMaskState(DEPTH_WRITE).setCullState(NO_CULL).setOutputState(MAIN_TARGET).setShaderState(ShaderStateShard.POSITION_SHADER).createCompositeState(false));

	private RenderTypeRegistry(String name, Runnable setupState, Runnable clearState) {
		super(name, setupState, clearState);
	}
}