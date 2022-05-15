package me.fzzyhmstrs.amethyst_imbuement.scepter

import me.fzzyhmstrs.amethyst_imbuement.augment.base_augments.BaseAugment
import me.fzzyhmstrs.amethyst_imbuement.registry.RegisterStatus
import me.fzzyhmstrs.amethyst_imbuement.scepter.base_augments.MinorSupportAugment
import me.fzzyhmstrs.amethyst_imbuement.util.SpellType
import net.minecraft.entity.Entity
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.passive.GolemEntity
import net.minecraft.entity.passive.PassiveEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Items
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents
import net.minecraft.world.World

class CleanseAugment(tier: Int, maxLvl: Int, vararg slot: EquipmentSlot): MinorSupportAugment(tier,maxLvl, *slot) {

    override fun supportEffect(world: World, target: Entity?, user: LivingEntity?, level: Int): Boolean {
        if(target != null) {
            if (target is PassiveEntity || target is GolemEntity || target is PlayerEntity) {
                val statuses: MutableList<StatusEffectInstance> = mutableListOf()
                for (effect in (target as LivingEntity).statusEffects){
                    if (effect.effectType.isBeneficial) continue
                    statuses.add(effect)
                }
                for (effect in statuses) {
                    target.removeStatusEffect(effect.effectType)
                }
                target.fireTicks = 0
                BaseAugment.addStatusToQueue(target,RegisterStatus.IMMUNITY,300,0)
                world.playSound(null, target.blockPos, soundEvent(), SoundCategory.PLAYERS, 0.6F, 1.2F)
                return true
            }
        }
        return if(user != null){
            if (user is PlayerEntity) {
                val statuses: MutableList<StatusEffectInstance> = mutableListOf()
                for (effect in user.statusEffects){
                    if (effect.effectType.isBeneficial) continue
                    statuses.add(effect)
                }
                for (effect in statuses) {
                    user.removeStatusEffect(effect.effectType)
                }
                user.fireTicks = 0
                BaseAugment.addStatusToQueue(user,RegisterStatus.IMMUNITY,200,0)
                world.playSound(null, user.blockPos, soundEvent(), SoundCategory.PLAYERS, 0.6F, 1.2F)
                true
            } else {
                false
            }
        } else {
            false
        }
    }

    override fun soundEvent(): SoundEvent {
        return SoundEvents.ENTITY_EVOKER_PREPARE_SUMMON
    }

    override fun augmentStat(imbueLevel: Int): ScepterObject.AugmentDatapoint {
        return ScepterObject.AugmentDatapoint(SpellType.GRACE,600,12,1,imbueLevel,0, Items.MILK_BUCKET)
    }
}