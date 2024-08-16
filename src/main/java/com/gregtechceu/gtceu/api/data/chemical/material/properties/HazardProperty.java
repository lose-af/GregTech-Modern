package com.gregtechceu.gtceu.api.data.chemical.material.properties;

import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.UnificationEntry;
import com.gregtechceu.gtceu.api.data.medicalcondition.MedicalCondition;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.item.GTBucketItem;
import com.gregtechceu.gtceu.api.item.TagPrefixItem;
import com.gregtechceu.gtceu.api.item.armor.ArmorComponentItem;
import com.gregtechceu.gtceu.common.data.GTMedicalConditions;
import com.gregtechceu.gtceu.config.ConfigHolder;
import com.gregtechceu.gtceu.data.recipe.CustomTags;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;

import lombok.Getter;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * @author h3tR
 * @date 2024/2/12
 * @implNote HazardProperty
 */
public class HazardProperty implements IMaterialProperty<HazardProperty> {

    public static final Codec<HazardProperty> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            HazardTrigger.CODEC.fieldOf("trigger").forGetter(val -> val.hazardTrigger),
            MedicalCondition.CODEC.fieldOf("condition").forGetter(val -> val.condition),
            ExtraCodecs.POSITIVE_FLOAT.fieldOf("progression_multiplier")
                    .forGetter(val -> val.progressionMultiplier),
            Codec.BOOL.fieldOf("apply_to_derivatives").forGetter(val -> val.applyToDerivatives)
    ).apply(instance, HazardProperty::new));

    public MedicalCondition condition;
    public HazardTrigger hazardTrigger;
    public boolean applyToDerivatives;
    public float progressionMultiplier;

    public HazardProperty(HazardTrigger hazardTrigger, MedicalCondition condition, float progressionMultiplier,
                          boolean applyToDerivatives) {
        this.hazardTrigger = hazardTrigger;
        this.condition = condition;
        this.applyToDerivatives = applyToDerivatives;
        this.progressionMultiplier = progressionMultiplier;
    }

    public HazardProperty() {
        this(HazardTrigger.NONE, GTMedicalConditions.NONE, 0.0f, false);
    }

    @Override
    public void verifyProperty(MaterialProperties properties) {}

    public record HazardTrigger(String name, ProtectionType protectionType, Set<TagPrefix> affectedTagPrefixes)
            implements StringRepresentable {

        public static final Map<String, HazardTrigger> ALL_TRIGGERS = new HashMap<>();
        public static final Codec<HazardTrigger> CODEC = Codec.STRING.xmap(ALL_TRIGGERS::get, HazardTrigger::name);

        public static final HazardTrigger INHALATION = new HazardTrigger("inhalation", ProtectionType.MASK,
                TagPrefix.dust, TagPrefix.dustSmall, TagPrefix.dustTiny, TagPrefix.dustPure, TagPrefix.dustImpure);
        public static final HazardTrigger ANY = new HazardTrigger("any", ProtectionType.FULL);
        public static final HazardTrigger SKIN_CONTACT = new HazardTrigger("skin_contact", ProtectionType.HANDS,
                TagPrefix.dust, TagPrefix.dustSmall, TagPrefix.dustTiny);
        public static final HazardTrigger NONE = new HazardTrigger("none", ProtectionType.NONE);

        public HazardTrigger {
            ALL_TRIGGERS.put(name, this);
        }

        public HazardTrigger(String name, ProtectionType protectionType, TagPrefix... tagPrefixes) {
            this(name, protectionType, new HashSet<>());
            affectedTagPrefixes.addAll(Arrays.asList(tagPrefixes));
        }

        public boolean isAffected(TagPrefix prefix) {
            if (affectedTagPrefixes.isEmpty()) return true; // empty list means all prefixes are affected
            return affectedTagPrefixes.contains(prefix);
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }

    public enum ProtectionType {

        MASK(ArmorItem.Type.HELMET),
        HANDS(ArmorItem.Type.CHESTPLATE),
        FULL(ArmorItem.Type.BOOTS, ArmorItem.Type.HELMET, ArmorItem.Type.CHESTPLATE, ArmorItem.Type.LEGGINGS),
        NONE();

        @Getter
        private final Set<ArmorItem.Type> equipmentTypes;

        ProtectionType(ArmorItem.Type... equipmentTypes) {
            this.equipmentTypes = Set.of(equipmentTypes);
        }

        public boolean isProtected(LivingEntity livingEntity) {
            if (this == NONE) {
                return true;
            }
            Set<ArmorItem.Type> correctArmorItems = new HashSet<>();
            for (ArmorItem.Type equipmentType : equipmentTypes) {
                ItemStack armor = livingEntity.getItemBySlot(equipmentType.getSlot());
                if (!armor.isEmpty() && ((armor.getItem() instanceof ArmorComponentItem armorItem &&
                        armorItem.getArmorLogic().isPPE()) ||
                        armor.getTags().anyMatch(tag -> tag.equals(CustomTags.PPE_ARMOR)))) {
                    correctArmorItems.add(equipmentType);
                }
            }
            return correctArmorItems.containsAll(equipmentTypes);
        }
    }

    @Nullable
    public static Material getValidHazardMaterial(ItemStack item) {
        Material material = null;
        TagPrefix prefix = null;
        boolean isFluid = false;
        if (item.getItem() instanceof TagPrefixItem prefixItem) {
            material = prefixItem.material;
            prefix = prefixItem.tagPrefix;
        } else if (item.getItem() instanceof BucketItem bucket) {
            if (ConfigHolder.INSTANCE.gameplay.universalHazards || bucket instanceof GTBucketItem) {
                material = ChemicalHelper.getMaterial(bucket.getFluid());
                isFluid = true;
            }
        } else if (ConfigHolder.INSTANCE.gameplay.universalHazards) {
            UnificationEntry entry = ChemicalHelper.getUnificationEntry(item.getItem());
            if (entry != null && entry.material != null) {
                material = entry.material;
                prefix = entry.tagPrefix;
            }
        }
        if (material == null) {
            return null;
        }
        HazardProperty property = material.getProperty(PropertyKey.HAZARD);
        if (property == null) {
            return null;
        }
        if (!isFluid && !property.hazardTrigger.isAffected(prefix)) {
            return null;
        }
        return material;
    }
}
