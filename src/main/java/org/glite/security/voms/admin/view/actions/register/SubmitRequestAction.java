package org.glite.security.voms.admin.view.actions.register;

import java.util.Calendar;
import java.util.Date;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.glite.security.voms.admin.common.VOMSConfiguration;
import org.glite.security.voms.admin.dao.generic.DAOFactory;
import org.glite.security.voms.admin.event.EventManager;
import org.glite.security.voms.admin.event.registration.VOMembershipRequestSubmittedEvent;
import org.glite.security.voms.admin.view.actions.BaseAction;

import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

@ParentPackage("base")
@Results( { @Result(name = BaseAction.INPUT, location = "register"),
		@Result(name = BaseAction.SUCCESS, location = "registerConfirmation") })
public class SubmitRequestAction extends RegisterActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String name;
	String surname;

	String institution;
	String address;

	String phoneNumber;

	String aupAccepted;

	@Override
	public String execute() throws Exception {

		if (!VOMSConfiguration.instance().getBoolean(
				VOMSConfiguration.REGISTRATION_SERVICE_ENABLED, true))
			return "registrationDisabled";

		String result = checkExistingPendingRequests();

		if (result != null)
			return result;

		// FIXME: Change timespan for request expirations
		Date expirationDate = getFutureDate(new Date(), Calendar.MINUTE, 5);

		requester.setName(name);
		requester.setSurname(surname);
		requester.setInstitution(institution);
		requester.setAddress(address);
		requester.setPhoneNumber(phoneNumber);

		request = DAOFactory.instance().getRequestDAO()
				.createVOMembershipRequest(requester, expirationDate);

		String confirmURL = buildConfirmURL();
		String cancelURL = buildCancelURL();

		EventManager.dispatch(new VOMembershipRequestSubmittedEvent(request,
				confirmURL, cancelURL));

		return SUCCESS;
	}

	private String buildCancelURL() {

		return getBaseURL() + "/register/cancel-request.action?requestId="
				+ getModel().getId() + "&confirmId="
				+ getModel().getConfirmId();

	}

	private String buildConfirmURL() {

		return getBaseURL() + "/register/confirm-request.action?requestId="
				+ getModel().getId() + "&confirmId="
				+ getModel().getConfirmId();

	}

	@RequiredFieldValidator(type = ValidatorType.FIELD, message = "Please enter your name.")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@RequiredFieldValidator(type = ValidatorType.FIELD, message = "Please enter your surname.")
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@RequiredFieldValidator(type = ValidatorType.FIELD, message = "Please enter your institution.")
	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	@RequiredFieldValidator(type = ValidatorType.FIELD, message = "Please enter your address.")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@RequiredFieldValidator(type = ValidatorType.FIELD, message = "Please enter your phoneNumber.")
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@RequiredFieldValidator(type = ValidatorType.FIELD, message = "You must sign the AUP.")
	@RegexFieldValidator(type = ValidatorType.FIELD, expression = "^true$", message = "You must accept the terms of the AUP to proceed")
	public String getAupAccepted() {
		return aupAccepted;
	}

	public void setAupAccepted(String aupAccepted) {
		this.aupAccepted = aupAccepted;
	}

}