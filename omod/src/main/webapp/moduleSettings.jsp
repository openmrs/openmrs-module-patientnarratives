<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<openmrs:hasPrivilege privilege="Configure Patient Narratives">

<%@ include file="template/localHeader.jsp"%>
<%@ taglib prefix="fn" uri="/WEB-INF/taglibs/fn.tld" %>

<script type="text/javascript">
    $j(document).ready(function(){
        $j("input[name='formType']").change(function(event)  {
            var formType = $j("input:radio[name ='formType']:checked").val();
            if (formType == 'X-Form'){
                $j('#formID').val('1');
            } else{
                $j('#formID').val('3');
            }
            $j('#updated').fadeIn(800).delay(3000).fadeOut(800)

        });
    });
</script>

<style>
    #updated{
        display: none;
        font-weight: bold;
        color:#FF0000;
    }
</style>

<form method="post" >
    <b class="boxHeader">Module Properties</b>
    <div class="box" >
        <table>
            <tr>
                <td valign="top">Form Type</td>
                <td>
                    <input type="radio" name="formType" value="HTML-Form" <c:if test="${formType == 'HTML-Form'}">checked</c:if>>HTML-Form |
                    <input type="radio" name="formType" value="X-Form" <c:if test="${formType == 'X-Form'}">checked</c:if>>X-Form
                </td>
            </tr>
            <tr>
                <td valign="top">Form ID</td>
                <td>
                    <input type="text" id="formID" name="formID" value="<openmrs:globalProperty key="patientnarratives.formid"/>">
                    <div id='updated'>^ Please change the FormID now..</div>
                </td>
            </tr>
            <tr>
                <td valign="top"><br/></td>
                <td></td>
            </tr>
            <tr>
                <td valign="top">Patient ID</td>
                <td><input type="text" name="patientID" value="<openmrs:globalProperty key="patientnarratives.patientid"/>"></td>
            </tr>
            <tr>
                <td valign="top">Encounter Type</td>
                <td><input type="text" name="encType" value="<openmrs:globalProperty key="patientnarratives.enctype"/>"></td>
            </tr>
            <tr>
                <td> </td>
                <td>
                    <input type="submit" value="Save" />
                </td>
            </tr>
        </table>
    </div>

</openmrs:hasPrivilege>


<%@ include file="/WEB-INF/template/footer.jsp"%>

