var Blog = function () {
    function convert(orginal) {
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

    function renderPreview(target) {
        var converter = new showdown.Converter();

        var wPanel = $(target);
        var pPanel = wPanel.parents(".blog-container").find(".previewPanel");

        var orginal = $(target).val();
        var brOrginal = convert(orginal);
        var preview = converter.makeHtml(brOrginal);
        pPanel[0].innerHTML = preview;
        var decodePreviewText = encodeURIComponent(preview);
        var decodeOriginalText = encodeURIComponent(orginal);
        $("#htmlText").val(decodePreviewText);
        $("#originalText").val(decodeOriginalText);

        /*$(".previewPanel").each(function(index,item){
            var orginal = $(".writePanel").eq(index).val();
            var brOrginal = convert(orginal);
            var preview = converter.makeHtml(brOrginal);
            $(item)[0].innerHTML = preview;
            var decodePreviewText = encodeURIComponent(preview);
            var decodeOriginalText = encodeURIComponent(orginal);
            $("#htmlText").val(decodePreviewText);
            $("#originalText").val(decodeOriginalText);
        });*/
    }

    function init() {
        tabIndent.renderAll();
        $(function () {
            $(".writePanel").each(function (index, item) {
                $(item).on("scroll", function () {
                    var writeScrollTop = 0;
                    if ($(this).scrollTop() > 0) {
                        writeScrollTop = $(this).height() + $(this).scrollTop();
                    }
                    var writeHeight = $(this).prop("scrollHeight");
                    var percent = writeScrollTop / writeHeight;
                    //总高度
                    var previewHeight = $(".previewPanel").eq(index).prop("scrollHeight");
                    //可视高度
                    var writeViewHeight = $(".previewPanel").eq(index).height();
                    var previewScrollTop = Math.round(previewHeight * percent);
                    console.log(writeScrollTop + "," + percent + "," + previewScrollTop)
                    $(".previewPanel").eq(index).scrollTop(Math.max(previewScrollTop - writeViewHeight, 0));
                });
            });
            $(".writePanel").on("keyup", function () {
                renderPreview(this);
            });
        });
    }

    function classifyInit(id,pid) {
        $.ajax({
            url: ROOT_PATH + "/blogType/getRootNodes",
            type: "post",
            success: function (data) {
                if (data && data.length) {
                    var map = {};
                    $.each(data, function (i, n) {
                        $("#firLvl").append("<option value='" + n.id + "'>" + n.name + "</option>");
                        map[n.id] = n.children;
                    });
                    $("#firLvl").change(function () {
                        $("#secLvl option:eq(0)").nextAll().remove();
                        $.each(map[$(this).val()], function (i, n) {
                            $("#secLvl").append("<option value='" + n.id + "'>" + n.name + "</option>");
                        })
                    });

                    if(pid){
                        $("#firLvl").val(pid);
                        if(id){
                            $("#secLvl option:eq(0)").nextAll().remove();
                            $.each(map[pid], function (i, n) {
                                $("#secLvl").append("<option value='" + n.id + "'>" + n.name + "</option>");
                            })
                            $("#secLvl").val(id);
                        }
                    }
                }
            }
        });
    }

    return {
        init: init,
        conver: convert,
        classifyInit: classifyInit
    };
}();