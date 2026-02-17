package me.zipestudio.talkingheads.utils.talkingheads.interfaces;

public interface ResizablePlayer {

    void talkingHeads$setSize(double sizeX, double sizeY, double sizeZ);

    double talkingHeads$getSizeX();

    double talkingHeads$getSizeY();

    double talkingHeads$getSizeZ();

    boolean talkingHeads$isDefaults();

    void talkingHeads$setDefaultsSize();

    default double talkingHeads$getDefaultSize() {
        return 1.0;
    }

}