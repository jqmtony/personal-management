(function(audio){
    const AUDIO_TEXT_PLACEHODLER = "{TEXT}";
    const SPEED_TEXT_PLACEHODLER = "{SPEED}";
    const BAIDU_AUDIO_API = 'http://tts.baidu.com/text2audio?lan=zh&ie=UTF-8&spd='+SPEED_TEXT_PLACEHODLER+'&text='+AUDIO_TEXT_PLACEHODLER;
    audio.playText = function(text,speed){
        if(!speed)speed = 6;
        var url = BAIDU_AUDIO_API
            .replace(AUDIO_TEXT_PLACEHODLER,text)
            .replace(SPEED_TEXT_PLACEHODLER,speed);

        var rootNode = document.createElement("div");
        rootNode.innerHTML = '<audio id="textAudio" autoplay="autoplay">\n' +
            '        <source id="tts_source_id" src="'+url+'" type="audio/mpeg">\n' +
            '        <embed id="tts_embed_id" height="0" width="0" src="">\n' +
            '    </audio>';
        document.body.appendChild(rootNode);
    }
    audio.showMessage = function(selector,text,cb){
        $(selector).stop(true, false).animate({
            opacity: 0
        }, {
            duration: 520,
            complete: function() {
                $(selector).html(text);
                $(selector).stop(true, false).animate({
                    opacity: 1
                }, {
                    duration: 520,
                    complete: function() {
                        if(cb){
                            cb(text);
                        }
                    }
                })
            }
        })
    }
    audio.submit = function(){
        var text = $("#robotForm").children("input").val();
        $.ajax({
            url:ROOT_PATH+"/robot/chat",
            data:{text:text},
            method:"post",
            success:function(result){
                if(!result) result = "我们聊点别的吧！";
                audio.showMessage(".text-echo",result,function(text){
                    audio.playText(text);
                });
            }
        });
    }
})(window.Audio = {});