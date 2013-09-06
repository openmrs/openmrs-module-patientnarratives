<spring:htmlEscape defaultHtmlEscape="true" />

<script>
    var $j = jQuery.noConflict();
</script>

<ul id="menu">
    <li class="first">
        <a href="${pageContext.request.contextPath}/admin"><spring:message
            code="admin.title.short" /></a>
    </li>

    <li <c:if test='<%= request.getRequestURI().contains("/patientNarrativesForm") %>'>class="active"</c:if>>
    <a href="${pageContext.request.contextPath}/module/patientnarratives/patientNarrativesForm.form"><spring:message
            code="patientnarratives.careseeker.form" /></a>
    </li>

    <li <c:if test='<%= request.getRequestURI().contains("/careProviderConsole") %>'>class="active"</c:if>>
    <a href="${pageContext.request.contextPath}/module/patientnarratives/careProviderConsole.form"><spring:message
            code="patientnarratives.careprovider.console" /></a>
    </li>

    <li <c:if test='<%= request.getRequestURI().contains("/moduleSettings") %>'>class="active"</c:if>>
    <a href="${pageContext.request.contextPath}/module/patientnarratives/moduleSettings.form"><spring:message
            code="patientnarratives.module.settings" /></a>
    </li>

</ul>

<h2>
    <spring:message code="patientnarratives.title" />
</h2>

