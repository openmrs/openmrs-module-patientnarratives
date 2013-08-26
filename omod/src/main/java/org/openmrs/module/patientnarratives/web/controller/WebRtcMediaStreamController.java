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

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.IAudioSamples;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IPacket;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;
import com.xuggle.xuggler.IVideoPicture;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

@Controller
public class WebRtcMediaStreamController {

    private String returnUrl;
    public final static String FORM_PATH = "/module/patientnarratives/webRtcMedia.form";
    protected final Log log = LogFactory.getLog(getClass());

    private String mergedUrl = "/home/harshadura/gsoc2013/TestWebm/mergedFile1.flv";

    @RequestMapping(FORM_PATH)
    public ModelAndView handleRequest(HttpServletRequest request) throws Exception {

        String encId = request.getParameter("textEncID1");

        String encId2 = request.getParameter("textEncID2");

//        try {
//            String encId3 = request.getParameter("textEncID3");
//
//            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
//            for (FileItem item : items) {
//                if (item.isFormField()) {
//                    // Process regular form field (input type="text|radio|checkbox|etc", select, etc).
//                    String fieldname = item.getFieldName();
//                    String fieldvalue = item.getString();
//                } else {
//                    // Process form file field (input type="file").
//                    String fieldname = item.getFieldName();
//                    byte[] filecontent = item.get();
//
//                    if (fieldname.equals("audio")) {
//                        byte[] audioData = filecontent;
//                        OutputStream out1 = new FileOutputStream(new File(audioUrl));
//                        out1.write(audioData);
//                        out1.close();
//                    }
//                    else if (fieldname.equals("video")) {
//                        byte[] videoData = filecontent;
//                        OutputStream out2 = new FileOutputStream(new File(videoUrl));
//                        out2.write(videoData);
//                        out2.close();
//                    }
//                }
//            }
//        } catch (FileUploadException e) {
//            throw new ServletException("Cannot parse multipart request.", e);
//        }

        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile videofile             = (MultipartFile) multipartRequest.getFile("video");
            MultipartFile audiofile             = (MultipartFile) multipartRequest.getFile("audio");

//            byte[] videoData = videofile.getBytes();
//            byte[] audioData = audiofile.getBytes();

//            if (!videofile.isEmpty()) {
//                if (videofile.getSize() > 0) {  // limit video length or size.
//
//                    OutputStream out1 = new FileOutputStream(new File(videoUrl));
//                    out1.write(videoData);
//                    out1.close();
//
//                    OutputStream out2 = new FileOutputStream(new File(audioUrl));
//                    out2.write(audioData);
//                    out2.close();
//                }
//            mergeVideo(videofile, audiofile);


            try{
                IMediaWriter mWriter = ToolFactory.makeWriter(mergedUrl); //output file

                IContainer containerVideo = IContainer.make();
                IContainer containerAudio = IContainer.make();

                InputStream videoInputStream = videofile.getInputStream();
                InputStream audioInputStream = audiofile.getInputStream();

//            String filenamevideo = videoUrl; //"f:/testvidfol/video.mp4"; //this is the input file for video. you can change extension
//            String filenameaudio = audioUrl; //"f:/testvidfol/audio.wav"; //this is the input file for audio. you can change extension
//
//            IMediaWriter mWriter = ToolFactory.makeWriter(mergedUrl); //output file

//            IContainer containerVideo = IContainer.make();
//            IContainer containerAudio = IContainer.make();

                if (containerVideo.open(videoInputStream, null) < 0)
                    throw new IllegalArgumentException("Cant find " + videoInputStream);

                if (containerAudio.open(audioInputStream, null) < 0)
                    throw new IllegalArgumentException("Cant find " + audioInputStream);

                int numStreamVideo = containerVideo.getNumStreams();
                int numStreamAudio = containerAudio.getNumStreams();

                System.out.println("Number of video streams: "+numStreamVideo + "\n" + "Number of audio streams: "+numStreamAudio );

                int videostreamt = -1; //this is the video stream id
                int audiostreamt = -1;

                IStreamCoder  videocoder = null;

                for(int i=0; i<numStreamVideo; i++){
                    IStream stream = containerVideo.getStream(i);
                    IStreamCoder code = stream.getStreamCoder();

                    if(code.getCodecType() == ICodec.Type.CODEC_TYPE_VIDEO)
                    {
                        videostreamt = i;
                        videocoder = code;
                        break;
                    }

                }

                for(int i=0; i<numStreamAudio; i++){
                    IStream stream = containerAudio.getStream(i);
                    IStreamCoder code = stream.getStreamCoder();

                    if(code.getCodecType() == ICodec.Type.CODEC_TYPE_AUDIO)
                    {
                        audiostreamt = i;
                        break;
                    }

                }

                if (videostreamt == -1) throw new RuntimeException("No video steam found");
                if (audiostreamt == -1) throw new RuntimeException("No audio steam found");

                if(videocoder.open()<0 ) throw new RuntimeException("Cant open video coder");
                IPacket packetvideo = IPacket.make();

                IStreamCoder audioCoder = containerAudio.getStream(audiostreamt).getStreamCoder();

                if(audioCoder.open()<0 ) throw new RuntimeException("Cant open audio coder");
                mWriter.addAudioStream(1, 1, audioCoder.getChannels(), audioCoder.getSampleRate());

                mWriter.addVideoStream(0, 0, videocoder.getWidth(), videocoder.getHeight());

                IPacket packetaudio = IPacket.make();

                while(containerVideo.readNextPacket(packetvideo) >= 0 ||
                        containerAudio.readNextPacket(packetaudio) >= 0){

                    if(packetvideo.getStreamIndex() == videostreamt){

                        //video packet
                        IVideoPicture picture = IVideoPicture.make(videocoder.getPixelType(),
                                videocoder.getWidth(),
                                videocoder.getHeight());
                        int offset = 0;
                        while (offset < packetvideo.getSize()){
                            int bytesDecoded = videocoder.decodeVideo(picture,
                                    packetvideo,
                                    offset);
                            if(bytesDecoded < 0) throw new RuntimeException("bytesDecoded not working");
                            offset += bytesDecoded;

                            if(picture.isComplete()){
                                System.out.println(picture.getPixelType());
                                mWriter.encodeVideo(0, picture);

                            }
                        }
                    }

                    if(packetaudio.getStreamIndex() == audiostreamt){
                        //audio packet

                        IAudioSamples samples = IAudioSamples.make(512,
                                audioCoder.getChannels(),
                                IAudioSamples.Format.FMT_S32);
                        int offset = 0;
                        while(offset<packetaudio.getSize())
                        {
                            int bytesDecodedaudio = audioCoder.decodeAudio(samples,
                                    packetaudio,
                                    offset);
                            if (bytesDecodedaudio < 0)
                                throw new RuntimeException("could not detect audio");
                            offset += bytesDecodedaudio;

                            if (samples.isComplete()){
                                mWriter.encodeAudio(1, samples);

                            }
                        }

                    }

                }
            }catch (Exception e){
                e.getStackTrace();
            }


            }


        returnUrl = request.getContextPath() + "/module/patientnarratives/patientNarrativesForm.form";
        return new ModelAndView(new RedirectView(returnUrl));
    }

    public void mergeVideo(MultipartFile videofile, MultipartFile audiofile)
    {
//        try{
//            IMediaWriter mWriter = ToolFactory.makeWriter(mergedUrl); //output file
//
//            IContainer containerVideo = IContainer.make();
//            IContainer containerAudio = IContainer.make();
//
//            InputStream videoInputStream = videofile.getInputStream();
//            InputStream audioInputStream = audiofile.getInputStream();
//
////            String filenamevideo = videoUrl; //"f:/testvidfol/video.mp4"; //this is the input file for video. you can change extension
////            String filenameaudio = audioUrl; //"f:/testvidfol/audio.wav"; //this is the input file for audio. you can change extension
////
////            IMediaWriter mWriter = ToolFactory.makeWriter(mergedUrl); //output file
//
////            IContainer containerVideo = IContainer.make();
////            IContainer containerAudio = IContainer.make();
//
//            if (containerVideo.open(videoInputStream, null) < 0)
//                throw new IllegalArgumentException("Cant find " + videoInputStream);
//
//            if (containerAudio.open(audioInputStream, null) < 0)
//                throw new IllegalArgumentException("Cant find " + audioInputStream);
//
//            int numStreamVideo = containerVideo.getNumStreams();
//            int numStreamAudio = containerAudio.getNumStreams();
//
//            System.out.println("Number of video streams: "+numStreamVideo + "\n" + "Number of audio streams: "+numStreamAudio );
//
//            int videostreamt = -1; //this is the video stream id
//            int audiostreamt = -1;
//
//            IStreamCoder  videocoder = null;
//
//            for(int i=0; i<numStreamVideo; i++){
//                IStream stream = containerVideo.getStream(i);
//                IStreamCoder code = stream.getStreamCoder();
//
//                if(code.getCodecType() == ICodec.Type.CODEC_TYPE_VIDEO)
//                {
//                    videostreamt = i;
//                    videocoder = code;
//                    break;
//                }
//
//            }
//
//            for(int i=0; i<numStreamAudio; i++){
//                IStream stream = containerAudio.getStream(i);
//                IStreamCoder code = stream.getStreamCoder();
//
//                if(code.getCodecType() == ICodec.Type.CODEC_TYPE_AUDIO)
//                {
//                    audiostreamt = i;
//                    break;
//                }
//
//            }
//
//            if (videostreamt == -1) throw new RuntimeException("No video steam found");
//            if (audiostreamt == -1) throw new RuntimeException("No audio steam found");
//
//            if(videocoder.open()<0 ) throw new RuntimeException("Cant open video coder");
//            IPacket packetvideo = IPacket.make();
//
//            IStreamCoder audioCoder = containerAudio.getStream(audiostreamt).getStreamCoder();
//
//            if(audioCoder.open()<0 ) throw new RuntimeException("Cant open audio coder");
//            mWriter.addAudioStream(1, 1, audioCoder.getChannels(), audioCoder.getSampleRate());
//
//            mWriter.addVideoStream(0, 0, videocoder.getWidth(), videocoder.getHeight());
//
//            IPacket packetaudio = IPacket.make();
//
//            while(containerVideo.readNextPacket(packetvideo) >= 0 ||
//                    containerAudio.readNextPacket(packetaudio) >= 0){
//
//                if(packetvideo.getStreamIndex() == videostreamt){
//
//                    //video packet
//                    IVideoPicture picture = IVideoPicture.make(videocoder.getPixelType(),
//                            videocoder.getWidth(),
//                            videocoder.getHeight());
//                    int offset = 0;
//                    while (offset < packetvideo.getSize()){
//                        int bytesDecoded = videocoder.decodeVideo(picture,
//                                packetvideo,
//                                offset);
//                        if(bytesDecoded < 0) throw new RuntimeException("bytesDecoded not working");
//                        offset += bytesDecoded;
//
//                        if(picture.isComplete()){
//                            System.out.println(picture.getPixelType());
//                            mWriter.encodeVideo(0, picture);
//
//                        }
//                    }
//                }
//
//                if(packetaudio.getStreamIndex() == audiostreamt){
//                    //audio packet
//
//                    IAudioSamples samples = IAudioSamples.make(512,
//                            audioCoder.getChannels(),
//                            IAudioSamples.Format.FMT_S32);
//                    int offset = 0;
//                    while(offset<packetaudio.getSize())
//                    {
//                        int bytesDecodedaudio = audioCoder.decodeAudio(samples,
//                                packetaudio,
//                                offset);
//                        if (bytesDecodedaudio < 0)
//                            throw new RuntimeException("could not detect audio");
//                        offset += bytesDecodedaudio;
//
//                        if (samples.isComplete()){
//                            mWriter.encodeAudio(1, samples);
//
//                        }
//                    }
//
//                }
//
//            }
//        }catch (Exception e){
//            e.getStackTrace();
//        }
    }
}
