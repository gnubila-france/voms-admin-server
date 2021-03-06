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
package org.glite.security.voms.admin.core.validation.strategies.impl;

import org.glite.security.voms.admin.core.validation.UserSuspensionManagementContext;
import org.glite.security.voms.admin.core.validation.strategies.RestoreUserStrategy;
import org.glite.security.voms.admin.core.validation.strategies.SuspendUserStrategy;
import org.glite.security.voms.admin.event.EventManager;
import org.glite.security.voms.admin.event.user.UserRestoredEvent;
import org.glite.security.voms.admin.event.user.UserSuspendedEvent;
import org.glite.security.voms.admin.persistence.model.VOMSUser;
import org.glite.security.voms.admin.persistence.model.VOMSUser.SuspensionReason;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultUserSuspensionManagementBehaviour implements
  SuspendUserStrategy, RestoreUserStrategy, UserSuspensionManagementContext {

  public static final Logger log = LoggerFactory
    .getLogger(DefaultUserSuspensionManagementBehaviour.class);

  public void suspendUser(VOMSUser user, SuspensionReason suspensionReason) {

    if (!user.isSuspended()) {
      user.suspend(suspensionReason);
      EventManager.instance().dispatch(
        new UserSuspendedEvent(user, suspensionReason));
    }

  }

  public void restoreUser(VOMSUser user) {

    if (user.isSuspended()) {
      user.restore();
      EventManager.instance().dispatch(new UserRestoredEvent(user));
    }

  }

  public RestoreUserStrategy getRestoreUserStrategy() {

    return this;
  }

  public SuspendUserStrategy getSuspendUserStrategy() {

    return this;
  }

}
