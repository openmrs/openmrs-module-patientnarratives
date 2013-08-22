(function() {

    var videoBlob;
    var audioBlob;

    var constraints = { video: true, audio: true },
        recorder = new RecordRTC({
            enable: constraints,
            videoElem: document.getElementById("client-video"),
            video_width: 320,
            video_height: 240,
            canvas_width: 320,
            canvas_height: 240
        });

    // get user media
    recorder.getMedia(recorder.setMedia, function() {
        console.log("get user media failed!");
    });

    recorder.onVideoReady(function(blob) {
        attachLink(blob, "video");
        videoBlob = blob;
//        upload(blob);
    });

    recorder.onAudioReady(function(blob) {
        attachLink(blob, "audio");
        audioBlob = blob;
//        upload(blob);
    });

    document.getElementById("start-record").addEventListener("click", function() {
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

    function upload(blobOrFile) {
        var xhr = new XMLHttpRequest();
        xhr.open('POST', '/server', true);
        xhr.onload = function(e) {};

        // Listen to the upload progress.
        var progressBar = document.querySelector('progress');
        xhr.upload.onprogress = function(e) {
            if (e.lengthComputable) {
                progressBar.value = (e.loaded / e.total) * 100;
                progressBar.textContent = progressBar.value; // Fallback for unsupported browsers.
            }
        };

        xhr.send(blobOrFile);
    }

    function sendAudioVideoBlobs(){

        console.log("Sending blobs!");

        var recordForm = new FormData();

        recordForm.append("video", videoBlob);
        recordForm.append("audio", audioBlob);

//        data.append("video", videoBlob, (new Date()).getTime() + ".webm");
//        data.append("audio", audioBlob, (new Date()).getTime() + ".wav");

        var oReq = new XMLHttpRequest();

        formSubmitURL = "http://localhost:8088/openmrs/module/patientnarratives/webRtcMedia.form";
        console.log(formSubmitURL);

        oReq.open("POST", formSubmitURL);
        oReq.send(recordForm);

    }

})();
