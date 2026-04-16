package luckytnt.client.renderer.helper;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import luckytnt.registry.RenderTypeRegistry;
import luckytnt.registry.ShaderRegistry;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.ShaderInstance;

public class DeathRayRenderer {

	public static void renderDeathRay(Vector3f start, Vector3f end, float rayRadius, float length, float animationSpeed, boolean axialFalloff, Vector3f viewDir, Vector3f color, Vector3f centerColor, PoseStack pose, MultiBufferSource bufferSource) {
		VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderTypeRegistry.DEATH_RAY);
		ShaderInstance shader = ShaderRegistry.DEATH_RAY;
		shader.safeGetUniform("uTime").set((System.currentTimeMillis() % 100000) / 1000f * animationSpeed);
		shader.safeGetUniform("uRadius").set(rayRadius * 4f);
		shader.safeGetUniform("uLength").set(length);
		shader.safeGetUniform("uAxialFalloffFactor").set(axialFalloff ? 0f : 1f);
		shader.safeGetUniform("uColor").set(color);
		shader.safeGetUniform("uCenterColor").set(centerColor);
		
		renderRay(start, end, rayRadius, viewDir, pose, vertexConsumer);
		if (bufferSource instanceof BufferSource buffer) {
			buffer.endBatch(RenderTypeRegistry.DEATH_RAY);
		}
	}
	
	public static void renderChargeUp(Vector3f pos, Vector3f dir, float radius, float animationSpeed, Vector3f color, Vector3f centerColor, PoseStack pose, MultiBufferSource bufferSource) {
		VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderTypeRegistry.CHARGE_UP);
		ShaderInstance shader = ShaderRegistry.CHARGE_UP;
		shader.safeGetUniform("uTime").set((System.currentTimeMillis() % 100000) / 1000f * animationSpeed);
		shader.safeGetUniform("uRadius").set(radius * 4f);
		shader.safeGetUniform("uColor").set(color);
		shader.safeGetUniform("uCenterColor").set(centerColor);
		
		renderChargeQuad(pos, dir, radius, pose.last().pose(), vertexConsumer);
		if (bufferSource instanceof BufferSource buffer) {
			buffer.endBatch(RenderTypeRegistry.CHARGE_UP);
		}
	}
	
	private static void renderRay(Vector3f start, Vector3f end, float radius, Vector3f viewDir, PoseStack pose, VertexConsumer vertexConsumer) {
		Vector3f dir = new Vector3f(end).sub(start).normalize();	
		Vector3f right = new Vector3f(dir).cross(viewDir).normalize();
		renderRayQuad(start, end, right, new Vector3f(), radius, pose.last().pose(), vertexConsumer);
	}
	
	private static void renderRayQuad(Vector3f start, Vector3f end, Vector3f right, Vector3f forward, float radius, Matrix4f pose, VertexConsumer vertexConsumer) {
		Vector3f off0 = new Vector3f(right).mul(-radius).add(new Vector3f(forward).mul(-radius));
		Vector3f off1 = new Vector3f(right).mul(radius).add(new Vector3f(forward).mul(radius));
		
		Vector3f p0 = new Vector3f(start).add(off0);
		Vector3f p1 = new Vector3f(start).add(off1);
		Vector3f p2 = new Vector3f(end).add(off1);
		Vector3f p3 = new Vector3f(end).add(off0);
		vertexConsumer.vertex(pose, p0.x, p0.y, p0.z).uv(0, 0).endVertex();
		vertexConsumer.vertex(pose, p1.x, p1.y, p1.z).uv(0, 1).endVertex();
		vertexConsumer.vertex(pose, p2.x, p2.y, p2.z).uv(1, 1).endVertex();
		vertexConsumer.vertex(pose, p3.x, p3.y, p3.z).uv(1, 0).endVertex();
	}
	
	private static void renderChargeQuad(Vector3f pos, Vector3f dir, float radius, Matrix4f pose, VertexConsumer vertexConsumer) {
		Vector3f up = Math.abs(dir.y) < 0.99f ? new Vector3f(0f, 1f, 0f) : new Vector3f(1f, 0f, 0f);
		Vector3f right = new Vector3f(dir).cross(up).normalize();
		Vector3f forward = new Vector3f(dir).cross(right).normalize();
		
		Vector3f p0 = new Vector3f(pos).add(new Vector3f(right).mul(-radius).add(new Vector3f(forward).mul(-radius)));
		Vector3f p1 = new Vector3f(pos).add(new Vector3f(right).mul(-radius).add(new Vector3f(forward).mul(radius)));
		Vector3f p2 = new Vector3f(pos).add(new Vector3f(right).mul(radius).add(new Vector3f(forward).mul(radius)));
		Vector3f p3 = new Vector3f(pos).add(new Vector3f(right).mul(radius).add(new Vector3f(forward).mul(-radius)));
		
		vertexConsumer.vertex(pose, p0.x, p0.y, p0.z).uv(0, 0).endVertex();
		vertexConsumer.vertex(pose, p1.x, p1.y, p1.z).uv(0, 1).endVertex();
		vertexConsumer.vertex(pose, p2.x, p2.y, p2.z).uv(1, 1).endVertex();
		vertexConsumer.vertex(pose, p3.x, p3.y, p3.z).uv(1, 0).endVertex();
	}
}
