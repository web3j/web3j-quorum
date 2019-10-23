/*
 * Copyright 2019 Web3 Labs LTD.
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
package org.web3j.quorum.examples;

import java.util.List;
import java.util.Map;

import org.web3j.protocol.Web3jService;
import org.web3j.protocol.http.HttpService;
import org.web3j.quorum.Quorum;
import org.web3j.quorum.methods.response.istanbul.Snapshot;

public class IstanbulRpcExample {

    public static void main(String[] args) {

        String quorumUrl = "http://localhost:22000";
        Web3jService web3jService = new HttpService(quorumUrl);
        Quorum quorum = Quorum.build(web3jService);

        String blockHash = "";

        try {

            Snapshot snap = quorum.istanbulGetSnapshot("latest").send().getSnapshot().get();
            System.out.println("snap1 " + snap.toString());

            //			Snapshot snap2 =
            // cakeshop.istanbulGetSnapshotAtHash(blockHash).send().getSnapshot().get();
            //			System.out.println("snap2 at " + blockHash + ": " + snap2.toString());

            List<String> validators = quorum.istanbulGetValidators("latest").send().getValidators();
            System.out.println("validators " + validators);

            //			List<String> validators2 =
            // cakeshop.istanbulGetValidatorsAtHash(blockHash).send().getValidators();
            //			System.out.println("validators2 " + validators2);

            Map<String, Boolean> candidates = quorum.istanbulCandidates().send().getCandidates();
            System.out.println("candidates " + candidates);

            // propose 1st validator
            if (validators != null && !validators.isEmpty()) {
                String candidateAddress = (String) validators.get(0);
                String propose =
                        quorum.istanbulPropose(candidateAddress, true).send().getNoResponse();
                System.out.println("proposal of " + candidateAddress + " was " + propose);
            }

            // discard 1st validator
            if (validators != null && !validators.isEmpty()) {
                String candidateAddress = (String) validators.get(0);
                String propose = quorum.istanbulDiscard(candidateAddress).send().getNoResponse();
                System.out.println("discard of " + candidateAddress + " was " + propose);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
