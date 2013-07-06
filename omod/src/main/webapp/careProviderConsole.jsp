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

            <th width="300">VisitType</th>
            <th width="300">Form Name</th>
            <th width="300">Encounter Date</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${encounters}" var="encountersObj" varStatus="loopStatus">
            <tr class="">
                <td><a href="<openmrs:contextPath/>/admin/encounters/encounter.form?encounterId=<c:out value="${encountersObj.encounterId}"/>"><c:out value="${encountersObj.encounterId} "/> </td>
                <td><c:out value="${encountersObj.visit.visitType} "/> </td>
                <td><c:out value="${encountersObj.form.name} "/> </td>
                <td><c:out value="${encountersObj.encounterDatetime} "/></td>
            </tr>
        </c:forEach>

        </tbody>
    </table>
</div>

<%@ include file="/WEB-INF/template/footer.jsp"%>

