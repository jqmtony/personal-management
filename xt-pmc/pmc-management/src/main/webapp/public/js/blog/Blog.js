var Blog = function () {
    function init() {
        tabIndent.renderAll();
        $(function () {
            $("#writePanel").on("scroll",function(){
                var writeScrollTop = $(this).scrollTop();
                var writeHeight = $(this).height();
                var percent = writeScrollTop/writeHeight;
                var previewHeight = $("#previewPanel").height();
                var writeScrollTop = Math.round(previewHeight*percent);
                $("#previewPanel").scrollTop(writeScrollTop);
            });
            $("#writePanel").on("keyup", function () {
                var converter = new showdown.Converter();

                var orginal = $("#writePanel").val();

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
                //干掉“```”之前的<br/>
//            brOrginal = brOrginal.replace(/<br\/>\s*\n`+/g,"$1".replace("<br/>",""));
                var preview = converter.makeHtml(brOrginal);
                $("#previewPanel")[0].innerHTML = preview;

                var previewPanel = $("#previewPanel");
//            previewPanel.find("pre").addClass("language-java");
//            previewPanel.find("code").addClass("language-java");

                preview = previewPanel[0].outerHTML;
                var decodePreviewText = encodeURIComponent(preview);
                var decodeOriginalText = encodeURIComponent(orginal);
                $("#htmlText").val(decodePreviewText);
                $("#originalText").val(decodeOriginalText);
            });
        });
    }

    return {
        init: init
    };
}();