package com.dayofpi.sbw_main.world.registry;

import com.dayofpi.sbw_main.Main;
import com.dayofpi.sbw_main.world.structure_feature.ToadHouseGenerator;
import com.dayofpi.sbw_main.world.structure_feature.WarpPortalGenerator;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModStructurePieces {
    public static final StructurePieceType TOAD_HOUSE = ToadHouseGenerator.Piece::new;
    public static final StructurePieceType WARP_PORTAL = WarpPortalGenerator.Piece::new;

    private static void addPiece(String string, StructurePieceType type) {
        Registry.register(Registry.STRUCTURE_PIECE, new Identifier(Main.MOD_ID, string), type);
    }

    public static void registerStructurePiece() {
        addPiece("toad_house", TOAD_HOUSE);
        addPiece("warp_portal", WARP_PORTAL);
    }
}
