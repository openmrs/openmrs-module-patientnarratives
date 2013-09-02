<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ page import="net.tanesha.recaptcha.ReCaptcha" %>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory" %>

<openmrs:htmlInclude file="/dwr/engine.js" />
<openmrs:htmlInclude file="/dwr/util.js" />
<openmrs:htmlInclude file="/dwr/interface/DWRreCaptchaService.js" />

<openmrs:htmlInclude file="${pageContext.request.contextPath}/moduleResources/patientnarratives/css/styles.css"/>

<script type="text/javascript">
    var RecaptchaOptions = {
        theme : 'clean'
    };

    EnableSubmit = function(val)
    {
        var sbmt = document.getElementById("submitMainForm");

        if (val.checked == true)
        {
            sbmt.disabled = false;
        }
        else
        {
            sbmt.disabled = true;
        }
    }

    $j(document).ready(function(){

        logging: true;

        $j("#submitMainForm").click(function() {
            var recordForm = new FormData();

            recordForm.append("video", localStorage.getItem('videoFile'));
            recordForm.append("audio", localStorage.getItem('audioFile'));

//        data.append("video", videoBlob, (new Date()).getTime() + ".webm");
//        data.append("audio", audioBlob, (new Date()).getTime() + ".wav");

            var oReq = new XMLHttpRequest();

            formSubmitURL = "http://localhost:8088/openmrs/module/patientnarratives/webRtcMedia.form";
            console.log(formSubmitURL);

            oReq.open("POST", formSubmitURL);
            oReq.send(recordForm);


        });
    });

    /////////////////// WebRTC


    /////////////////////////


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

                    <video id="client-video" width="320" height="240" autoplay loop muted></video>

                    <br/><br/>

                    <button id="start-record">Start Recording</button>
                    <button id="stop-record">Stop</button>
                    <button id="upload-record">Upload</button>

                    <br/><br/>

                    <div id="result"></div>

                    <openmrs:htmlInclude file="/moduleResources/patientnarratives/js/webRtc/whammy.js" />
                    <openmrs:htmlInclude file="/moduleResources/patientnarratives/js/webRtc/StereoRecorder.js" />
                    <openmrs:htmlInclude file="/moduleResources/patientnarratives/js/webRtc/record-rtc.js" />
                    <openmrs:htmlInclude file="/moduleResources/patientnarratives/js/webRtc/main.js" />

                <%--<p>--%>
                    <%--<video id="video" width="400" height="250" autoplay="autoplay">--%>
                        <%--Your browser does not support the HTML5 video tag.--%>
                    <%--</video>--%>
                <%--</p>--%>
                <%--<p><input type="button" id="buttonSnap" value="Take screenshot" onclick="snapshot()" /></p>--%>
                <%--<p>--%>
                    <%--<input type="button" id="buttonStart" value="Start" onclick="start()" />--%>
                    <%--<input type="button" id="buttonStop" value="Stop" onclick="stop()" />--%>
                <%--</p>--%>

                <progress min="0" max="100" value="0">0% complete</progress>

                <%--<p><canvas id="canvas" width="640" height="480"></canvas></p>--%>

                <%----%>

                <%--<canvas id="myCanvas" width="400" height="200" style="border:1px solid #000000;">--%>
                <%--Your browser does not support the HTML5 canvas tag.--%>
                <%--</canvas>--%>
            </center>

        </div>
        <%--<div id="info-wrap">--%>
            <%--</br></br>--%>
            <%--<table border="1">--%>
                <%--<tr>--%>
                    <%--<td> <span>Additional Information</span></td>--%>
                    <%--<td>--%>
                        <%--<textarea rows="4" cols="50">--%>
                            <%--Describe here.--%>
                        <%--</textarea>--%>
                    <%--</td>--%>
                <%--</tr>--%>
            <%--</table>--%>
        <%--</div>--%>
        <div id="info-wrap">

            </br></br>
            <table border="1">
                <tr>
                    <td> <span>Upload files (X-ray, reports, etc)</span> </td>
                    <td>
                        <input type="file" name="file" id="file" size="40"/>
                    </td>
                </tr>
            </table>

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

            <input type="checkbox" name="agreement" value="Accept" onClick="EnableSubmit(this)">

            <span>
              <%--<strong>--%>
                  I agree to the OpenMRS - Patient Narratives Module <br> <a target="_blank" href="http://openmrs.org/privacy/">Terms of Service</a> and <a target="_blank" id="PrivacyLink" href="http://openmrs.org/privacy/">Privacy Policy</a>
              <%--</strong>--%>
            </span>
            <br> <br>

            <input id="submitMainForm" type="button" value="Submit" disabled />

        </div>
    </div>
</div>

<%@ include file="/WEB-INF/template/footer.jsp"%>


