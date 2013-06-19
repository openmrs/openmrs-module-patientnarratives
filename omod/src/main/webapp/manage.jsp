<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>

<openmrs:htmlInclude file="${pageContext.request.contextPath}/moduleResources/patientnarratives/js/bootstrap.min.js"/>
<openmrs:htmlInclude file="${pageContext.request.contextPath}/moduleResources/patientnarratives/css/bootstrap.css"/>


<p>Hello ${user.systemId}!</p>

<h1>Hello, world!</h1>

<form>
    <fieldset>
        <legend>Legend</legend>
        <label>Label name</label>
        <input type="text" placeholder="Type something…">
        <span class="help-block">Example block-level help text here.</span>
        <label class="checkbox">
            <input type="checkbox"> Check me out
        </label>
        <button type="submit" class="btn">Submit</button>
    </fieldset>
</form>


<%@ include file="/WEB-INF/template/footer.jsp"%>


