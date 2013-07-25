<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ page import="net.tanesha.recaptcha.ReCaptcha" %>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory" %>

<openmrs:htmlInclude file="${pageContext.request.contextPath}/moduleResources/patientnarratives/css/styles.css"/>

<script type="text/javascript">
    var RecaptchaOptions = {
        theme : 'clean'
    };

//    var $j = jQuery.noConflict();
//    $j(document).ready(function(){
//
//        logging: true;
//
//        function doSubmitCaptchaForm() {
//            $j("#captchaForm").submit();
//
//            $j("#captchaForm").submit(function(){
//                        var thistarget = this.target;
//                        jQuery.ajax({
//                            data: $j(this).serialize(),
//                            url: this.action,
//                            type: this.method,
//                            error: function() {
//                                $j(thistarget).html("<span class='error'>Failed to submit form!</span>");
//                            },
//                            success: function(results) {
//                                $j(thistarget).html(results);
//                            }
//                        })
//                        return false;
//                    }
//            );
//        }
//    });

</script>


<div id="main-wrap">

    <div id="sidebar">

        <div id="formEntryDialog22">
            <c:catch var="ex">
                <c:choose>
                    <c:when test="${formType == 'HTML-Form'}">
                        <openmrs:portlet url="htmlFormEntry" id="htmlFormEntryForm" moduleId="patientnarratives" />
                    </c:when>
                    <c:when test="${formType == 'X-Form'}">
                        <openmrs:portlet url="xFormEntry" id="xFormEntryForm" moduleId="patientnarratives" />
                    </c:when>
                </c:choose>
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

            <%--WebRTC--%>

                <center>
                    <%--<h2 id="demo">Demo</h2>--%>
                        <p>
                            <video id="video" width="400" height="250" autoplay="autoplay">
                                Your browser does not support the HTML5 video tag.
                            </video>
                        </p>
                    <%--<p><input type="button" id="buttonSnap" value="Take screenshot" onclick="snapshot()" /></p>--%>
                    <p>
                        <input type="button" id="buttonStart" value="Start" onclick="start()" />
                        <input type="button" id="buttonStop" value="Stop" onclick="stop()" />
                    </p>
                    <%--<p><canvas id="canvas" width="640" height="480"></canvas></p>--%>

                    <%----%>

                    <%--<canvas id="myCanvas" width="400" height="200" style="border:1px solid #000000;">--%>
                        <%--Your browser does not support the HTML5 canvas tag.--%>
                    <%--</canvas>--%>
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
            <form id="captchaForm" method="post">
            <%
                ReCaptcha c = ReCaptchaFactory.newReCaptcha("6LdAWuMSAAAAAD3RQXMNBKgI9-1OiYjDx_sl0xYy", "6LdAWuMSAAAAALxWgnM5yRj_tGVRQCk4lit8rLHb", false);
                out.print(c.createRecaptchaHtml(null, null));
            %>

            </br>
            </form>

            <input id="submitMainForm" type="button" value="Submit" />

        </div>
    </div>
</div>

<openmrs:htmlInclude file="/moduleResources/patientnarratives/js/webRTC.js" />


<%@ include file="/WEB-INF/template/footer.jsp"%>


