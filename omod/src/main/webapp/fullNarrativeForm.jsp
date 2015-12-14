<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<openmrs:hasPrivilege privilege="Manage Patient Narratives">

<%@ include file="template/localHeader.jsp"%>

<%@ taglib prefix="kc" tagdir="/WEB-INF/tags/module/patientnarratives/"%>

<openmrs:htmlInclude file="/moduleResources/patientnarratives/videojs/video-js.min.css" />
<openmrs:htmlInclude file="/moduleResources/patientnarratives/videojs/video.dev.js" />

<script>
    videojs.options.flash.swf = "<openmrs:contextPath/>/moduleResources/patientnarratives/videojs/video-js.swf";
</script>

<script>
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

<style>
    form {display: inline; }
</style>

<h2>
    <spring:message code="patientnarratives.fullinfo"/>
</h2>

<b class="boxHeader"><spring:message code="patientnarratives.generalinfo"/></b>
<div class="box" >
    <table id="table">
        <tr>
            <th width="200"><spring:message code="patientnarratives.patientInfo.encID"/></th>
            <td>
                <c:out value="${encounterId} "/> -- <a target="_blank" href="<openmrs:contextPath/>/admin/encounters/encounter.form?encounterId=<c:out value="${encounterId}"/>">[View Encounter]</a>
            </td>
        </tr>
        <tr>
            <th width="200"><spring:message code="patientnarratives.patientInfo.dateOfEnc"/></th>
            <td><openmrs:formatDate date="${encDate}" type="long" /></td>
        </tr>
        <tr>
            <th width="200"><spring:message code="patientnarratives.patientInfo.name"/></th>
            <td><c:out value="${name} "/> </td>
        </tr>
        <tr>
            <th width="200"><spring:message code="patientnarratives.patientInfo.genre"/></th>
            <td><c:out value="${sex} "/> </td>
        </tr>
        <tr>
            <th width="200"><spring:message code="patientnarratives.patientInfo.age"/></th>
            <td><c:out value="${age} "/> </td>
        </tr>
        <tr>
            <th width="200"><spring:message code="patientnarratives.patientInfo.hometown"/></th>
            <td><c:out value="${city} "/> </td>
        </tr>
        <tr>
            <th width="200"><spring:message code="patientnarratives.patientInfo.email"/></th>
            <td> <c:out value="${email}"/> </td>
        </tr>

        <tr>
            <th width="200"><spring:message code="patientnarratives.patientInfo.tel"/></th>
            <td> <c:out value="${tp}"/> </td>
        </tr>

        <tr>
            <th width="200"><spring:message code="patientnarratives.patientInfo.subject"/></th>
            <td> <c:out value="${subject}"/> </td>
        </tr>

        <tr>
            <th width="200"><spring:message code="patientnarratives.patientNarrative"/></th>
            <td>
                <a class="toggleAddTag" href="#"><spring:message code="patientnarratives.showNarrative"/></a>
                <div id="addTag" style="border: 1px black solid; background-color: #e0e0e0; display: none">
                    <c:out value="${story}"/>
                </div>
            </td>
        </tr>
    </table>
</div>

<b class="boxHeader"><spring:message code="patientnarratives.narrativeVideoAndAttach"/></b>
<div class="box" >
    <table id="table2">


        <tr>
            <th width="200"><spring:message code="patientnarratives.video"/></th>
            <td>

                <c:choose>

                    <c:when test="${empty videoObsId}">
                        <spring:message code="patientnarratives.noVideo"/>
                    </c:when>

                    <c:otherwise>
                        <video id="example_video_1" class="video-js vjs-default-skin" controls preload="none" width="640" height="264"
                               poster="http://video-js.zencoder.com/oceans-clip.png"
                               data-setup="{}">
                            <source src="<openmrs:contextPath/>/moduleServlet/patientnarratives/filesDownloadServlet?obsId=<c:out value="${videoObsId}"/>" type='video/flv' />
                            <track kind="captions" src="demo.captions.vtt" srclang="en" label="English"></track><!-- Tracks need an ending tag thanks to IE9 -->
                        </video>

                        <br/>
                        <center>
                        <a target="_blank" href="<openmrs:contextPath/>/moduleServlet/patientnarratives/filesDownloadServlet?obsId=<c:out value="${videoObsId}"/>" >
                        <spring:message code="patientnarratives.downloadVideo"/></a>
                        </center>
                    </c:otherwise>

                </c:choose>

            </td>
        </tr>

        <tr>
            <th width="200"><spring:message code="patientnarratives.attach"/></th>
            <td>
                <c:if test="${empty uploadedFilesMap}">
                    <spring:message code="patientnarratives.noAttach"/>
                </c:if>
                <c:forEach items="${uploadedFilesMap}" var="entry">
                    <a target="_blank" href="<openmrs:contextPath/>/moduleServlet/patientnarratives/filesDownloadServlet?obsId=<c:out value="${entry.key}"/>" >
                    ${entry.value}</a>
                    <br/>
                </c:forEach>
            </td>
        </tr>

    </table>
</div>

<b class="boxHeader"><spring:message code="patientnarratives.statusOfNarrative"/></b>
<div class="box" >
    <table id="table9">
        <tr>
            <th width="200"><spring:message code="patientnarratives.patientInfo.status"/></th>
            <td>
                <c:out value="${status}"/>
                <form method="post">
                    <input type=hidden name=encounterId value=<c:out value="${encounterId}"/> >
                    <br/>
                    <input type="text" name="newStatus" >
                    <input type="submit" value='<spring:message code="patientnarratives.updateStatus"/>' />
                </form>
            </td>

        </tr>
    </table>
</div>

<b class="boxHeader"><spring:message code="patientnarratives.registerPatientAndTransfer"/></b>
<div class="box" >
    <table id="table5">
        <tr>
            <th width="200"><spring:message code="patientnarratives.registeredPatient"/></th>
            <td>
                <c:choose>
                    <c:when test="${patientId == defaultPatientId}">
                        <form method="post">
                            <input type=hidden name=registerEncounterId value=<c:out value="${encounterId}"/> >
                            <input type="submit" value='<spring:message code="patientnarratives.registereThisPatient"/>' />
                        </form>
                    </c:when>
                    <c:otherwise>
                        <c:out value="${patientId}"/> -- <a target="_blank" href="<openmrs:contextPath/>/patientDashboard.form?patientId=<c:out value="${patientId}"/>">[<spring:message code="patientnarratives.viewPatient"/>]</a>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>

    </table>
</div>

<b class="boxHeader"><spring:message code="patientnarratives.comment.submit"/></b>
<div class="box" >
    <form method="post"   enctype="multipart/form-data">
        <textarea name="comment" rows="10" cols="80" type="_moz" size="35"></textarea>
        <br/>
        <input type=hidden name=encounterId value=<c:out value="${encounterId}"/> >
        <input type="submit" value='<spring:message code="patientnarratives.comment.add"/>' />
        <br/>
    </form>
</div>

<b class="boxHeader"><spring:message code="patientnarratives.comment.comments"/></b>
<div class="box" >
    <c:forEach items="${comments}" var="commentObj" >
        <div class="box" >
            <c:out value="${commentObj.comment}"/>
            <div class="description">
                <c:out value="${commentObj.creator.personName}"/>
                <kc:prettyTime date="${commentObj.dateCreated}"></kc:prettyTime>
            </div>
        </div>
    </c:forEach>
</div>

</openmrs:hasPrivilege>

<%@ include file="/WEB-INF/template/footer.jsp" %>
