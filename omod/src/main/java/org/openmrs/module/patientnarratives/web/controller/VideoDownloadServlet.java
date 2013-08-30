/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

package org.openmrs.module.patientnarratives.web.controller;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Obs;
import org.openmrs.api.context.Context;
import org.openmrs.obs.ComplexData;
import org.openmrs.util.OpenmrsConstants;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;

import java.io.*;


public class VideoDownloadServlet extends HttpServlet {

    public static final String defaultContentType = "application/octet-stream";
    public static final long   serialVersionUID   = 1231231L;
    private Log log                = LogFactory.getLog(getClass());

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            if (StringUtils.hasLength(request.getParameter("videoObsId"))) {

                Integer videoObsId = Integer.parseInt(request.getParameter("videoObsId"));

                Obs complexObs = Context.getObsService().getComplexObs(videoObsId, OpenmrsConstants.RAW_VIEW);
                ComplexData complexData = complexObs.getComplexData();
                Object object2 = complexData.getData();  //object will be a BufferedImage object

                ByteArrayOutputStream out = new ByteArrayOutputStream();
                ObjectOutputStream os2 = new ObjectOutputStream(out);
                os2.writeObject(object2);
                byte[] videoObject = out.toByteArray();

//                response.setHeader("Pragma", "No-cache");
//                response.addHeader("Cache-Control", "no-transform, max-age=0");
//                response.setContentType("video/flv")

                response.setContentType("video/x-flv");
                response.setContentLength(videoObject.length);


                byte content[] = new byte[1024 * 4];
                BufferedInputStream is = new BufferedInputStream(new ByteArrayInputStream(videoObject));
                OutputStream os = response.getOutputStream();
                while (is.read(content) != -1) {
                    os.write(content);
                }
                is.close();
                os.flush();
                os.close();

            }
        }

        // Add error handling above and remove this try/catch
        catch (Exception e) {
            log.error("unable to get file", e);
        }
    }

//    public Object complexObsVideo(Integer complexObsId){
//
////        ConceptComplex conceptComplex = Context.getConceptService().getConceptComplex(14);
//        Obs complexObs = Context.getObsService().getComplexObs(complexObsId, OpenmrsConstants.RAW_VIEW);
//        ComplexData complexData = complexObs.getComplexData();
//        Object object2 = complexData.getData();  //object will be a BufferedImage object
//        return  object2;
//    }
//
//    public static byte[] serialize(Object obj) throws IOException {
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        ObjectOutputStream os = new ObjectOutputStream(out);
//        os.writeObject(obj);
//        return out.toByteArray();
//    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
