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

import java.util.List;

import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

/**
 * Quorum's Transaction Receipt object.
 *
 * <p>This is almost identical to the Ethereum {@link
 * org.web3j.protocol.core.methods.response.TransactionReceipt} with the exception that it includes
 * the isPrivacyMarkerTransaction field.
 */
public class QuorumTransactionReceipt extends TransactionReceipt {
    private Boolean isPrivacyMarkerTransaction;

    public QuorumTransactionReceipt() {}

    public QuorumTransactionReceipt(
            String transactionHash,
            String transactionIndex,
            String blockHash,
            String blockNumber,
            String cumulativeGasUsed,
            String gasUsed,
            String contractAddress,
            String root,
            String status,
            String from,
            String to,
            List<Log> logs,
            String logsBloom,
            String revertReason,
            Boolean isPrivacyMarkerTransaction) {
        super(
                transactionHash,
                transactionIndex,
                blockHash,
                blockNumber,
                cumulativeGasUsed,
                gasUsed,
                contractAddress,
                root,
                status,
                from,
                to,
                logs,
                logsBloom,
                revertReason,
                null,
                null);
        this.isPrivacyMarkerTransaction = isPrivacyMarkerTransaction;
    }

    public Boolean getIsPrivacyMarkerTransaction() {
        return isPrivacyMarkerTransaction;
    }

    public void setIsPrivacyMarkerTransaction(Boolean isPrivacyMarkerTransaction) {
        this.isPrivacyMarkerTransaction = isPrivacyMarkerTransaction;
    }

    public boolean isPrivacyMarkerTransaction() {
        if (null != isPrivacyMarkerTransaction) {
            return isPrivacyMarkerTransaction;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false;
        }
        if (!(o instanceof QuorumTransactionReceipt)) {
            return false;
        }

        QuorumTransactionReceipt that = (QuorumTransactionReceipt) o;

        return isPrivacyMarkerTransaction.equals(that);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result =
                31 * result
                        + (isPrivacyMarkerTransaction != null
                                ? isPrivacyMarkerTransaction.hashCode()
                                : 0);
        return result;
    }

    @Override
    public String toString() {
        return "QuorumTransactionReceipt{"
                + "transactionHash='"
                + super.getTransactionHash()
                + '\''
                + ", transactionIndex='"
                + super.getTransactionIndex()
                + '\''
                + ", blockHash='"
                + super.getBlockHash()
                + '\''
                + ", blockNumber='"
                + super.getBlockNumber()
                + '\''
                + ", cumulativeGasUsed='"
                + super.getCumulativeGasUsed()
                + '\''
                + ", gasUsed='"
                + super.getGasUsed()
                + '\''
                + ", contractAddress='"
                + super.getContractAddress()
                + '\''
                + ", root='"
                + super.getRoot()
                + '\''
                + ", status='"
                + super.getStatus()
                + '\''
                + ", from='"
                + super.getFrom()
                + '\''
                + ", to='"
                + super.getTo()
                + '\''
                + ", logs="
                + super.getLogs()
                + ", logsBloom='"
                + super.getLogsBloom()
                + '\''
                + ", revertReason='"
                + super.getRevertReason()
                + '\''
                + ", isPrivacyMarkerTransaction='"
                + isPrivacyMarkerTransaction
                + '\''
                + '}';
    }
}
