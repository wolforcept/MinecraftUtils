package wolforce.utils.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureManager;

public class UtilRender {

	public static Minecraft MC = Minecraft.getInstance();

	public static ItemRenderer renderItem = MC.getItemRenderer();
	public static TextureManager textureManager = MC.textureManager;
	public static LocalPlayer player = Minecraft.getInstance().player;

//	public static void renderItem(PoseStack matrix, ItemStack stack, int x, int y, int z, MultiBufferSource buffer) {
//		renderItem(matrix, stack, x, y, z, null, buffer);
//	}
//
//	public static void renderItem(PoseStack matrix, ItemStack stack, int x, int y, int z, ColorAction colorAction) {
//		MultiBufferSource.BufferSource buffer = MC.renderBuffers().bufferSource();
//		renderItem(matrix, stack, x, y, z, colorAction, buffer);
//		buffer.endBatch();
//	}
//
//	public static void renderItem(PoseStack matrix, ItemStack stack, int x, int y, int z) {
//		MultiBufferSource.BufferSource buffer = Minecraft.getInstance().renderBuffers().bufferSource();
//		renderItem(matrix, stack, x, y, z, null, buffer);
//		buffer.endBatch();
//	}
//
//	public static void renderItem(PoseStack matrix, ItemStack stack, int combinedLight, int combinedOverlay,
//			ColorAction colorAction, MultiBufferSource buffer) {
//
//		if (!UtilItemStack.isValid(stack))
//			return;
//
//		BakedModel model = renderItem.getModel(stack, null, player, 0);
//		PoseStack posestack = RenderSystem.getModelViewStack();
////		posestack.pushPose();
////		posestack.translate((double) x, (double) y, (double) (100.0F + z));
////		posestack.translate(8.0D, 8.0D, 0.0D);
////		posestack.scale(1.0F, -1.0F, 1.0F);
////		posestack.scale(16.0F, 16.0F, 16.0F);
//		RenderSystem.applyModelViewMatrix();
//		PoseStack posestack1 = new PoseStack();
//
//		Lighting.setupForFlatItems();
//
//		if (colorAction != null)
//			renderItemBlack(stack, ItemTransforms.TransformType.GUI, false, posestack1, buffer, 15728880,
//					OverlayTexture.NO_OVERLAY, model, colorAction);
//		else
//			renderItem.render(stack, ItemTransforms.TransformType.GUI, false, posestack1, buffer, combinedLight,
//					combinedOverlay, model);
//
////		posestack.popPose();
//		RenderSystem.applyModelViewMatrix();
//
//	}
//
//	public static void renderItemBlack(ItemStack stack, ItemTransforms.TransformType type, boolean _flag,
//			PoseStack matrix, MultiBufferSource source, int x, int y, BakedModel p_115151_, ColorAction colorAction) {
//		if (!stack.isEmpty()) {
//			matrix.pushPose();
//			boolean flag = type == ItemTransforms.TransformType.GUI || type == ItemTransforms.TransformType.GROUND
//					|| type == ItemTransforms.TransformType.FIXED;
//			if (flag) {
//				if (stack.is(Items.TRIDENT)) {
//					p_115151_ = renderItem.getItemModelShaper().getModelManager()
//							.getModel(new ModelResourceLocation("minecraft:trident#inventory"));
//				} else if (stack.is(Items.SPYGLASS)) {
//					p_115151_ = renderItem.getItemModelShaper().getModelManager()
//							.getModel(new ModelResourceLocation("minecraft:spyglass#inventory"));
//				}
//			}
//
//			p_115151_ = net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms(matrix, p_115151_, type,
//					_flag);
//			matrix.translate(-0.5D, -0.5D, -0.5D);
//			if (!p_115151_.isCustomRenderer() && (!stack.is(Items.TRIDENT) || flag)) {
//				boolean flag1;
//				if (type != ItemTransforms.TransformType.GUI && !type.firstPerson()
//						&& stack.getItem() instanceof BlockItem) {
//					Block block = ((BlockItem) stack.getItem()).getBlock();
//					flag1 = !(block instanceof HalfTransparentBlock) && !(block instanceof StainedGlassPaneBlock);
//				} else {
//					flag1 = true;
//				}
//				if (p_115151_.isLayered()) {
//					net.minecraftforge.client.ForgeHooksClient.drawItemLayered(renderItem, p_115151_, stack, matrix,
//							source, x, y, flag1);
//				} else {
//					RenderType rendertype = ItemBlockRenderTypes.getRenderType(stack, flag1);
//					VertexConsumer vertexconsumer;
//					if (stack.is(Items.COMPASS) && stack.hasFoil()) {
//						matrix.pushPose();
//						PoseStack.Pose posestack$pose = matrix.last();
//						if (type == ItemTransforms.TransformType.GUI) {
//							posestack$pose.pose().multiply(0.5F);
//						} else if (type.firstPerson()) {
//							posestack$pose.pose().multiply(0.75F);
//						}
//
//						if (flag1) {
//							vertexconsumer = ItemRenderer.getCompassFoilBufferDirect(source, rendertype,
//									posestack$pose);
//						} else {
//							vertexconsumer = ItemRenderer.getCompassFoilBuffer(source, rendertype, posestack$pose);
//						}
//
//						matrix.popPose();
//					} else if (flag1) {
//						vertexconsumer = ItemRenderer.getFoilBufferDirect(source, rendertype, true, stack.hasFoil());
//					} else {
//						vertexconsumer = ItemRenderer.getFoilBuffer(source, rendertype, true, stack.hasFoil());
//					}
//
//					renderItem.renderModelLists(p_115151_, stack, x, y, matrix,
//							new CustomVertexConsumer(vertexconsumer, colorAction));
//				}
//			} else {
//				net.minecraftforge.client.RenderProperties.get(stack).getItemStackRenderer().renderByItem(stack, type,
//						matrix, source, x, y);
//			}
//
//			matrix.popPose();
//		}
//	}

	/** @param colorStr e.g. "#FFFFFF" */
	public static float[] hex2Rgb(String colorStr) {
		return new float[] { //
				Integer.valueOf(colorStr.substring(0, 2), 16) / 256f, //
				Integer.valueOf(colorStr.substring(2, 4), 16) / 256f, //
				Integer.valueOf(colorStr.substring(4, 6), 16) / 256f //
		};
	}

	public static void colorBlit(PoseStack pMatrixStack, int pX, int pY, float pUOffset, float pVOffset, int pUWidth,
			int pVHeight, int color) {
		innerBlit(pMatrixStack, pX, pX + pUWidth, pY, pY + pVHeight, 0, pUWidth, pVHeight, pUOffset, pVOffset, pUWidth,
				pVHeight, color);
	}

	private static void innerBlit(PoseStack pMatrixStack, int pX1, int pX2, int pY1, int pY2, int pBlitOffset,
			int pUWidth, int pVHeight, float pUOffset, float pVOffset, int pTextureWidth, int pTextureHeight,
			int color) {
		innerBlit(pMatrixStack.last().pose(), pX1, pX2, pY1, pY2, pBlitOffset, (pUOffset + 0.0F) / pTextureWidth,
				(pUOffset + pUWidth) / pTextureWidth, (pVOffset + 0.0F) / pTextureHeight,
				(pVOffset + pVHeight) / pTextureHeight, color);
	}

	private static void innerBlit(Matrix4f pMatrix, int pX1, int pX2, int pY1, int pY2, int pBlitOffset, float pMinU,
			float pMaxU, float pMinV, float pMaxV, int color) {
		RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
		BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
		bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX);
		bufferbuilder.vertex(pMatrix, pX1, pY2, pBlitOffset)
				.color(color >> 16 & 0xFF, color >> 8 & 0xFF, color & 0xFF, 255).uv(pMinU, pMaxV).endVertex();
		bufferbuilder.vertex(pMatrix, pX2, pY2, pBlitOffset)
				.color(color >> 16 & 0xFF, color >> 8 & 0xFF, color & 0xFF, 255).uv(pMaxU, pMaxV).endVertex();
		bufferbuilder.vertex(pMatrix, pX2, pY1, pBlitOffset)
				.color(color >> 16 & 0xFF, color >> 8 & 0xFF, color & 0xFF, 255).uv(pMaxU, pMinV).endVertex();
		bufferbuilder.vertex(pMatrix, pX1, pY1, pBlitOffset)
				.color(color >> 16 & 0xFF, color >> 8 & 0xFF, color & 0xFF, 255).uv(pMinU, pMinV).endVertex();
		bufferbuilder.end();
		BufferUploader.end(bufferbuilder);
	}

}
