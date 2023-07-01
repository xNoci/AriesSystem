package de.ariesbuildings.utils;

import com.cryptomorin.xseries.XMaterial;
import com.google.common.collect.Maps;
import de.ariesbuildings.I18n;
import lombok.Getter;
import me.noci.quickutilities.utils.QuickItemStack;
import me.noci.quickutilities.utils.SkullItem;

import java.util.HashMap;
import java.util.Optional;

public enum CustomBlock {

    //FURNACE
    LIT_FURNACE("d17b8b43f8c4b5cfeb919c9f8fe93f26ceb6d2b133c2ab1eb339bd6621fd309c"),
    LIT_BLAST_FURNACE("9fbb6eea57547b2588baf8af649d9116ff08cac56dd2140b3c49577799bad7c"),
    LIT_SMOKER("c5c5b24cd5efa07d31beea655d7ff972e6f47cdb898be4404363deeba43ba5d"),
    //PAINTING
    PAINTING_ELEMENT_EARTH(XMaterial.PAINTING),
    PAINTING_ELEMENT_WIND(XMaterial.PAINTING),
    PAINTING_ELEMENT_WATER(XMaterial.PAINTING),
    PAINTING_ELEMENT_FIRE(XMaterial.PAINTING),
    //REDSTONE
    POWERED_REDSTONE_LAMP("a919dd72e38cec369c6508686896ccb84100fd027c4f60a681d16a7640329cc"),
    //PORTAL
    NETHER_PORTAL("b0bfc2577f6e26c6c6f7365c2c4076bccee653124989382ce93bca4fc9e39b"),
    END_PORTAL("5639d4079d6b7c0a913cff608ec43fa39a72fca5d64fb22700b1cc6c46cc69c2"),
    END_PORTAL_GATEWAY("5639d4079d6b7c0a913cff608ec43fa39a72fca5d64fb22700b1cc6c46cc69c2"),
    //OTHER
    PISTON_HEAD("13957fc91378b765ce5340f0fe82717a60bdcc7335dfbc0aa9892c5e5a54e6cd"),
    INVISIBLE_ITEM_FRAME(XMaterial.ITEM_FRAME),
    COMMAND_BLOCK(XMaterial.COMMAND_BLOCK),
    BARRIER(XMaterial.BARRIER),
    LIGHT(XMaterial.LIGHT),
    STRUCTURE_VOID(XMaterial.STRUCTURE_VOID);

    private static final HashMap<String, CustomBlock> BLOCKS = Maps.newHashMap();

    static {
        for (CustomBlock value : values()) {
            BLOCKS.put(value.getDisplayItem().getDisplayName(), value);
        }
    }

    @Getter private final QuickItemStack displayItem;

    CustomBlock(String headID) {
        this(SkullItem.getSkull(headID));
    }

    CustomBlock(XMaterial material) {
        this(new QuickItemStack(material.parseMaterial()));
    }

    CustomBlock(QuickItemStack itemStack) {
        this.displayItem = itemStack;
        this.displayItem.setDisplayName(I18n.translate("custom_block.item.%s.displayname".formatted(name().toLowerCase())));
        this.displayItem.addItemFlags();
    }

    public static Optional<CustomBlock> matchCustomBlock(QuickItemStack itemStack) {
        return Optional.ofNullable(BLOCKS.get(itemStack.getDisplayName()));
    }

}
