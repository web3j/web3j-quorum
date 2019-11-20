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
package org.web3j.quorum.methods.response.permissioning;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.module.kotlin.KotlinModule;

import org.web3j.protocol.core.Response;

public class PermissionOrgList extends Response<List<PermissionOrgInfo>> {

    public List<PermissionOrgInfo> getPermissionOrgList() {
        return getResult();
    }

    @Override
    @JsonDeserialize(using = PermissionOrgList.ResponseDeserialiser.class)
    public void setResult(List<PermissionOrgInfo> result) {
        super.setResult(result);
    }

    public static class ResponseDeserialiser extends JsonDeserializer<List<PermissionOrgInfo>> {
        private ObjectMapper om = new ObjectMapper().registerModule(new KotlinModule());

        @Override
        public List<PermissionOrgInfo> deserialize(
                JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {

            List<PermissionOrgInfo> orgList = new ArrayList<>();
            JsonToken nextToken = jsonParser.nextToken();

            if (nextToken == JsonToken.START_OBJECT) {
                Iterator<PermissionOrgInfo> orgInfoIterator =
                        om.readValues(jsonParser, PermissionOrgInfo.class);
                while (orgInfoIterator.hasNext()) {
                    orgList.add(orgInfoIterator.next());
                }
                return orgList;
            } else {
                return null;
            }
        }
    }
}
