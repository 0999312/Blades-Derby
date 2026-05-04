package net.tracen.blades_derby.umaskill;

import mods.flammpfeil.slashblade.registry.ModAttributes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.tracen.umapyoi.events.ApplyUmasoulAttributeEvent;
import net.tracen.umapyoi.utils.UmaSoulUtils;

@EventBusSubscriber(modid = "blades_derby")
public class UmaSkillEvents {
    @SubscribeEvent
    public static void testPassiveSkill_im(ApplyUmasoulAttributeEvent event) {
        var soul = event.getUmaSoul();
        if (UmaSoulUtils.hasSkill(soul, UmaSkillRegistry.IAIDO.getId())) {
        	event.getAttributes().put(BuiltInRegistries.ATTRIBUTE.wrapAsHolder(ModAttributes.getSlashBladeDamage()), 
    			new AttributeModifier(ResourceLocation.fromNamespaceAndPath("blades_derby", "passive_speed_bonus"),
    			0.1D, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
    			);
        }
    }
}
