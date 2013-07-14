<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ page import="net.tanesha.recaptcha.ReCaptcha" %>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory" %>

<openmrs:htmlInclude file="${pageContext.request.contextPath}/moduleResources/patientnarratives/css/styles.css"/>

<script>
    var $j = jQuery.noConflict();

    $j(document).ready(function(){
        logging: true;
        $j("#submit").click(function() {
            submitForm();
        });
    });

</script>

<div id="main-wrap">

    <div id="sidebar">

        <div id="formEntryDialog22">
            <%--<openmrs:portlet url="personFormEntry" personId="${patient.personId}" id="encounterTabFormEntryPopup" parameters="showLastThreeEncounters=false|returnUrl=${model.formEntryReturnUrl}"/>--%>
            <%--<openmrs:portlet url="${extension.portletUrl}" moduleId="${extension.moduleId}" id="${extension.portletUrl}" />--%>

                <c:catch var="ex">
                    <%--<openmrs:portlet url="xFormEntry" id="submitPatientNarrative2" moduleId="patientnarratives" />--%>

                    <openmrs:portlet url="htmlFormEntry" id="submitPatientNarrative2" moduleId="patientnarratives" />
                </c:catch>
                <c:if test="${not empty ex}">
                    <div class="error">
                        <openmrs:message code="fix.error.plain"/> <br/>
                        <b>${ex}</b>
                        <div style="height: 200px; width: 800px; overflow: scroll">
                            <c:forEach var="row" items="${ex.cause.stackTrace}">
                                ${row}<br/>
                            </c:forEach>
                        </div>
                    </div>
                </c:if>
        </div>


    </div>

    <div id="content-wrap">
        <div id="info-wrap">
            <center>
                <canvas id="myCanvas" width="400" height="200" style="border:1px solid #000000;">
                    Your browser does not support the HTML5 canvas tag.
                </canvas>
            </center>

        </div>
        <div id="info-wrap">
            </br></br><span>Patient Narrative</span>
            <textarea rows="4" cols="50">
                Describe your narrative here.
            </textarea>
        </div>
        <div id="info-wrap">
            </br></br><span>Upload file (X-ray, reports, etc)</span>
            <input type="file" name="file" id="file" size="40"/>
        </div>
        <div id="info-wrap">
            </br></br>
            <%--<form action="" method="post">--%>
            <%
                ReCaptcha c = ReCaptchaFactory.newReCaptcha("6LdAWuMSAAAAAD3RQXMNBKgI9-1OiYjDx_sl0xYy", "6LdAWuMSAAAAALxWgnM5yRj_tGVRQCk4lit8rLHb", false);
                out.print(c.createRecaptchaHtml(null, null));
            %>

            </br>
            <input id="submit" type="button" value="Submit" />
            <%--</form>--%>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/template/footer.jsp"%>


