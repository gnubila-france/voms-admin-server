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
package org.glite.security.voms.admin.view.actions.dev;

import org.apache.struts2.convention.annotation.Result;
import org.glite.security.voms.admin.test.CreateRandomConfirmedMembershipRequests;
import org.glite.security.voms.admin.view.actions.BaseAction;

@Result(name = BaseAction.SUCCESS, location = "/home/login.action",
  type = "redirect")
public class CreateRandomRequestsAction extends BaseDevAction {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Override
  public String execute() throws Exception {

    if (devEnabled()) {
      new CreateRandomConfirmedMembershipRequests().run();
    }
    
    return SUCCESS;
  }

}
