<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>
<%@ taglib prefix="fn" uri="/WEB-INF/taglibs/fn.tld" %>

<form method="post" >
    <b class="boxHeader">Module Properties</b>
    <div class="box" >
        <table>
            <tr>
                <td valign="top">Form Type</td>
                <td>
                    <input type="radio" name="formType" value="HTML-Form" <c:if test="${formType == 'HTML-Form'}">checked</c:if>>HTML-Form |
                    <input type="radio" name="formType" value="X-Form" <c:if test="${formType == 'X-Form'}">checked</c:if>>X-Form
                </td>
            </tr>
            <tr>
                <td valign="top">Form ID</td>
                <td><input type="text" name="formID" value="<openmrs:globalProperty key="patientnarratives.formid"/>"></td>
            </tr>
            <tr>
                <td valign="top">Patient ID</td>
                <td><input type="text" name="patientID" value="<openmrs:globalProperty key="patientnarratives.patientid"/>"></td>
            </tr>
            <tr>
                <td valign="top">Encounter Type</td>
                <td><input type="text" name="encType" value="<openmrs:globalProperty key="patientnarratives.enctype"/>"></td>
            </tr>
            <tr>
                <td> </td>
                <td>
                    <input type="submit" value="Save" />
                </td>
            </tr>
        </table>
    </div>

    <%@ include file="/WEB-INF/template/footer.jsp"%>

