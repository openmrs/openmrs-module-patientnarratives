<spring:htmlEscape defaultHtmlEscape="true" />
<ul id="menu">
    <li class="first">
        <a href="${pageContext.request.contextPath}/admin"><spring:message
            code="admin.title.short" /></a>
    </li>

    <li <c:if test='<%= request.getRequestURI().contains("/manage") %>'>class="active"</c:if>>
    <a href="${pageContext.request.contextPath}/module/patientnarratives/manage.form"><spring:message
            code="patientnarratives.manage" /></a>
    </li>

    <!-- Add further links here -->
    <li <c:if test='<%= request.getRequestURI().contains("/submitPatientNarrative") %>'>class="active"</c:if>>
    <a href="${pageContext.request.contextPath}/module/patientnarratives/submitPatientNarrative.form"><spring:message
            code="patientnarratives.manage" /></a>
    </li>





</ul>

<h2>
    <spring:message code="patientnarratives.title" />
</h2>

