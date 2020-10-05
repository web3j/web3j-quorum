/*
 * Copyright 2020 Web3 Labs Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.web3j.quorum;

import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

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
