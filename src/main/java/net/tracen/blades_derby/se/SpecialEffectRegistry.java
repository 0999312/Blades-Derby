package net.tracen.blades_derby.se;

import mods.flammpfeil.slashblade.registry.specialeffects.SpecialEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.tracen.blades_derby.BladesDerby;

public class SpecialEffectRegistry {
	public static final DeferredRegister<SpecialEffect> SPECIAL_EFFECT = DeferredRegister
			.create(SpecialEffect.REGISTRY_KEY, BladesDerby.MODID);
	
    public static final RegistryObject<SpecialEffect> UMA_SPECIAL = SPECIAL_EFFECT.register("uma_special", 
    		UmaSpecial::new);
}
