package org.web3j.quorum;

import java.util.List;

/**
 * Quorum node configuration class.
 */
public class Node {

    private final String address;
    private final List<String> publicKeys;
    private final String url;

    public Node(String address, List<String> publicKeys, String url) {
        this.address = address;
        this.publicKeys = publicKeys;
        this.url = url;
    }

    public String getAddress() {
        return address;
    }

    public List<String> getPublicKeys() {
        return publicKeys;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Node node = (Node) o;

        if (getAddress() != null ? !getAddress().equals(node.getAddress())
                : node.getAddress() != null) {
            return false;
        }
        if (getPublicKeys() != null ? !getPublicKeys().equals(node.getPublicKeys())
                : node.getPublicKeys() != null) {
            return false;
        }
        return getUrl() != null ? getUrl().equals(node.getUrl()) : node.getUrl() == null;
    }

    @Override
    public int hashCode() {
        int result = getAddress() != null ? getAddress().hashCode() : 0;
        result = 31 * result + (getPublicKeys() != null ? getPublicKeys().hashCode() : 0);
        result = 31 * result + (getUrl() != null ? getUrl().hashCode() : 0);
        return result;
    }
}
