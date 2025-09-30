package dev.su5ed.somnia;

import dev.su5ed.somnia.capability.CapabilityFatigue;
import dev.su5ed.somnia.capability.FatigueStore;
import dev.su5ed.somnia.effect.AwakeningEffect;
import dev.su5ed.somnia.effect.InsomniaEffect;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.brewing.BrewingRecipeRegistry;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

@EventBusSubscriber
public final class SomniaObjects {
    private static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, SomniaAwoken.MODID);
    private static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(BuiltInRegistries.POTION, SomniaAwoken.MODID);
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, SomniaAwoken.MODID);

    public static final Holder<MobEffect> AWAKENING_EFFECT = MOB_EFFECTS.register("awakening", AwakeningEffect::new);
    public static final Holder<MobEffect> INSOMNIA_EFFECT = MOB_EFFECTS.register("insomnia", InsomniaEffect::new);

    public static final Holder<Potion> AWAKENING_POTION = POTIONS.register("awakening", () -> new Potion("awakening", new MobEffectInstance(AWAKENING_EFFECT, 2400)));
    public static final Holder<Potion> LONG_AWAKENING_POTION = POTIONS.register("long_awakening", () -> new Potion("awakening", new MobEffectInstance(AWAKENING_EFFECT, 3600)));
    public static final Holder<Potion> STRONG_AWAKENING_POTION = POTIONS.register("strong_awakening", () -> new Potion("awakening", new MobEffectInstance(AWAKENING_EFFECT, 2400, 1)));

    public static final Holder<Potion> INSOMNIA_POTION = POTIONS.register("insomnia", () -> new Potion("insomnia", new MobEffectInstance(INSOMNIA_EFFECT, 1800)));
    public static final Holder<Potion> LONG_INSOMNIA_POTION = POTIONS.register("long_insomnia", () -> new Potion("insomnia", new MobEffectInstance(INSOMNIA_EFFECT, 3000)));
    public static final Holder<Potion> STRONG_INSOMNIA_POTION = POTIONS.register("strong_insomnia", () -> new Potion("insomnia", new MobEffectInstance(INSOMNIA_EFFECT, 1800, 1)));

    public static final Supplier<AttachmentType<FatigueStore>> FATIGUE_ATTACHMENT = ATTACHMENT_TYPES.register(
        "fatigue_store", () -> AttachmentType.builder(FatigueStore::new).serialize(FatigueStore.CODEC).copyOnDeath().build());

    static void register(IEventBus bus) {
        MOB_EFFECTS.register(bus);
        POTIONS.register(bus);
        ATTACHMENT_TYPES.register(bus);
    }

    static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerEntity(CapabilityFatigue.INSTANCE, EntityType.PLAYER, (player, unused) -> player.getData(FATIGUE_ATTACHMENT));
    }

    static void registerCommands(RegisterCommandsEvent event) {
        SomniaCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    static void registerBrewingRecipes(RegisterBrewingRecipesEvent event) {
        var b = event.getBuilder();
        addBrewingRecipe(b, Potions.NIGHT_VISION, Items.GLISTERING_MELON_SLICE, SomniaObjects.AWAKENING_POTION);
        addBrewingRecipe(b, Potions.LONG_NIGHT_VISION, Items.GLISTERING_MELON_SLICE, SomniaObjects.LONG_AWAKENING_POTION);
        addBrewingRecipe(b, Potions.NIGHT_VISION, Items.BLAZE_POWDER, SomniaObjects.STRONG_AWAKENING_POTION);

        addBrewingRecipe(b, SomniaObjects.AWAKENING_POTION, Items.FERMENTED_SPIDER_EYE, SomniaObjects.INSOMNIA_POTION);
        addBrewingRecipe(b, SomniaObjects.LONG_AWAKENING_POTION, Items.FERMENTED_SPIDER_EYE, SomniaObjects.LONG_INSOMNIA_POTION);
        addBrewingRecipe(b, SomniaObjects.STRONG_AWAKENING_POTION, Items.FERMENTED_SPIDER_EYE, SomniaObjects.STRONG_INSOMNIA_POTION);
    }

    private static void addBrewingRecipe(PotionBrewing.Builder b, Holder<Potion> input, Item ingredient, Holder<Potion> output) {
        ItemStack outputStack = PotionContents.createItemStack(Items.POTION, output);
        b.addRecipe(Ingredient.of(PotionContents.createItemStack(Items.POTION, input)), Ingredient.of(ingredient), outputStack);
    }

    private SomniaObjects() {}
}
