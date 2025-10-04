package dev.su5ed.somnia;

import dev.su5ed.somnia.capability.CapabilityFatigue;
import dev.su5ed.somnia.capability.Fatigue;
import dev.su5ed.somnia.capability.FatigueStore;
import dev.su5ed.somnia.effect.AwakeningEffect;
import dev.su5ed.somnia.effect.InsomniaEffect;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.brewing.BrewingRecipeRegisterEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber
public final class SomniaObjects {
    private static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, SomniaAwoken.MODID);
    private static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, SomniaAwoken.MODID);

    public static final RegistryObject<MobEffect> AWAKENING_EFFECT = MOB_EFFECTS.register("awakening", AwakeningEffect::new);
    public static final RegistryObject<MobEffect> INSOMNIA_EFFECT = MOB_EFFECTS.register("insomnia", InsomniaEffect::new);

    public static final RegistryObject<Potion> AWAKENING_POTION = POTIONS.register("awakening", () -> new Potion("awakening", new MobEffectInstance(AWAKENING_EFFECT.getHolder().get(), 2400)));
    public static final RegistryObject<Potion> LONG_AWAKENING_POTION = POTIONS.register("long_awakening", () -> new Potion("awakening", new MobEffectInstance(AWAKENING_EFFECT.getHolder().get(), 3600)));
    public static final RegistryObject<Potion> STRONG_AWAKENING_POTION = POTIONS.register("strong_awakening", () -> new Potion("awakening", new MobEffectInstance(AWAKENING_EFFECT.getHolder().get(), 2400, 1)));

    public static final RegistryObject<Potion> INSOMNIA_POTION = POTIONS.register("insomnia", () -> new Potion("insomnia", new MobEffectInstance(INSOMNIA_EFFECT.getHolder().get(), 1800)));
    public static final RegistryObject<Potion> LONG_INSOMNIA_POTION = POTIONS.register("long_insomnia", () -> new Potion("insomnia", new MobEffectInstance(INSOMNIA_EFFECT.getHolder().get(), 3000)));
    public static final RegistryObject<Potion> STRONG_INSOMNIA_POTION = POTIONS.register("strong_insomnia", () -> new Potion("insomnia", new MobEffectInstance(INSOMNIA_EFFECT.getHolder().get(), 1800, 1)));

    static void register(IEventBus bus) {
        MOB_EFFECTS.register(bus);
        POTIONS.register(bus);
    }

    @SubscribeEvent
    static void attachEntityCapabilities(AttachCapabilitiesEvent<Entity> e) {
        if (!(e.getObject() instanceof Player))
            return;
        e.addCapability(CapabilityFatigue.NAME, new ICapabilityProvider() {
            private final LazyOptional<Fatigue> fatigue = LazyOptional.of(FatigueStore::new);
            @Override
            public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
                return CapabilityFatigue.INSTANCE.orEmpty(cap, fatigue);
            }
        });
    }

    @SubscribeEvent
    static void registerBrewingRecipes(BrewingRecipeRegisterEvent e) {
        var b = e.getBuilder();
        addBrewingRecipe(b, Potions.NIGHT_VISION, Items.GLISTERING_MELON_SLICE, SomniaObjects.AWAKENING_POTION.getHolder().get());
        addBrewingRecipe(b, Potions.LONG_NIGHT_VISION, Items.GLISTERING_MELON_SLICE, SomniaObjects.LONG_AWAKENING_POTION.getHolder().get());
        addBrewingRecipe(b, Potions.NIGHT_VISION, Items.BLAZE_POWDER, SomniaObjects.STRONG_AWAKENING_POTION.getHolder().get());

        addBrewingRecipe(b, SomniaObjects.AWAKENING_POTION.getHolder().get(), Items.FERMENTED_SPIDER_EYE, SomniaObjects.INSOMNIA_POTION.getHolder().get());
        addBrewingRecipe(b, SomniaObjects.LONG_AWAKENING_POTION.getHolder().get(), Items.FERMENTED_SPIDER_EYE, SomniaObjects.LONG_INSOMNIA_POTION.getHolder().get());
        addBrewingRecipe(b, SomniaObjects.STRONG_AWAKENING_POTION.getHolder().get(), Items.FERMENTED_SPIDER_EYE, SomniaObjects.STRONG_INSOMNIA_POTION.getHolder().get());
    }

    private static void addBrewingRecipe(PotionBrewing.Builder b, Holder<Potion> input, Item ingredient, Holder<Potion> output) {
        b.addMix(input, ingredient, output);
    }

    private SomniaObjects() {}
}
