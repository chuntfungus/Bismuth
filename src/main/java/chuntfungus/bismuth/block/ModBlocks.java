package chuntfungus.bismuth.block;

import chuntfungus.bismuth.Bismuth;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;

public class ModBlocks {

    private static Block register(String name, Function<BlockBehaviour.Properties, Block> blockFactory, BlockBehaviour.Properties settings, boolean shouldRegisterItem) {
        // Create block registry
        ResourceKey<Block> blockKey = keyOfBlock(name);

        // Create block instance
        Block block = blockFactory.apply(settings.setId(blockKey));

        // Sometimes, you don't want to register an item for a block (like moving pistons, or end portal gateways)
        if (shouldRegisterItem) {
            // Items need to be registered with a different registry key, but ID can stay the same.
            ResourceKey<Item> itemKey = keyOfItem(name);

            BlockItem blockItem = new BlockItem(block, new Item.Properties().setId(itemKey).useBlockDescriptionPrefix());
            Registry.register(BuiltInRegistries.ITEM, itemKey, blockItem);
        }

        return Registry.register(BuiltInRegistries.BLOCK, blockKey, block);
    }

    private static ResourceKey<Block> keyOfBlock(String name) {
        return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(Bismuth.MOD_ID, name));
    }

    private static ResourceKey<Item> keyOfItem(String name) {
        return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Bismuth.MOD_ID, name));
    }

    // BLOCKS
    public static final Block BISMUTH_ORE = register(
      "bismuth_ore",
      Block::new,
      BlockBehaviour.Properties.of()
              .sound(SoundType.STONE)
              .requiresCorrectToolForDrops()
              .strength(3),
      true
    );

    // initialize mod blocks
    public static void initialize() {
        // adds block to creative "building blocks" tab
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.BUILDING_BLOCKS).register((itemGroup) -> {
            itemGroup.accept(ModBlocks.BISMUTH_ORE.asItem());
        });

    }

}
