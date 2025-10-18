package me.zipestudio.talkingheads.utils.talkingheads;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class THPlayerProfile {

    private double playerVolume;
    private UUID playerUuid;

    public THPlayerProfile(UUID uuid, float volume) {
        setPlayerUuid(uuid);
        setPlayerVolume(volume);
    }

}
