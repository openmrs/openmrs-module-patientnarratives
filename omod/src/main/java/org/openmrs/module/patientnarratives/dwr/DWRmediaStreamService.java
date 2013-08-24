package org.openmrs.module.patientnarratives.dwr;


import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class DWRmediaStreamService {

    private String audioUrl = "/home/harshadura/gsoc2013/TestWebm/audioFile1.wav";
    private String videoUrl = "/home/harshadura/gsoc2013/TestWebm/videoFile1.webm";
    private String mergedUrl = "/home/harshadura/gsoc2013/TestWebm/mergedFile1.flv";


//    public boolean uploadVideo
//        if (request instanceof MultipartHttpServletRequest) {
//            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//            MultipartFile videofile             = (MultipartFile) multipartRequest.getFile("video");
//            MultipartFile audiofile             = (MultipartFile) multipartRequest.getFile("audio");

//        try{


//            mergeVideo();

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
//
//            }
//        }

//        }catch(Exception e){

//        }
//        returnUrl = request.getContextPath() + "/module/patientnarratives/patientNarrativesForm.form";
//        return new ModelAndView(new RedirectView(returnUrl));
//        return false;
//    }

    public boolean uploadVideo(MultipartFile videofile, MultipartFile audiofile){

        try{
//            byte[] videoData = videofile.getBytes();
//            byte[] audioData = audiofile.getBytes();

//            String filenamevideo = videoUrl; //"f:/testvidfol/video.mp4"; //this is the input file for video. you can change extension
//            String filenameaudio = audioUrl; //"f:/testvidfol/audio.wav"; //this is the input file for audio. you can change extension

            IMediaWriter mWriter = ToolFactory.makeWriter(mergedUrl); //output file

            IContainer containerVideo = IContainer.make();
            IContainer containerAudio = IContainer.make();

            InputStream videoInputStream = videofile.getInputStream();
            InputStream audioInputStream = audiofile.getInputStream();

            if (containerVideo.open(videoInputStream, null) < 0)
                throw new IllegalArgumentException("Cant find " + videoInputStream);

            if (containerAudio.open(audioInputStream, null) < 0)
                throw new IllegalArgumentException("Cant find " + audioInputStream);

            int numStreamVideo = containerVideo.getNumStreams();
            int numStreamAudio = containerAudio.getNumStreams();

            System.out.println("Number of video streams: "+numStreamVideo + "\n" + "Number of audio streams: "+numStreamAudio );

            int videostreamt = -1; //this is the video stream id
            int audiostreamt = -1;

            IStreamCoder videocoder = null;

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

            return true;

        }catch (Exception e){
            e.getStackTrace();
            return false;
        }
    }
}
