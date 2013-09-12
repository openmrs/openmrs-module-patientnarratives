<spring:htmlEscape defaultHtmlEscape="true" />

<script>
    var $j = jQuery.noConflict();
</script>

<ul id="menu">
    <li class="first">
        <a href="${pageContext.request.contextPath}/admin"><spring:message
            code="admin.title.short" /></a>
    </li>

    <openmrs:hasPrivilege privilege="Add Patient Narratives">
    <li <c:if test='<%= request.getRequestURI().contains("/patientNarrativesForm") %>'>class="active"</c:if>>
    <a href="${pageContext.request.contextPath}/module/patientnarratives/patientNarrativesForm.form"><spring:message
            code="patientnarratives.careseeker.form" /></a>
    </li>
    </openmrs:hasPrivilege>

    <openmrs:hasPrivilege privilege="Manage Patient Narratives">
    <li <c:if test='<%= request.getRequestURI().contains("/careProviderConsole") %>'>class="active"</c:if>>
    <a href="${pageContext.request.contextPath}/module/patientnarratives/careProviderConsole.form"><spring:message
            code="patientnarratives.careprovider.console" /></a>
    </li>
    </openmrs:hasPrivilege>

    <openmrs:hasPrivilege privilege="Configure Patient Narratives">
    <li <c:if test='<%= request.getRequestURI().contains("/moduleSettings") %>'>class="active"</c:if>>
    <a href="${pageContext.request.contextPath}/module/patientnarratives/moduleSettings.form"><spring:message
            code="patientnarratives.module.settings" /></a>
    </li>
    </openmrs:hasPrivilege>

</ul>

<h2>
    <spring:message code="patientnarratives.title" />
</h2>

