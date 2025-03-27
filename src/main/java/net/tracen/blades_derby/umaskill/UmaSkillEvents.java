package net.tracen.blades_derby.umaskill;

import mods.flammpfeil.slashblade.registry.ModAttributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.tracen.umapyoi.events.ApplyUmasoulAttributeEvent;
import net.tracen.umapyoi.utils.UmaSoulUtils;

@EventBusSubscriber
public class UmaSkillEvents {
    @SubscribeEvent
    public static void testPassiveSkill_im(ApplyUmasoulAttributeEvent event) {
        var soul = event.getUmaSoul();
        if (UmaSoulUtils.hasSkill(soul, UmaSkillRegistry.IAIDO.getId())) {
        	event.getAttributes().put(ModAttributes.getSlashBladeDamage(), 
    			new AttributeModifier(event.getUUID(), "passive_speed_bonus",
    			0.1D, AttributeModifier.Operation.MULTIPLY_TOTAL)
    			);
        }
    }
}
