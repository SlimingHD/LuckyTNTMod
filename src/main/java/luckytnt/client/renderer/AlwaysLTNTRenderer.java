package luckytnt.client.renderer;

import luckytntlib.client.renderer.LTNTRenderer;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AlwaysLTNTRenderer extends LTNTRenderer {
	
	public AlwaysLTNTRenderer(EntityRendererProvider.Context context) {
		super(context);
	}
	
	@Override
	public boolean shouldRender(Entity entity, Frustum frustum, double x, double y, double z) {
		return true;
	}
}
