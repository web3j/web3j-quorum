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
package org.web3j.quorum.methods.response.permissioning

public data class PermissionRoleInfo(
    val isVoter: Boolean,
    val active: Boolean,
    val orgId: String,
    val roleId: String,
    val access: Int,
    val isAdmin: Boolean
)

public data class PermissionAccountInfo(
    val acctId: String,
    val isOrgAdmin: Boolean,
    val orgId: String,
    val roleId: String,
    val status: Int
)

public data class PermissionNodeInfo(
    val orgId: String,
    val url: String,
    val status: Int
)

public data class PermissionOrgInfo(
    val orgId: String,
    val fullOrgId: String,
    val parentOrgId: String,
    val ultimateParent: String,
    val level: Int,
    val status: Int,
    val subOrgList: List<String>?
)

public data class OrgDetails(
    val roleList: List<PermissionRoleInfo>?,
    val acctList: List<PermissionAccountInfo>?,
    var nodeList: List<PermissionNodeInfo>?,
    var subOrgList: List<String>?
)
