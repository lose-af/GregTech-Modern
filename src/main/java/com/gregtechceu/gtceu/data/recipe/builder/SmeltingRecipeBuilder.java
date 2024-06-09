package com.gregtechceu.gtceu.data.recipe.builder;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;

import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * @author KilaBash
 * @date 2023/2/21
 * @implNote SmeltingRecipeBuilder
 */
@Accessors(chain = true, fluent = true)
public class SmeltingRecipeBuilder {

    private Ingredient input;
    @Setter
    protected String group;
    @Setter
    protected CookingBookCategory category = CookingBookCategory.MISC;

    private ItemStack output = ItemStack.EMPTY;
    @Setter
    private float experience;
    @Setter
    private int cookingTime;
    @Setter
    protected ResourceLocation id;

    public SmeltingRecipeBuilder(@Nullable ResourceLocation id) {
        this.id = id;
    }

    public SmeltingRecipeBuilder input(TagKey<Item> itemStack) {
        return input(Ingredient.of(itemStack));
    }

    public SmeltingRecipeBuilder input(ItemStack itemStack) {
        if (!itemStack.getComponents().isEmpty()) {
            input = DataComponentIngredient.of(true, itemStack);
        } else {
            input = Ingredient.of(itemStack);
        }
        return this;
    }

    public SmeltingRecipeBuilder input(ItemLike itemLike) {
        return input(Ingredient.of(itemLike));
    }

    public SmeltingRecipeBuilder input(Ingredient ingredient) {
        input = ingredient;
        return this;
    }

    public SmeltingRecipeBuilder output(ItemStack itemStack) {
        this.output = itemStack.copy();
        return this;
    }

    public SmeltingRecipeBuilder output(ItemStack itemStack, int count) {
        this.output = itemStack.copy();
        this.output.setCount(count);
        return this;
    }

    protected ResourceLocation defaultId() {
        return BuiltInRegistries.ITEM.getKey(output.getItem());
    }

    private SmeltingRecipe create() {
        return new SmeltingRecipe(Objects.requireNonNullElse(this.group, ""), this.category, this.input, this.output,
                this.experience, this.cookingTime);
    }

    public void save(RecipeOutput consumer) {
        var recipeId = id == null ? defaultId() : id;
        consumer.accept(ResourceLocation.fromNamespaceAndPath(recipeId.getNamespace(), "smelting/" + recipeId.getPath()), create(),
                null);
    }
}
