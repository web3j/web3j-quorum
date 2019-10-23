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
package org.web3j.quorum.methods.response.istanbul;

public class Tally {

    private boolean authorize;
    private int votes;

    public Tally() {};

    public Tally(boolean authorize, int votes) {
        this.setAuthorize(authorize);
        this.setVotes(votes);
    }

    public boolean isAuthorize() {
        return authorize;
    }

    public void setAuthorize(boolean authorize) {
        this.authorize = authorize;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tally)) {
            return false;
        }

        Tally that = (Tally) o;

        if (isAuthorize() != that.isAuthorize()) {
            return false;
        }

        return getVotes() != that.getVotes();
    }

    @Override
    public int hashCode() {
        int result = isAuthorize() ? 1 : 0;
        result = 31 * result + votes;

        return result;
    }

    @Override
    public String toString() {
        return "Tally{" + "authorize='" + authorize + '\'' + ", votes='" + votes + '\'' + '}';
    }
}
