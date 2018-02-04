(function(audio){
    const AUDIO_TEXT_PLACEHODLER = "{TEXT}";
    const SPEED_TEXT_PLACEHODLER = "{SPEED}";
    const BAIDU_AUDIO_API = 'http://tts.baidu.com/text2audio?lan=zh&ie=UTF-8&spd='+SPEED_TEXT_PLACEHODLER+'&text='+AUDIO_TEXT_PLACEHODLER;
    audio.playText = function(text,speed){
        $("#textAudio").remove();
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
    audio.submit = function(text){
        var text = text || $("#robotForm").children("input").val();
        $.ajax({
            url:ROOT_PATH+"/robot/chat",
            data:{text:text},
            headers:{"Accept":"application/json; charset=utf-8"},
            method:"post",
            error: function(xhr, statu, ex) {
                console.log(ex);
                var errorText = "哎呀，服务器故障了，请稍后再试试吧";
                audio.showMessage(".text-echo",errorText,function(ask){
                    audio.playText(errorText);
                });
            },
            success:function(result){
                var ask = "";
                if(result && result.text){
                    ask = result.text;
                } else {
                    ask = "我们聊一点别的吧";
                }
                audio.showMessage(".text-echo",ask,function(ask){
                    audio.playText(ask);
                    if(result.list){
                        $(".text-echo").append("<br/><br/><br/><br/>")
                        $.each(result.list,function(i,n){
                            if(n.article){
                                $(".text-echo").append("<a target='_blank' href='"+n.detailurl+"'>"+n.article+"</a><br/>")
                            }
                        });
                    }
                    if(result.url){
                        $(".text-echo").append("<br/><a target='_blank' href='"+result.url+"'>今日头条</a><br/>")
                    }
                });

                if(result.question){
                    audio.addMemcon(result.question,true);
                    audio.addMemcon(result.text);
                }
                $(".audioTextinput").val('');
            }
        });
    }

    var getNowFormatDate =  function() {
        var a = new Date;
        var e = "-";
        var t = ":";
        var i = a.getMonth() + 1;
        var n = a.getDate();
        if (i >= 1 && i <= 9) {
            i = "0" + i
        }
        if (n >= 0 && n <= 9) {
            n = "0" + n
        }
        var r = a.getHours() + t + a.getMinutes() + t + a.getSeconds();
        return r
    }

    audio.addMemcon = function(msg,self) {
        if (self===true) {
            var a = "我";
            $("#messagerecorder ul").append('<li class="mrb">' + getNowFormatDate() + " " + a + "：" + msg + "</li>")
        } else {
            var a = "robot";
            $("#messagerecorder ul").append('<li class="mra">' + getNowFormatDate() + " " + a + "：" + msg + "</li>")
        }
    }
})(window.Audio = {});