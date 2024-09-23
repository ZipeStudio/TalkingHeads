package me.zipestudio.talkingheads.client;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class THPlayer {

    private double playerVolume;
    private UUID playerUuid;

    public THPlayer(UUID uuid, double volume) {
        setPlayerUuid(uuid);
        setPlayerVolume(volume);
    }

}
