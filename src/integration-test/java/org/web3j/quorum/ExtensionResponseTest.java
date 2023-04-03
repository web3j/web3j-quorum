/*
 * Copyright 2022 Web3 Labs Ltd.
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

import org.junit.jupiter.api.Test;

import org.web3j.protocol.ResponseTester;
import org.web3j.quorum.methods.response.extension.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ExtensionResponseTest extends ResponseTester {

    @Test
    public void testActiveExtensionList() {
        buildResponse(
                "{\n"
                        + "  \"jsonrpc\":\"2.0\",\n"
                        + "  \"id\":10,\n"
                        + "  \"result\": [{\n"
                        + "    \"managementContractAddress\":\"managementContractAddress\",\n"
                        + "    \"contractExtended\":\"contractExtended\",\n"
                        + "    \"creationData\":\"creationData\",\n"
                        + "    \"initiator\":\"initiator\",\n"
                        + "    \"recipient\":\"recipient\",\n"
                        + "    \"recipientPtmKey\":\"recipientPtmKey\"\n"
                        + "  }]\n"
                        + "}");

        ActiveExtensionList actExtList = deserialiseResponse(ActiveExtensionList.class);
        assertThat(
                actExtList.getActiveExtensionList().toString(),
                is(
                        "[ActiveExtensionInfo(managementContractAddress=managementContractAddress, contractExtended=contractExtended, creationData=creationData, initiator=initiator, recipient=recipient, recipientPtmKey=recipientPtmKey)]"));
    }

    @Test
    public void testApproveExtension() {
        buildResponse(
                "{\n"
                        + "  \"jsonrpc\":\"2.0\",\n"
                        + "  \"id\":10,\n"
                        + "  \"result\":\"result\"\n"
                        + "}");

        ApproveExtensionInfo approveExtensionInfo = deserialiseResponse(ApproveExtensionInfo.class);
        assertThat(approveExtensionInfo.getApproveExtensionInfo(), is("result"));
    }

    @Test
    public void testCancelExtension() {
        buildResponse(
                "{\n"
                        + "  \"jsonrpc\":\"2.0\",\n"
                        + "  \"id\":10,\n"
                        + "  \"result\":\"result\"\n"
                        + "}");

        CancelExtensionInfo cancelExtensionInfo = deserialiseResponse(CancelExtensionInfo.class);
        assertThat(cancelExtensionInfo.getCancelExtensionInfo(), is("result"));
    }

    @Test
    public void testExtendContract() {
        buildResponse(
                "{\n"
                        + "  \"jsonrpc\":\"2.0\",\n"
                        + "  \"id\":10,\n"
                        + "  \"result\":\"result\"\n"
                        + "}");

        ExtendContractInfo extendContractInfo = deserialiseResponse(ExtendContractInfo.class);
        assertThat(extendContractInfo.getExtendContractInfo(), is("result"));
    }

    @Test
    public void testExtensionStatus() {
        buildResponse(
                "{\n"
                        + "  \"jsonrpc\":\"2.0\",\n"
                        + "  \"id\":10,\n"
                        + "  \"result\":\"DONE\"\n"
                        + "}");

        ExtensionStatusInfo extensionStatusInfo = deserialiseResponse(ExtensionStatusInfo.class);
        assertThat(extensionStatusInfo.getExtensionStatus(), is("DONE"));
    }

    @Test
    public void testgquorumExtensionGenerateExtensionApprovalUuid() {
        buildResponse(
                "{\n"
                        + "  \"jsonrpc\":\"2.0\",\n"
                        + "  \"id\":10,\n"
                        + "  \"result\":\"UUID\"\n"
                        + "}");

        ExtensionApprovalUuid extensionApprovalUuid =
                deserialiseResponse(ExtensionApprovalUuid.class);
        assertThat(extensionApprovalUuid.getExtensionApprovalUuid(), is("UUID"));
    }
}
