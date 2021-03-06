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
package org.glite.security.voms.admin.operations.requests;

import org.glite.security.voms.admin.event.EventManager;
import org.glite.security.voms.admin.event.request.MembershipRemovalApprovedEvent;
import org.glite.security.voms.admin.event.request.MembershipRemovalRejectedEvent;
import org.glite.security.voms.admin.operations.VOMSContext;
import org.glite.security.voms.admin.operations.VOMSPermission;
import org.glite.security.voms.admin.operations.users.DeleteUserOperation;
import org.glite.security.voms.admin.persistence.model.VOMSUser;
import org.glite.security.voms.admin.persistence.model.request.MembershipRemovalRequest;
import org.glite.security.voms.admin.persistence.model.request.Request.STATUS;

public class HandleMembershipRemovalRequest extends
  BaseHandleRequestOperation<MembershipRemovalRequest> {

  public HandleMembershipRemovalRequest(MembershipRemovalRequest request,
    DECISION decision) {

    super(request, decision);
  }

  @Override
  protected void approve() {

    checkRequestStatus(STATUS.SUBMITTED);
    VOMSUser u = getRequesterAsVomsUser();
    DeleteUserOperation.instance(u).execute();

    approveRequest();

    EventManager.instance().dispatch(new MembershipRemovalApprovedEvent(request));

  }

  @Override
  protected void reject() {

    checkRequestStatus(STATUS.SUBMITTED);

    rejectRequest();

    EventManager.instance().dispatch(new MembershipRemovalRejectedEvent(request));
  }

  @Override
  protected void setupPermissions() {

    addRequiredPermission(VOMSContext.getVoContext(), VOMSPermission
      .getContainerRWPermissions().setMembershipRWPermission());
  }

}
