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
package org.glite.security.voms.admin.view.actions.apiv2;

import java.util.List;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.glite.security.voms.admin.apiv2.JSONSerializer;
import org.glite.security.voms.admin.apiv2.UserSearchTO;
import org.glite.security.voms.admin.operations.users.DynaSearchOperation;
import org.glite.security.voms.admin.persistence.model.VOMSUser;
import org.glite.security.voms.admin.view.actions.BaseAction;

import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@ParentPackage("json")
@Results({ @Result(name = BaseAction.SUCCESS, type = "json") })
public class SearchUserAction extends BaseAction implements Preparable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  UserSearchTO searchData;

  @VisitorFieldValidator(appendPrefix = true, message = "Invalid input:  ")
  public UserSearchTO getSearchData() {

    return searchData;
  }

  public void setSearchData(UserSearchTO searchData) {

    this.searchData = searchData;
  }

  @Override
  public void prepare() throws Exception {

    if (searchData == null) {
      addActionError("Please provide a searchData structure");
    }

  }

  @Override
  public String execute() throws Exception {

    DynaSearchOperation op = new DynaSearchOperation(searchData);
    List<VOMSUser> matchedUsers = op.execute();
    searchData.setResults(JSONSerializer.serialize(matchedUsers));

    return BaseAction.SUCCESS;
  }

}