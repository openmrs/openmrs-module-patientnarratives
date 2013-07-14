<spring:htmlEscape defaultHtmlEscape="true" />


<script>
    var $j = jQuery.noConflict();

    $j(document).ready(function()
    {
        $j('.toggleAddTag').click(function(event)
        {
            $j('#addTag').slideToggle('fast');
            event.preventDefault();
        });
        colorVisibleTableRows("table", "white", "whitesmoke");
    });
</script>


<ul id="menu">
    <li class="first">
        <a href="${pageContext.request.contextPath}/admin"><spring:message
            code="admin.title.short" /></a>
    </li>

    <!-- Add further links here -->
    <li <c:if test='<%= request.getRequestURI().contains("/patientNarrativesForm") %>'>class="active"</c:if>>
    <a href="${pageContext.request.contextPath}/module/patientnarratives/patientNarrativesForm.form"><spring:message
            code="patientnarratives.careseeker.form" /></a>
    </li>

    <!-- Add further links here -->
    <li <c:if test='<%= request.getRequestURI().contains("/careProviderConsole") %>'>class="active"</c:if>>
    <a href="${pageContext.request.contextPath}/module/patientnarratives/careProviderConsole.form"><spring:message
            code="patientnarratives.careprovider.console" /></a>
    </li>



</ul>

<h2>
    <spring:message code="patientnarratives.title" />
</h2>

