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
        localStorage.setItem('videoFile', videoBlob);
//        upload(blob);
    });

    recorder.onAudioReady(function(blob) {
        attachLink(blob, "audio");
        audioBlob = blob;
        localStorage.setItem('audioFile', audioBlob);
//        upload(blob);
    });

    document.getElementById("start-record").addEventListener("click", function() {
        progressBar.value = 0;
        recorder.start();
    });

    document.getElementById("stop-record").addEventListener("click", function() {
        recorder.stop();
    });

    document.getElementById("upload-record").addEventListener("click", function() {
        sendAudioVideoBlobs();
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

    function sendAudioVideoBlobs(){

        console.log("Sending blobs!");

        var recordForm = new FormData();

        recordForm.append("video", videoBlob);
        recordForm.append("audio", audioBlob);

        var oReq = new XMLHttpRequest();

        formSubmitURL = "http://localhost:8088/openmrs/module/patientnarratives/webRtcMedia.form";
        console.log(formSubmitURL);

        oReq.open("POST", formSubmitURL);

        oReq.onload = function(e) {};

        // Listen to the upload progress.
        oReq.upload.onprogress = function(e) {
            if (e.lengthComputable) {
                progressBar.value = (e.loaded / e.total) * 100;
                progressBar.textContent = progressBar.value; // Fallback for unsupported browsers.
            }
        };

        oReq.send(recordForm);

    }

})();
