/*
 * Copyright 2019 Web3 Labs Ltd.
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
package org.web3j.quorum.methods.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.web3j.quorum.PrivacyFlag;

import java.util.List;

/**
 * Quorum's Transaction object.
 *
 * <p>This is almost identical to the Ethereum {@link
 * org.web3j.protocol.core.methods.request.Transaction} with the exception that it provides the
 * privateFor field and does not contain a gas price.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrivateRawTransaction {

    private List<String> privateFor;
    private PrivacyFlag privacyFlag;

    public PrivateRawTransaction(List<String> privateFor) {
        this(privateFor, null);
    }
    public PrivateRawTransaction(List<String> privateFor, PrivacyFlag privacyMode) {
        this.privateFor = privateFor;
        this.privacyFlag = privacyMode;
    }

    public List<String> getPrivateFor() {
        return privateFor;
    }

    public void setPrivateFor(List<String> privateFor) {
        this.privateFor = privateFor;
    }

    public PrivacyFlag getPrivacyFlag() {
        return privacyFlag;
    }

    public void setPrivacyFlag(PrivacyFlag privacyFlag) {
        this.privacyFlag = privacyFlag;
    }
}
