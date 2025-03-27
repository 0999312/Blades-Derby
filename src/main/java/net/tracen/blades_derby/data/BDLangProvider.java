package net.tracen.blades_derby.data;

import java.util.function.Supplier;

import cn.mcmod_mmf.mmlib.data.AbstractLangProvider;
import mods.flammpfeil.slashblade.registry.slashblade.SlashBladeDefinition;
import net.minecraft.Util;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.tracen.blades_derby.BladesDerby;
import net.tracen.blades_derby.data.builtin.BuiltInSlashBladeRegistry;
import net.tracen.blades_derby.data.builtin.BuiltInSupportCardRegistry;
import net.tracen.blades_derby.umaskill.UmaSkillRegistry;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.registry.skills.UmaSkill;
import net.tracen.umapyoi.registry.training.card.SupportCard;

public class BDLangProvider extends AbstractLangProvider {

	public BDLangProvider(PackOutput gen) {
		super(gen, BladesDerby.MODID, "en_us");
	}

	@Override
	protected void addTranslations() {
		addSlashBlade(BuiltInSlashBladeRegistry.UMA_HISHI, "Miracle-Rhombus-");
		addSlashBlade(BuiltInSlashBladeRegistry.UMA_BLACK, "Muteki-Black General-");
		
		addSlashBlade(BuiltInSlashBladeRegistry.UMA_ODACHI_HARU, "Odachi-Haru-");
		addSlashBlade(BuiltInSlashBladeRegistry.UMA_ODACHI_HALO, "Odachi-Halo-");
		
		addSkill(UmaSkillRegistry.IAIDO, "Iaido");
        add(Util.makeDescriptionId("umadata", new ResourceLocation(Umapyoi.MODID, "saint_lite")), "Saint Lite");
        add("se.blades_derby.uma_special", "Uma Soul Resonance");
		addSupportCard(BuiltInSupportCardRegistry.SAINT_LITE_IAIDO, "[Clean Cut] Saint Lite");
	}
	
    private void addSupportCard(ResourceKey<SupportCard> data, String name) {
        this.addSupportCard(data.location(), name);
    }
    
    private void addSupportCard(ResourceLocation key, String name) {
        add(Util.makeDescriptionId("support_card", key) + ".name", name);
    }

	private void addSlashBlade(ResourceKey<SlashBladeDefinition> data, String name) {
		this.addSlashBlade(data.location(), name);
	}

	private void addSlashBlade(ResourceLocation key, String name) {
		add(Util.makeDescriptionId("item", key), name);
	}
	

    private void addSkill(Supplier<UmaSkill> key, String name) {
        addSkill(key.get(), name);
    }

    private void addSkill(UmaSkill key, String name) {
        add(key.getDescriptionId(), name);
    }

}
