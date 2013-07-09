<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<openmrs:htmlInclude file="${pageContext.request.contextPath}/moduleResources/patientnarratives/css/styles.css"/>

<%@ include file="template/localHeader.jsp"%>
<%@ taglib prefix="kc" tagdir="/WEB-INF/tags/module/patientnarratives/"%>

<openmrs:htmlInclude file="/moduleResources/patientnarratives/css/demo_table_jui.css" />
<openmrs:htmlInclude file="/moduleResources/patientnarratives/js/jquery.dataTables.min.js" />


<script type="text/javascript">
    $j(document).ready(function() {
        $j('#table').dataTable( {
            "bFilter": true,
            "iDisplayLength": 15,
            "bProcessing": true,
            "bJQueryUI": true,
            "sPaginationType": "full_numbers",
            "aaSorting": [[ 6, "desc" ]],
            "aoColumns": [
                { "bSearchable": true,
                    "bVisible":    true },
                { "bSearchable": true,
                    "bVisible":    true },
                { "bSearchable": true,
                    "bVisible":    true },
                { "bSearchable": true,
                    "bVisible":    true },
                { "bSearchable": true,
                    "bVisible":    true },
                { "bSearchable": true,
                    "bVisible":    true },
                { "bSearchable": true,
                    "bVisible":    true },
                { "bSearchable": true,
                    "bVisible":    true },
                { "bSearchable": true,
                    "bVisible":    true }
            ]

        } );
    } );
</script>

<%--<h2><spring:message code="feedback.submittedFeedback"/></h2>--%>


<%--<b class="boxHeader"><spring:message code="feedback.submit"/></b>--%>
<div class="box" >

    <table cellspacing="0" cellpadding="2" id="table" class="display">
        <thead>
        <tr>
            <th width="200">Encounter ID</th>
            <th width="300">Encounter Date</th>

            <th width="300">Patient Name</th>
            <th width="300">Gender</th>
            <th width="300">Age</th>
            <th width="300">Hometown</th>
            <th width="300">Narrative</th>
            <th width="300">Email</th>
            <th width="300">Telephone</th>

        </tr>
        </thead>
        <tbody>
        <c:forEach items="${encounters}" var="encountersObj" varStatus="loopStatus">
            <tr class="">
                <td><a href="<openmrs:contextPath/>/admin/encounters/encounter.form?encounterId=<c:out value="${encountersObj.encounterId}"/>"><c:out value="${encountersObj.encounterId} "/> </td>

                <%--<c:forEach items="${encountersObj.encounterProviders}" var="encProvidersObj" varStatus="loopStatus">--%>
                <%--<td><c:out value="${encProvidersObj.provider} "/> </td>--%>
                <%--</c:forEach>--%>

                <%--<td><c:out value="${encountersObj.encounterType.name} "/> </td>--%>

                <td><c:out value="${encountersObj.encounterDatetime} "/></td>

                <c:forEach items="${encountersObj.obs}" var="encObsObj" varStatus="loopStatus">
                    <c:choose>
                        <c:when test="${encObsObj.concept == '9'}">
                            <c:set var="narrative_patient_story" value="${encObsObj.valueText}" />
                        </c:when>
                        <c:when test="${encObsObj.concept == '11'}">
                            <c:set var="narrative_patient_tp" value="${encObsObj.valueText}" />
                        </c:when>
                        <c:when test="${encObsObj.concept == '13'}">
                            <c:set var="narrative_patient_sex" value="${encObsObj.valueText}" />
                        </c:when>
                        <c:when test="${encObsObj.concept == '7'}">
                            <c:set var="narrative_patient_city" value="${encObsObj.valueText}" />
                        </c:when>
                        <c:when test="${encObsObj.concept == '12'}">
                            <c:set var="narrative_patient_name" value="${encObsObj.valueText}" />
                        </c:when>
                        <c:when test="${encObsObj.concept == '10'}">
                            <c:set var="narrative_patient_email" value="${encObsObj.valueText}" />
                        </c:when>
                        <c:when test="${encObsObj.concept == '8'}">
                            <c:set var="narrative_patient_age" value="${encObsObj.valueText}" />
                        </c:when>
                    </c:choose>
                </c:forEach>

                <td><c:out value="${narrative_patient_name} "/> </td>
                <td><c:out value="${narrative_patient_sex} "/> </td>
                <td><c:out value="${narrative_patient_age} "/> </td>
                <td><c:out value="${narrative_patient_city} "/> </td>

                <td>
                    <a class="toggleAddTag" href="#">Show-Story</a>
                    <div id="addTag" style="border: 1px black solid; background-color: #e0e0e0; display: none">
                        <c:out value="${narrative_patient_story}"/>
                    </div>
                </td>

                <td><c:out value="${narrative_patient_tp} "/> </td>
                <td><c:out value="${narrative_patient_email} "/> </td>

            </tr>
        </c:forEach>

        </tbody>
    </table>
</div>

<%@ include file="/WEB-INF/template/footer.jsp"%>

