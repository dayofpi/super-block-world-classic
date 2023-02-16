package com.dayofpi.super_block_world.entity.entities;

public interface Toad {
    boolean isCheering();

    boolean isScared();

    boolean isWaving();

    void setToadState(int state);

    int getAttentionCooldown();

    boolean isToadette();
}
