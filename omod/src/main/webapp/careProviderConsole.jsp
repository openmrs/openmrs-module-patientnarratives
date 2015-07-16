<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<openmrs:hasPrivilege privilege="Manage Patient Narratives">

<%@ include file="template/localHeader.jsp"%>
<%@ taglib prefix="kc" tagdir="/WEB-INF/tags/module/patientnarratives/"%>

<openmrs:htmlInclude file="${pageContext.request.contextPath}/moduleResources/patientnarratives/css/styles.css"/>
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
                    "bVisible":    true }
            ]

        } );
    } );
</script>

<div class="box" >

    <table cellspacing="0" cellpadding="2" id="table" class="display">
        <thead>
        <tr>
            <th width="200"><spring:message code="patientnarratives.encounterId"/></th>
            <th width="300"><spring:message code="patientnarratives.encounterDate"/></th>

            <th width="300"><spring:message code="patientnarratives.patientName"/></th>
            <%--<th width="300">Gender</th>--%>
            <%--<th width="300">Age</th>--%>
            <%--<th width="300">Hometown</th>--%>
            <%--<th width="300">Narrative</th>--%>
            <th width="300"><spring:message code="patientnarratives.subject"/></th>
            <th width="300"><spring:message code="patientnarratives.telephone"/></th>
            <th width="300"><spring:message code="patientnarratives.status"/></th>

            <%--<th width="100">Register</th>--%>

        </tr>
        </thead>
        <tbody>
        <c:forEach items="${encounters}" var="encountersObj" varStatus="loopStatus">
            <tr class="">
                <%--<td><a href="<openmrs:contextPath/>/admin/encounters/encounter.form?encounterId=<c:out value="${encountersObj.encounterId}"/>"><c:out value="${encountersObj.encounterId} "/> </td>--%>

                <td><a href="<openmrs:contextPath/>/module/patientnarratives/fullNarrativeForm.form?encounterId=<c:out value="${encountersObj.encounterId}"/>"><c:out value="${encountersObj.encounterId} "/> </td>

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
                        <c:when test="${encObsObj.concept == '15'}">
                            <c:set var="narrative_patient_status" value="${encObsObj.valueText}" />
                        </c:when>
                        <c:when test="${encObsObj.concept == '16'}">
                            <c:set var="narrative_patient_subject" value="${encObsObj.valueText}" />
                        </c:when>
                    </c:choose>
                </c:forEach>

                <td><c:out value="${narrative_patient_name} "/> </td>
                <%--<td><c:out value="${narrative_patient_sex} "/> </td>--%>
                <%--<td><c:out value="${narrative_patient_age} "/> </td>--%>
                <%--<td><c:out value="${narrative_patient_city} "/> </td>--%>

                <%--<td>--%>
                    <%--<a class="toggleAddTag" href="#">Show-Story</a>--%>
                    <%--<div id="addTag" style="border: 1px black solid; background-color: #e0e0e0; display: none">--%>
                        <%--<c:out value="${narrative_patient_story}"/>--%>
                    <%--</div>--%>
                <%--</td>--%>


                <td><c:out value="${narrative_patient_subject} "/> </td>
                <td><c:out value="${narrative_patient_tp} "/> </td>
                <td><c:out value="${narrative_patient_status} "/> </td>

                <%--<td>--%>
                    <%--<form method="post">--%>
                        <%--<input type="hidden" name="textEncID" value="${encountersObj.encounterId}">--%>
                        <%--<input type="submit" value="Register">--%>
                    <%--</form>--%>
                <%--</td>--%>
            </tr>
        </c:forEach>

        </tbody>
    </table>
</div>

</openmrs:hasPrivilege>

<%@ include file="/WEB-INF/template/footer.jsp"%>

