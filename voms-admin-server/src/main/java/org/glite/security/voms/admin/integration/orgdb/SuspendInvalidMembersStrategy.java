/**
 * Copyright (c) Istituto Nazionale di Fisica Nucleare (INFN). 2006-2015
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.glite.security.voms.admin.integration.orgdb;

import org.glite.security.voms.admin.core.validation.ValidationManager;
import org.glite.security.voms.admin.integration.orgdb.strategies.OrgDBMissingMembershipRecordStrategy;
import org.glite.security.voms.admin.persistence.model.VOMSUser;
import org.glite.security.voms.admin.persistence.model.VOMSUser.SuspensionReason;

public class SuspendInvalidMembersStrategy implements
  OrgDBMissingMembershipRecordStrategy {

  public void handleMissingMembershipRecord(VOMSUser u) {

    SuspensionReason reason = SuspensionReason.OTHER;
    reason
      .setMessage(String
        .format(
          "OrgDB: No record found for user '%s %s, %s' inside CERN organization database.",
          u.getName(), u.getSurname(), u.getEmailAddress()));
    ValidationManager.instance().suspendUser(u, reason);

  }

}
