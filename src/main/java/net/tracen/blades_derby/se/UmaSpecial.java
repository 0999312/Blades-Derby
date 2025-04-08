package net.tracen.blades_derby.se;

import mods.flammpfeil.slashblade.capability.slashblade.ISlashBladeState;
import mods.flammpfeil.slashblade.event.SlashBladeEvent;
import mods.flammpfeil.slashblade.registry.specialeffects.SpecialEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.tracen.blades_derby.BladesDerbyConfig;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.utils.UmaSoulUtils;

@EventBusSubscriber
public class UmaSpecial extends SpecialEffect {

	public UmaSpecial() {
		super(30, true, true);
	}

	@SubscribeEvent
	public static void onSlashBladeUpdate(SlashBladeEvent.UpdateEvent event) {
		ISlashBladeState state = event.getSlashBladeState();
		if (state.hasSpecialEffect(SpecialEffectRegistry.UMA_SPECIAL.getId())) {
			if (!(event.getEntity() instanceof LivingEntity)) {
				return;
			}

			if (!event.isSelected())
				return;

			LivingEntity living = (LivingEntity) event.getEntity();

			if (UmapyoiAPI.getUmaSoul(living).isEmpty()) {
				living.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 20, 1));
				living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 1));
				living.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 20, 2));
			}

		}
	}

	@SubscribeEvent
	public static void onSlashBladePowered(SlashBladeEvent.PowerBladeEvent event) {
		LivingEntity user = event.getUser();
		ISlashBladeState state = event.getSlashBladeState();
		if (state.hasSpecialEffect(SpecialEffectRegistry.UMA_SPECIAL.getId())) {
			if (SpecialEffect.isEffective(SpecialEffectRegistry.UMA_SPECIAL.getId(),
					user instanceof Player ? ((Player) user).experienceLevel : 0)) {
				boolean equiped = !UmapyoiAPI.getUmaSoul(user).isEmpty();
				int ap = equiped ?UmaSoulUtils.getActionPoint(UmapyoiAPI.getUmaSoul(user)):0;
				event.setPowered(equiped && ap >= BladesDerbyConfig.getAPCost());
			}
		}
	}
	
	@SubscribeEvent
	public static void onSlashBladeSlash(SlashBladeEvent.DoSlashEvent event) {
		LivingEntity user = event.getUser();
		ISlashBladeState state = event.getSlashBladeState();
		if (state.hasSpecialEffect(SpecialEffectRegistry.UMA_SPECIAL.getId())) {
			if (SpecialEffect.isEffective(SpecialEffectRegistry.UMA_SPECIAL.getId(),
					user instanceof Player ? ((Player) user).experienceLevel : 0)) {
				ItemStack umaSoul = UmapyoiAPI.getUmaSoul(user);
				boolean equiped = !umaSoul.isEmpty();
				int ap = equiped ?UmaSoulUtils.getActionPoint(umaSoul) : 0;
				if(equiped && ap >= BladesDerbyConfig.getAPCost()) {
					UmaSoulUtils.setActionPoint(umaSoul, ap - BladesDerbyConfig.getAPCost());
				}
			}
		}
	}
}
