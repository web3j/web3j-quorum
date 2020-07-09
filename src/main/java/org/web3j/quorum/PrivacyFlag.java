package org.web3j.quorum;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum PrivacyFlag {
    STANDARD_PRIVATE(0),
    PARTY_PROTECTION(1),
    PRIVATE_STATE_VALIDATION(3);

    private final int value;

    PrivacyFlag(int value) {
        this.value = value;
    }

    @JsonCreator
    public static PrivacyFlag fromFlag(int privacyFlag) {
        return Stream.of(PrivacyFlag.values())
                .filter(v -> v.getValue() == privacyFlag)
                .findFirst()
                .orElse(PrivacyFlag.STANDARD_PRIVATE);
    }

    @JsonValue
    public int getValue() {
        return this.value;
    }
}