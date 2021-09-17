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
import java.util.Optional;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectReader;

import org.web3j.protocol.ObjectMapperFactory;
import org.web3j.protocol.core.Response;

/** eth_getTransactionReceipt. */
public class EthGetQuorumTransactionReceipt extends Response<QuorumTransactionReceipt> {

    public Optional<QuorumTransactionReceipt> getTransactionReceipt() {
        return Optional.ofNullable(getResult());
    }

    public static class ResponseDeserialiser extends JsonDeserializer<QuorumTransactionReceipt> {

        private ObjectReader objectReader = ObjectMapperFactory.getObjectReader();

        @Override
        public QuorumTransactionReceipt deserialize(
                JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            System.out.printf("######### EthGetQuorumTransactionReceipt::deserialize() ENTERED\n");
            if (jsonParser.getCurrentToken() != JsonToken.VALUE_NULL) {
                // TODO: restore the next line and remove the code below, added to facilitate
                // debugging
                // return objectReader.readValue(jsonParser, QuorumTransactionReceipt.class);
                QuorumTransactionReceipt receipt =
                        objectReader.readValue(jsonParser, QuorumTransactionReceipt.class);
                System.out.printf(
                        "######### EthGetQuorumTransactionReceipt::deserialize() Created QuorumTransactionReceipt: %s\n",
                        receipt.toString());
                return receipt;
            } else {
                return null; // null is wrapped by Optional in above getter
            }
        }
    }
}
