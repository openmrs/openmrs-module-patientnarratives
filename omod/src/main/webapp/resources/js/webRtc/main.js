(function() {

    var videoBlob;
    var audioBlob;

    var constraints = { video: true, audio: true },
        recorder = new RecordRTC({
            enable: constraints,
            video_width: 320,
            video_height: 240,
            canvas_width: 320,
            canvas_height: 240,
            videoElem: document.getElementById("client-video")

        });

    // get user media
    recorder.getMedia(recorder.setMedia, function() {
        console.log("get user media failed!");
    });

    recorder.onVideoReady(function(blob) {
        attachLink(blob, "video");
        videoBlob = blob;
    });

    recorder.onAudioReady(function(blob) {
        attachLink(blob, "audio");
        audioBlob = blob;
    });

    document.getElementById("start-record").addEventListener("click", function() {
        $j('#start-record').attr('disabled','disabled');
        $j('#stop-record').removeAttr('disabled');
        progressBar.value = 0;
        recorder.start();
    });

    document.getElementById("stop-record").addEventListener("click", function() {
        $j('#stop-record').attr('disabled','disabled');
        $j('#upload-record').removeAttr('disabled');
        $j('#clear-record').removeAttr('disabled');
        recorder.stop();
    });

    document.getElementById("upload-record").addEventListener("click", function() {
        $j('#upload-record').attr('disabled','disabled');
        $j('#clear-record').attr('disabled','disabled');
        $j('#start-record').removeAttr('disabled');
        sendAudioVideoBlobs();
    });


    document.getElementById("clear-record").addEventListener("click", function() {
        videoBlob = null;
        audioBlob = null;
        $j('#upload-record').attr('disabled','disabled');
        $j('#clear-record').attr('disabled','disabled');
        $j('#start-record').removeAttr('disabled');
    });

    function attachLink(blob, str) {
        var a = document.createElement('a');
            a.target = '_blank';
            a.innerHTML = 'Open Recorded ' + str;

        var reader = new FileReader();
        reader.onload = function(e) {
            a.href = e.target.result;
        };
        reader.readAsDataURL(blob);

        result.appendChild(a);
    }

    var result = document.getElementById('result');
    var progressBar = document.getElementById('videoUploadProgressBar');
    var uploadStatus = document.getElementById('uploadStatus');

    function sendAudioVideoBlobs(){

        console.log("Sending blobs!");

        var recordForm = new FormData();

        recordForm.append("video", videoBlob);
        recordForm.append("audio", audioBlob);

        var oReq = new XMLHttpRequest();

        formSubmitURL = "webRtcMedia.form";
        console.log(formSubmitURL);

        oReq.open("POST", formSubmitURL);

        oReq.onload = function(e) {};

        // Listen to the upload progress.
        oReq.upload.onprogress = function(e) {
            if (e.lengthComputable) {
                progressBar.value = (e.loaded / e.total) * 100;
                progressBar.textContent = progressBar.value; // Fallback for unsupported browsers.
            }

            if(progressBar.value == 100){
                $j('#uploadStatus').fadeIn(800).delay(2000).fadeOut(800)
                progressBar.value = 0;
            }
        };

        oReq.send(recordForm);

    }

})();

