var Blog = function () {
    function convert(orginal){
        var lineStrArr = orginal.split("\n");
        var brOrginal = "";
        var codeSeparator = 1;
        if (lineStrArr.length) {
            for (var i = 0; i < lineStrArr.length; i++) {
                if (lineStrArr[i].indexOf("```") == -1 && lineStrArr[i].match(/`.+`/)) {
                    //  `hello world`
                    codeSeparator = 1;
                } else {
                    if (codeSeparator == 1 && lineStrArr[i].match(/^\s*`+.*/)) {
                        codeSeparator = 2;
                    } else if (codeSeparator == 2 && lineStrArr[i].match(/^\s*`+.*/)) {
                        codeSeparator = 1;
                    }
                }
                if (codeSeparator == 2) {
                    brOrginal += lineStrArr[i] + "\n";
                } else {
                    if (!lineStrArr[i].match(/^\s*>.*/) && !lineStrArr[i].match(/^\s*`+.*/)) {
                        brOrginal += lineStrArr[i] + "<br/>\n";
                    } else {
                        brOrginal += lineStrArr[i] + "\n\n";
                    }
                }
            }
        }
        return brOrginal;
    }

    function renderPreview(){
        var converter = new showdown.Converter();
        $(".previewPanel").each(function(index,item){
            var orginal = $(".writePanel").eq(index).val();
            var brOrginal = convert(orginal);
            var preview = converter.makeHtml(brOrginal);
            $(item)[0].innerHTML = preview;
            var decodePreviewText = encodeURIComponent(preview);
            var decodeOriginalText = encodeURIComponent(orginal);
            $("#htmlText").val(decodePreviewText);
            $("#originalText").val(decodeOriginalText);
        });
    }

    function init() {
        tabIndent.renderAll();
        $(function () {
            $(".writePanel").on("scroll",function(){
                var writeScrollTop = $(this).scrollTop();
                var writeHeight = $(this).height();
                var percent = writeScrollTop/writeHeight;
                var previewHeight = $(".previewPanel").height();
                var writeScrollTop = Math.round(previewHeight*percent);
                $(".previewPanel").scrollTop(writeScrollTop);
            });
            $(".writePanel").on("keyup", function () {
                var orginal = $(".writePanel").val();
                renderPreview();
            });
            renderPreview();
        });
    }

    return {
        init: init,
        conver:convert
    };
}();