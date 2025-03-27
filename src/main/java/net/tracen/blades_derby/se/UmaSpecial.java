package net.tracen.blades_derby.se;

import mods.flammpfeil.slashblade.capability.slashblade.ISlashBladeState;
import mods.flammpfeil.slashblade.event.SlashBladeEvent;
import mods.flammpfeil.slashblade.registry.specialeffects.SpecialEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.tracen.umapyoi.api.UmapyoiAPI;

@EventBusSubscriber
public class UmaSpecial extends SpecialEffect {

	public UmaSpecial() {
		super(0, true, true);
	}

	@SubscribeEvent
	public static void onSlashBladeUpdate(SlashBladeEvent.UpdateEvent event) {
		ISlashBladeState state = event.getSlashBladeState();
		if(state.hasSpecialEffect(SpecialEffectRegistry.UMA_SPECIAL.getId())) {
			if (!(event.getEntity() instanceof LivingEntity)) {
				return;
			}
			
			if(!event.isSelected())
				return;
			
			LivingEntity living = (LivingEntity) event.getEntity();
			
			if(UmapyoiAPI.getUmaSoul(living).isEmpty()){
				living.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 20, 1));
				living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 1));
				living.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 20, 2));
			}
				
		}
	}
	
}
