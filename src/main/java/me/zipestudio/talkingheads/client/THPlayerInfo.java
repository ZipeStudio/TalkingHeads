package me.zipestudio.talkingheads.client;

import lombok.Getter;
import lombok.Setter;
import su.plo.voice.proto.data.player.VoicePlayerInfo;

import java.util.UUID;

@Setter
@Getter
public class THPlayerInfo {
    private VoicePlayerInfo playerInfo;
    private double playerVolume;

    public THPlayerInfo(VoicePlayerInfo playerInfo, double playerVolume) {
        this.playerInfo = playerInfo;
        this.playerVolume = playerVolume;
    }

}
