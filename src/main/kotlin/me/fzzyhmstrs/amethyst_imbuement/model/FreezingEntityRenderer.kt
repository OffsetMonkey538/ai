package me.fzzyhmstrs.amethyst_imbuement.model

import me.fzzyhmstrs.amethyst_imbuement.entity.FreezingEntity
import net.minecraft.client.render.OverlayTexture
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.entity.EntityRenderer
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.entity.model.EntityModelLayers
import net.minecraft.client.render.entity.model.ShulkerBulletEntityModel
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.projectile.ShulkerBulletEntity
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3f

@Suppress("PrivatePropertyName", "SpellCheckingInspection")
class FreezingEntityRenderer(context: EntityRendererFactory.Context): EntityRenderer<FreezingEntity>(context) {
    private val TEXTURE = Identifier("textures/entity/shulker/spark.png")
    private val LAYER = RenderLayer.getEntityTranslucent(TEXTURE)
    private val model: ShulkerBulletEntityModel<ShulkerBulletEntity> = ShulkerBulletEntityModel(context.getPart(EntityModelLayers.SHULKER_BULLET))

    override fun getBlockLight(shulkerBulletEntity: FreezingEntity, blockPos: BlockPos): Int {
        return 15
    }

    override fun render(
        freezingEntity: FreezingEntity,
        f: Float,
        g: Float,
        matrixStack: MatrixStack,
        vertexConsumerProvider: VertexConsumerProvider,
        i: Int
    ) {
        matrixStack.push()
        val k = freezingEntity.age.toFloat() + g
        matrixStack.translate(0.0, 0.15, 0.0)
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(MathHelper.sin(k * 0.1f) * 180.0f))
        matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(MathHelper.cos(k * 0.1f) * 180.0f))
        matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.sin(k * 0.15f) * 360.0f))
        matrixStack.scale(-0.5f, -0.5f, 0.5f)
        val vertexConsumer = vertexConsumerProvider.getBuffer(model.getLayer(TEXTURE))
        model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f)
        matrixStack.scale(1.5f, 1.5f, 1.5f)
        val vertexConsumer2 = vertexConsumerProvider.getBuffer(LAYER)
        model.render(matrixStack, vertexConsumer2, i, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 0.15f)
        matrixStack.pop()
        super.render(freezingEntity, f, g, matrixStack, vertexConsumerProvider, i)
    }

    override fun getTexture(missileEntityCopy: FreezingEntity): Identifier {
        return TEXTURE
    }
}