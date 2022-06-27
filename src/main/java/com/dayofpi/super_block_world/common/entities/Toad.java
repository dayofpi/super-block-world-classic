package com.dayofpi.super_block_world.common.entities;

public interface Toad {
    boolean isCheering();

    boolean isScared();

    boolean isWaving();

    void setToadState(int state);

    int getAttentionCooldown();
}
