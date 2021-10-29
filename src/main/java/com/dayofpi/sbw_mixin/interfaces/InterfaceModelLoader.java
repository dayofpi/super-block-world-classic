package com.dayofpi.sbw_mixin.interfaces;

import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(EntityModelLoader.class)
public interface InterfaceModelLoader {
    @Accessor
    Map<EntityModelLayer, TexturedModelData> getModelParts();

    @Accessor("modelParts")
    void setModelParts(Map map);

}