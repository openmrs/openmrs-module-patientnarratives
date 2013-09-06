<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
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

<b class="boxHeader">General Information</b>
<div class="box" >
    <table id="table">
        <tr>
            <th width="100">Encounter Id</th>
            <td>
                <c:out value="${encounterId} "/> -- <a target="_blank" href="<openmrs:contextPath/>/admin/encounters/encounter.form?encounterId=<c:out value="${encounterId}"/>">[View Encounter]</a>
            </td>
        </tr>
        <tr>
            <th width="400">Date of Encounter</th>
            <td><openmrs:formatDate date="${encDate}" type="long" /></td>
        </tr>
        <tr>
            <th width="300">Patient Name</th>
            <td><c:out value="${name} "/> </td>
        </tr>
        <tr>
            <th width="400">Gender</th>
            <td><c:out value="${sex} "/> </td>
        </tr>
        <tr>
            <th width="300">Age</th>
            <td><c:out value="${age} "/> </td>
        </tr>
        <tr>
            <th width="400">Hometown</th>
            <td><c:out value="${city} "/> </td>
        </tr>
        <tr>
            <th width="400">Email</th>
            <td> <c:out value="${email}"/> </td>
        </tr>

        <tr>
            <th width="400">Telephone</th>
            <td> <c:out value="${tp}"/> </td>
        </tr>

        <tr>
            <th width="400">Subject</th>
            <td> <c:out value="${subject}"/> </td>
        </tr>

        <tr>
            <th width="400">Patient Narrative</th>
            <td>
                <a class="toggleAddTag" href="#">Show story</a>
                <div id="addTag" style="border: 1px black solid; background-color: #e0e0e0; display: none">
                    <c:out value="${story}"/>
                </div>
            </td>
        </tr>
    </table>
</div>

<b class="boxHeader">Narrative Video and Attachments</b>
<div class="box" >
    <table id="table2">


        <tr>
            <th width="400">Video</th>
            <td>

                <video id="example_video_1" class="video-js vjs-default-skin" controls preload="none" width="640" height="264"
                       poster="http://video-js.zencoder.com/oceans-clip.png"
                       data-setup="{}">
                    <source src="<openmrs:contextPath/>/moduleServlet/patientnarratives/filesDownloadServlet?obsId=<c:out value="${videoObsId}"/>" type='video/flv' />
                    <track kind="captions" src="demo.captions.vtt" srclang="en" label="English"></track><!-- Tracks need an ending tag thanks to IE9 -->
                </video>


                <br/>
                <a target="_blank" href="<openmrs:contextPath/>/moduleServlet/patientnarratives/filesDownloadServlet?obsId=<c:out value="${videoObsId}"/>" >
                Download Video</a>

                <%--<a target="_blank" href="<openmrs:contextPath/>/moduleServlet/feedback/fileDownloadServlet?feedbackScreenshotId=<c:out value="${feedback.encounterId}"/>" >--%>
                <%--<img src="<openmrs:contextPath/>/moduleServlet/feedback/fileDownloadServlet?feedbackScreenshotId=<c:out value="${feedback.encounterId}"/>" height="200" width="400">--%>
                <%--</img>--%>
            </td>
        </tr>


        <tr>
            <th width="400">Attachments</th>
            <td>
                <c:forEach items="${uploadedFilesMap}" var="entry">
                    <a target="_blank" href="<openmrs:contextPath/>/moduleServlet/patientnarratives/filesDownloadServlet?obsId=<c:out value="${entry.key}"/>" >
                    ${entry.value}</a>
                    <br/>
                </c:forEach>
            </td>
        </tr>

    </table>
</div>



<%----%>

<%--<openmrs:hasPrivilege privilege="Admin Feedback">--%>

    <%--<b class="boxHeader"><spring:message code="feedback.manage.form.assign"/></b>--%>
    <%--<div class="box" >--%>
        <%--<table id="table4">--%>

            <%--<tr>--%>
                <%--<th width="400"><spring:message code="feedback.assigned.user"/> </th>--%>
                <%--<form method="post">--%>
                    <%--<td>--%>
                        <%--<input type=hidden name=encounterId value=<c:out value="${feedback.encounterId}"/> >--%>
                        <%--<select name="delAssignedUser">--%>
                            <%--<c:forEach items="${assigned_users}" var="usersObj" >--%>
                                <%--<option value="<c:out value="${usersObj.username}"/>"> <c:out value="${usersObj.username}"/> </option>--%>
                            <%--</c:forEach>--%>
                            <%--&lt;%&ndash;<option value="-" selected="selected">-</option>&ndash;%&gt;--%>
                        <%--</select>--%>
                    <%--</td>--%>
                    <%--<td>--%>
                        <%--<input type="submit" id="delAssigned" name="delAssigned" value="<spring:message code="feedback.delete.user" />" />--%>
                    <%--</td>--%>
                <%--</form>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<th></th>--%>
                <%--<form method="post">--%>
                    <%--<td>--%>
                        <%--<input type=hidden name=encounterId value=<c:out value="${feedback.encounterId}"/> >--%>
                        <%--<select name="addAssignedUser">--%>
                            <%--<c:forEach items="${allusers}" var="usersObj" >--%>
                                <%--<option value="<c:out value="${usersObj.username}"/>"> <c:out value="${usersObj.username}"/> </option>--%>
                            <%--</c:forEach>--%>
                            <%--&lt;%&ndash;<option value="-" selected="selected">-</option>&ndash;%&gt;--%>
                        <%--</select>--%>
                    <%--</td>--%>
                    <%--<td>--%>
                        <%--<input type="submit" id="addAssigned" name="addAssigned" value="<spring:message code="feedback.add.user" />" />--%>

                    <%--</td>--%>
                <%--</form>--%>
            <%--</tr>--%>
        <%--</table>--%>
    <%--</div>--%>

<%--</openmrs:hasPrivilege>--%>
<%----%>

    <b class="boxHeader">Status of the Narrative</b>
    <div class="box" >
        <table id="table9">
            <tr>
                <th width="400">Status</th>
                <td>
                    <c:out value="${status}"/>
                    <form method="post">
                        <input type=hidden name=encounterId value=<c:out value="${encounterId}"/> >
                        <br/>
                        <input type="text" name="newStatus" >
                    <%--<select name="status">--%>
                            <%--<c:forEach items="${statuses}" var="statusObj" >--%>
                                <%--<option value="<c:out value="${statusObj.status}"/>"> <c:out value="${statusObj.status}"/> </option>--%>
                            <%--</c:forEach>--%>
                            <%--<option value="-" selected="selected">-</option>--%>
                        <%--</select>--%>
                        <input type="submit" value="Update Status" />
                    </form>
                </td>

            </tr>
        </table>
    </div>

<b class="boxHeader">Register patient & Transfer the Encounter</b>
<div class="box" >
    <table id="table5">
        <tr>
            <th width="400">(Registered) Patient Id</th>
            <td>
                <c:choose>
                    <c:when test="${patientId == defaultPatientId}">
                        <form method="post">
                            <input type=hidden name=registerEncounterId value=<c:out value="${encounterId}"/> >
                            <input type="submit" value="Register" />
                        </form>
                    </c:when>
                    <c:otherwise>
                        <c:out value="${patientId}"/> -- <a target="_blank" href="<openmrs:contextPath/>/patientDashboard.form?patientId=<c:out value="${patientId}"/>">[View Patient]</a>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>

    </table>
</div>

<%--<form method="post">--%>
<%--<input type="hidden" name="textEncID" value="${encountersObj.encounterId}">--%>
<%--<input type="submit" value="Register">--%>
<%--</form>--%>


    <%--<div class="box" >--%>
        <%--<table id="table75">--%>
            <%--<tr>--%>
                <%--<th width="400"></th>--%>
                <%--<td>--%>
                    <%--&lt;%&ndash;<form method="post">&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<input type=hidden name=delete value= "1"/>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<input type=hidden name=encounterId value="${feedback.encounterId}"/>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<input type="submit" value="<spring:message code="feedback.delete"/>" />&ndash;%&gt;--%>
                    <%--&lt;%&ndash;</form>&ndash;%&gt;--%>
                    <%--<form method="get" action="<openmrs:contextPath/>/module/feedback/forwardFeedback.form">--%>
                        <%--<input type=hidden name=encounterId value="${feedback.encounterId}"/>--%>
                        <%--<input type="submit" value="<spring:message code="feedback.forward"/>" />--%>
                    <%--</form>--%>
                <%--</td>--%>
            <%--</tr>--%>

        <%--</table>--%>
    <%--</div>--%>


<b class="boxHeader">Submit a Comment</b>
<div class="box" >
    <table id="table6">

        <form method="post"   enctype="multipart/form-data">
            <tr>
                <th width="400">Attachments</th>
                <td><input type="file" accept="image" name="file" size="40" />
                    <div class="description">
                        Attachments
                    </div>
                </td>
            </tr>
            <tr>
                <th valign="top">Comment </th>
                <td><textarea name="comment" rows="10" cols="120" type="_moz" size="35"></textarea> </td>
            </tr>

            <td>
            </td>
            <td>
                <input type=hidden name=encounterId value=<c:out value="${encounterId}"/> >
                <input type="submit" value="Add Comment" />
            </td>
        </form>

    </table>
</div>


<b class="boxHeader"><spring:message code="feedback.manage.form.comments"/></b>
<div class="box" >
    <%--<table id="table3">--%>
    <%--<tr>--%>
    <%--<th width="400"><spring:message code="feedback.reply.list"/></th>--%>
    <%--<td colspan="2">--%>
    <c:forEach items="${comments}" var="commentObj" >
        <c:out value="${commentObj.comment}"/>
        <div class="description">
            <%--<c:if test="${not empty commentObj.attachment}">--%>
                <%--<a href="<openmrs:contextPath/>/moduleServlet/feedback/fileDownloadServlet?narrativesCommentId=<c:out value="${commentObj.narrativesCommentId}"/> ">--%>
                <%--Attachment--%>
                <%--</a>--%>
            <%--</c:if >--%>

            <c:out value="${commentObj.creator.personName}"/>
            <kc:prettyTime date="${commentObj.dateCreated}"></kc:prettyTime>
        </div>
    </c:forEach>
    <%--</td>--%>
    <%--</tr>--%>
    <%--</table>--%>
</div>

<%@ include file="/WEB-INF/template/footer.jsp" %>

<%--<script type='text/javascript'>--%>
    <%--jwplayer('mediaspace').setup({--%>
        <%--'flashplayer': '<openmrs:contextPath/>/moduleResources/patientnarratives/jwplayer/jwplayer.flash.swf',--%>
        <%--'file': '<openmrs:contextPath/>/moduleServlet/patientnarratives/fileDownloadServlet?videoObsId=<c:out value="${videoObsId}"/>',--%>
        <%--'controlbar': 'bottom',--%>
        <%--'width': '470',--%>
        <%--'height': '320'--%>
    <%--});--%>
<%--</script>--%>