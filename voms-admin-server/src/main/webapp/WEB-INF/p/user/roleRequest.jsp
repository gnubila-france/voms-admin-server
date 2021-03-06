<%--

    Copyright (c) Istituto Nazionale di Fisica Nucleare (INFN). 2006-2015

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

--%>
<%@include file="/WEB-INF/p/shared/taglibs.jsp"%>

<h1>Role assignment request</h1>

<tiles2:insertTemplate template="../shared/errorsAndMessages.jsp"/>

<p class="req-gm">
You have requested the assignment of role:
</p>

<div class="req-fqan">
 ${group.name}/${role}
</div>

<s:form 
	validate="true" 
	action="request-role-membership" 
	id="requestRoleMembership"
	>
  <s:token/>
  <s:hidden name="userId" value="%{userId}"/>
  <s:hidden name="groupId" value="%{groupId}"/>
  <s:hidden name="roleId" value="%{roleId}"/>
  <s:textarea name="reason" 
    cols="78"
    rows="3"
    label="Please provide a reason for your request"/>
  <s:submit value="%{'Submit'}" align="left"/>
</s:form>