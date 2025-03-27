package net.tracen.blades_derby.data.builtin;

import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.tracen.blades_derby.umaskill.UmaSkillRegistry;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.registry.TrainingSupportRegistry;
import net.tracen.umapyoi.registry.training.SupportType;
import net.tracen.umapyoi.registry.training.card.SupportCard;
import net.tracen.umapyoi.registry.training.card.SupportEntry;
import net.tracen.umapyoi.utils.GachaRanking;
import net.tracen.umapyoi.utils.UmaSkillUtils;

public class BuiltInSupportCardRegistry {

    public static final ResourceKey<SupportCard> SAINT_LITE_IAIDO = register("saint_lite_iaido");

    public static void registerAll(BootstapContext<SupportCard> bootstrap) {
        bootstrap.register(SAINT_LITE_IAIDO,
                SupportCard.Builder.create()
                .ranking(GachaRanking.SSR)
                .maxDamage(5)
                .supportType(SupportType.FRIENDSHIP)
                .addSupporter(new ResourceLocation(Umapyoi.MODID, "saint_lite"))
                .addSupport(new SupportEntry(TrainingSupportRegistry.STRENGTH_SUPPORT.getId(), 2))
                .addSupport(UmaSkillUtils.getSkillSupportEnrty(UmaSkillRegistry.IAIDO.getId()))
                .build());
    }
    
    private static ResourceKey<SupportCard> register(String id) {
        ResourceKey<SupportCard> loc = ResourceKey.create(SupportCard.REGISTRY_KEY, new ResourceLocation(Umapyoi.MODID, id));
        return loc;
    }
}
