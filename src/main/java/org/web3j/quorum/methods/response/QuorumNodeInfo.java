package org.web3j.quorum.methods.response;

import org.web3j.protocol.core.Response;

/**
 * quorum_nodeInfo
 */
public class QuorumNodeInfo extends Response<QuorumNodeInfo.NodeInfo> {

    public NodeInfo getNodeInfo() {
        return getResult();
    }

    public static class NodeInfo {
        private String blockMakerAccount;
        private String voteAccount;
        private boolean canCreateBlocks;
        private boolean canVote;

        private BlockMakeStrategy blockmakestrategy;

        public NodeInfo() {
        }

        public NodeInfo(String blockMakerAccount, String voteAccount, boolean canCreateBlocks, boolean canVote, BlockMakeStrategy blockmakestrategy) {
            this.blockMakerAccount = blockMakerAccount;
            this.voteAccount = voteAccount;
            this.canCreateBlocks = canCreateBlocks;
            this.canVote = canVote;
            this.blockmakestrategy = blockmakestrategy;
        }

        public String getBlockMakerAccount() {
            return blockMakerAccount;
        }

        public void setBlockMakerAccount(String blockMakerAccount) {
            this.blockMakerAccount = blockMakerAccount;
        }

        public String getVoteAccount() {
            return voteAccount;
        }

        public void setVoteAccount(String voteAccount) {
            this.voteAccount = voteAccount;
        }

        public boolean isCanCreateBlocks() {
            return canCreateBlocks;
        }

        public void setCanCreateBlocks(boolean canCreateBlocks) {
            this.canCreateBlocks = canCreateBlocks;
        }

        public boolean isCanVote() {
            return canVote;
        }

        public void setCanVote(boolean canVote) {
            this.canVote = canVote;
        }

        public BlockMakeStrategy getBlockmakestrategy() {
            return blockmakestrategy;
        }

        public void setBlockmakestrategy(BlockMakeStrategy blockmakestrategy) {
            this.blockmakestrategy = blockmakestrategy;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            NodeInfo nodeInfo = (NodeInfo) o;

            if (canCreateBlocks != nodeInfo.canCreateBlocks) return false;
            if (canVote != nodeInfo.canVote) return false;
            if (blockMakerAccount != null ? !blockMakerAccount.equals(nodeInfo.blockMakerAccount) : nodeInfo.blockMakerAccount != null)
                return false;
            if (voteAccount != null ? !voteAccount.equals(nodeInfo.voteAccount) : nodeInfo.voteAccount != null)
                return false;
            return blockmakestrategy != null ? blockmakestrategy.equals(nodeInfo.blockmakestrategy) : nodeInfo.blockmakestrategy == null;
        }

        @Override
        public int hashCode() {
            int result = blockMakerAccount != null ? blockMakerAccount.hashCode() : 0;
            result = 31 * result + (voteAccount != null ? voteAccount.hashCode() : 0);
            result = 31 * result + (canCreateBlocks ? 1 : 0);
            result = 31 * result + (canVote ? 1 : 0);
            result = 31 * result + (blockmakestrategy != null ? blockmakestrategy.hashCode() : 0);
            return result;
        }
    }

    public static class BlockMakeStrategy {
        private int maxblocktime;
        private int minblocktime;
        private String status;
        private String type;

        public BlockMakeStrategy() {
        }

        public BlockMakeStrategy(int maxblocktime, int minblocktime, String status, String type) {
            this.maxblocktime = maxblocktime;
            this.minblocktime = minblocktime;
            this.status = status;
            this.type = type;
        }

        public int getMaxblocktime() {
            return maxblocktime;
        }

        public void setMaxblocktime(int maxblocktime) {
            this.maxblocktime = maxblocktime;
        }

        public int getMinblocktime() {
            return minblocktime;
        }

        public void setMinblocktime(int minblocktime) {
            this.minblocktime = minblocktime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            BlockMakeStrategy that = (BlockMakeStrategy) o;

            if (maxblocktime != that.maxblocktime) return false;
            if (minblocktime != that.minblocktime) return false;
            if (status != null ? !status.equals(that.status) : that.status != null) return false;
            return type != null ? type.equals(that.type) : that.type == null;
        }

        @Override
        public int hashCode() {
            int result = maxblocktime;
            result = 31 * result + minblocktime;
            result = 31 * result + (status != null ? status.hashCode() : 0);
            result = 31 * result + (type != null ? type.hashCode() : 0);
            return result;
        }
    }
}
