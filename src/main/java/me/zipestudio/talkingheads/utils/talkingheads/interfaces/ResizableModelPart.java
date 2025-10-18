package me.zipestudio.talkingheads.utils.talkingheads.interfaces;

public interface ResizableModelPart {

	void talkingHeads$setSize(double sizeX, double sizeY, double sizeZ);
	double talkingHeads$getSizeX();
	double talkingHeads$getSizeY();
	double talkingHeads$getSizeZ();

	void talkingHeads$setDefaultSize();

	default double talkingHeads$getDefaultSize() {
		return 1.0F;
	}

}

