package me.fzzyhmstrs.amethyst_imbuement.scepter

import me.fzzyhmstrs.amethyst_imbuement.entity.MissileEntity
import me.fzzyhmstrs.amethyst_imbuement.scepter.base_augments.SummonProjectileAugment
import me.fzzyhmstrs.amethyst_imbuement.util.ScepterObject
import me.fzzyhmstrs.amethyst_imbuement.util.SpellType
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.projectile.ProjectileEntity
import net.minecraft.item.Items
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents
import net.minecraft.world.World

class SoulMissileAugment(tier: Int, maxLvl: Int, vararg slot: EquipmentSlot): SummonProjectileAugment(tier, maxLvl, *slot) {

    override fun entityClass(world: World, user: LivingEntity, level: Int): ProjectileEntity {
        val me = MissileEntity(world, user, true)
        me.setVelocity(user,user.pitch,user.yaw,0.0f,
            2.0f,
            0.1f)
        return me
    }

    override fun soundEvent(): SoundEvent {
        return SoundEvents.ENTITY_ENDER_DRAGON_SHOOT
    }

    override fun augmentStat(imbueLevel: Int): ScepterObject.AugmentDatapoint {
        return ScepterObject.AugmentDatapoint(SpellType.FURY,16,2,1,imbueLevel,0,Items.SOUL_SAND)
    }

}