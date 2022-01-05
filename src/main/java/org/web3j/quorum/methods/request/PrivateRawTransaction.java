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

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.web3j.quorum.PrivacyFlag;

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
    private List<String> mandatoryFor;

    public PrivateRawTransaction(List<String> privateFor) {
        this(privateFor, null, null);
    }

    public PrivateRawTransaction(List<String> privateFor, PrivacyFlag privacyFlag) {
        this(privateFor, privacyFlag, null);
    }

    public PrivateRawTransaction(
            List<String> privateFor, PrivacyFlag privacyFlag, List<String> mandatoryFor) {
        this.privateFor = privateFor;
        this.privacyFlag = privacyFlag;
        this.mandatoryFor = mandatoryFor;
    }

    public List<String> getPrivateFor() {
        return privateFor;
    }

    public void setPrivateFor(List<String> privateFor) {
        this.privateFor = privateFor;
    }

    public int getPrivacyFlag() {
        if (privacyFlag != null) {
            return privacyFlag.getValue();
        }

        return PrivacyFlag.STANDARD_PRIVATE.getValue();
    }

    public void setPrivacyFlag(PrivacyFlag PrivacyFlag) {
        this.privacyFlag = privacyFlag;
    }

    public List<String> getMandatoryFor() {
        return mandatoryFor;
    }

    public void setMandatoryFor(List<String> mandatoryFor) {
        this.mandatoryFor = mandatoryFor;
    }
}
