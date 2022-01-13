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
package org.web3j.quorum.methods.response;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.web3j.protocol.core.Response;
import org.web3j.quorum.PrivacyFlag;

/** quorum_getPrivatePayload */
public class ContractPrivacyMetadataInfo
        extends Response<ContractPrivacyMetadataInfo.ContractPrivacyMetadata> {
    public ContractPrivacyMetadataInfo.ContractPrivacyMetadata getContractPrivacyMetadata() {
        return getResult();
    }

    @Override
    @JsonDeserialize(using = ContractPrivacyMetadataInfo.ResponseDeserialiser.class)
    public void setResult(ContractPrivacyMetadata result) {
        super.setResult(result);
    }

    public static class ResponseDeserialiser extends JsonDeserializer<ContractPrivacyMetadata> {
        private ObjectMapper om = new ObjectMapper();

        @Override
        public ContractPrivacyMetadata deserialize(
                JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            if (jsonParser.getCurrentToken() != JsonToken.VALUE_NULL) {
                return om.readValue(jsonParser, ContractPrivacyMetadata.class);
            } else {
                return null;
            }
        }
    }

    public static class ContractPrivacyMetadata {
        private byte[] creationTxHash;
        private PrivacyFlag privacyFlag;

        public ContractPrivacyMetadata() {}

        public ContractPrivacyMetadata(byte[] creationTxHash, PrivacyFlag privacyFlag) {
            this.creationTxHash = creationTxHash;
            this.privacyFlag = privacyFlag;
        }

        public byte[] getCreationTxHash() {
            return creationTxHash;
        }

        public void setCreationTxHash(byte[] creationTxHash) {
            this.creationTxHash = creationTxHash;
        }

        public PrivacyFlag getPrivacyFlag() {
            return privacyFlag;
        }

        public void setPrivacyFlag(PrivacyFlag privacyFlag) {
            this.privacyFlag = privacyFlag;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ContractPrivacyMetadata)) return false;
            ContractPrivacyMetadata that = (ContractPrivacyMetadata) o;
            return Arrays.equals(creationTxHash, that.creationTxHash)
                    && privacyFlag == that.privacyFlag;
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(privacyFlag);
            result = 31 * result + Arrays.hashCode(creationTxHash);
            return result;
        }

        @Override
        public String toString() {
            return "ContractPrivacyMetadata{"
                    + "creationTxHash="
                    + Arrays.toString(creationTxHash)
                    + ", privacyFlag="
                    + privacyFlag
                    + '}';
        }
    }
}
