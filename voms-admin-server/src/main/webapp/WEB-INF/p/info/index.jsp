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

<h1>Your certificate information</h1>

<s:if test="currentAdmin">
  <s:if test='currentAdmin.unauthenticated'>
    <p>No certificate found! This can be caused by your
      browser being misconfigured or the certificate stored in your browser
      could be expired!</p>
  </s:if>
  <s:else>
    <div>
      <dl>
        <dt>Subject</dt>
        <dd>
          <s:property value="currentAdmin.realSubject" />
        </dd>
        <dt>Issuer</dt>
        <dd>
          <s:property value="currentAdmin.realIssuer" />
        </dd>
        <dt>Serial number</dt>
        <dd>
          <s:property value="currentAdmin.clientCert.serialNumber" />
        </dd>
        <dt>Not valid after</dt>
        <dd>
          <s:date
            name="currentAdmin.clientCert.notAfter"
            format="MMM d, yyyy HH:mm:ss" />
          (
          <s:date
            name="currentAdmin.clientCert.notAfter"
            nice="true"
            format="struts.date.format.future" />
          )
        </dd>
      </dl>
    </div>

    <div style="clear: left;">
      Your certificate:
      <ul class="certificate-info">

        <s:if test="currentAdmin.isVOAdmin()">
          <li>grants you <strong>VO administrator</strong> privileges on
            this VO
          </li>
        </s:if>

        <s:if test="currentAdmin.isVoUser()">
          <li>is currently linked to the following membership: <s:url
              action="load"
              namespace="/user"
              var="loadUserURL">
              <s:param
                name="userId"
                value="currentAdmin.getVoUser().id" />
            </s:url> <a href="${loadUserURL}"> <s:property
                value="currentAdmin.getVoUser().shortName" />
          </a>
          </li>
        </s:if>
        <s:else>
          
          <li>is <strong>NOT</strong> linked to any membership in this VO. This
            means you are <strong>NOT</strong> recognized as a VO member, and
            cannot get VOMS credentials using <code>voms-proxy-init</code> for
            this VO out of this certificate.
          </li>
        </s:else>
      </ul>
      
       <s:if test="! currentAdmin.isVoUser()">
        <div style="text-align: center; margin-top: 2em">
          <a class="register-url" href='<s:url action="start" namespace="/register"/>'>Click here to register as a new member</a>
        </div>
       </s:if>
    </div>
  </s:else>
</s:if>
<s:else>
  No authentication information found in current context!
</s:else>
