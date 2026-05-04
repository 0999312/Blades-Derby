package net.tracen.blades_derby.umaskill;

import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.tracen.blades_derby.BladesDerby;
import net.tracen.umapyoi.registry.skills.UmaSkill;
import net.tracen.umapyoi.registry.skills.passive.PassiveSkill;

public class UmaSkillRegistry {
	public static final DeferredRegister<UmaSkill> UMA_SKILLS = DeferredRegister
			.create(UmaSkill.REGISTRY_KEY, BladesDerby.MODID);
	
	public static final DeferredHolder<UmaSkill, PassiveSkill> IAIDO = UMA_SKILLS.register("iaido",
            () -> new PassiveSkill(new UmaSkill.Builder().level(2).requiredWisdom(5)));
	
	public static final DeferredHolder<UmaSkill, PassiveSkill> BREATH_OF_NATURE = UMA_SKILLS.register("breath_of_nature",
            () -> new PassiveSkill(new UmaSkill.Builder().level(2).requiredWisdom(5)));
}
