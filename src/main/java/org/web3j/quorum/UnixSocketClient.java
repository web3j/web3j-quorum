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
package org.web3j.quorum;

import java.io.File;
import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UnixSocketClient {
    public static final String PAYLOAD =
            "{\"payload\":\"YIBgQFI0gBVhABBXYACA/VtQYEBRYCCAYQENgzmBAYBgQFJgIIEQFWEAM"
                    + "FdgAID9W1BRYABVYMqAYQBDYAA5YADz/mCAYEBSYAQ2EGBCV3wBAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAGAANQRjYP5HsYEU"
                    + "YEdXgGNtTOY8FGBvV1tgAID9WzSAFWBSV2AAgP1bUGBtYASANgNgIIEQFWBnV2AAgP1bUDVgk1ZbAFs0gBVgeldgAID9W1BggWCYVlt"
                    + "gQIBRkYJSUZCBkANgIAGQ81tgAFVWW2AAVJBW/qFlYnp6cjBYIHXHcMfo5mFEgQaND9vpPt1lCscRGz5DYt82nUd8TKATACkAAAAAAA"
                    + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAFw==\",\"from\":\"BULeR8JyUWhiuuCMU/HLA0Q5pzkYT+cHII3ZKBey3Bo=\",\"to"
                    + "\":[\"QfeDAys9MPDs2XHExtc84jKGHxZg/aj52DTh0vtA3Xc=\"]}";

    public static void main(String[] args) {
        OkHttpClient client =
                new OkHttpClient.Builder()
                        .socketFactory(
                                new UnixDomainSocketFactory(
                                        new File(
                                                "/Users/sebastianraba/Desktop/work/"
                                                        + "web3js-quorum/constellation/data/constellation.ipc")))
                        .build();

        RequestBody requestBody =
                RequestBody.create(PAYLOAD, okhttp3.MediaType.parse("application/json"));

        // use a proper host name as a DNS lookup still occurs (localhost should suffice)
        Request request =
                new Request.Builder().url("http://localhost/send").post(requestBody).build();
        try (Response response = client.newCall(request).execute()) {
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
