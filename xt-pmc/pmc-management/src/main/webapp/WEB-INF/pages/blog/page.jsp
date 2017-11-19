<%@ page isELIgnored="false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>${siteInfo.titlePrefix}写博客</title>
    <jsp:include page="${ctx}/WEB-INF/pages/core/header.jsp"/>
    <style>
        body {
            background: #fff;
        }

        #previewPanel *{
            background: #f6f6f6;
            margin: 0;
        }

        blockquote {
            border-left: #e8e8e8 solid 10px;
            background: #f0f0f0;
            padding-left: 20px;
        }

        ul li {
            line-height: 20px;
        }

        code {
            color: #D34B62;
            background: #42cdef;
        }
    </style>
</head>
<body>
<div style="width:50%; height: 500px; float: left; overflow: hidden;">
    <textarea class="tabIndent" id="writePanel" style="width:100%; height: 100%; border: none; outline:none; resize:none;"></textarea>
</div>
<div style="width:50%; height: 500px; float: left; overflow: auto;">
    <%--background: #272822; color:#42cdef;--%>
    <div id="previewPanel" style="width:100%; height: 100%; background: #f6f6f6;">
    </div>
</div>
<hr style="clear: both; background: #000; height: 5px;"/>
<div style="width:100%; float: left; overflow: auto;">
    <form action="${ctx}/blog/submit" method="post">
        <input type="hidden" name="html" id="htmlText"/>
        <input type="hidden" name="original" id="originalText"/>
        <input type="submit"/>
    </form>
</div>
<div style="width:100%; float: left; overflow: auto;">
    ${html}
</div>
<%--<div style="width:100%; float: left; overflow: auto;">
<pre class="line-numbers"><code class="language-java">public class Test{
    public void main(){
        System.out.println("123");
    }

    trait ezcReflectionReturnInfo {
        function getReturnType() { /*1*/ }
        function getReturnDescription() { /*2*/ }
    }
    function gen_one_to_three() {
        for ($i = 1; $i <= 3; $i++) {
            // Note that $i is preserved between yields.
            yield $i;
        }
}</code></pre>
</div>--%>

<script>
    tabIndent.renderAll();
    $(function () {
        /*$("#writePanel").on("keydown", function (e) {
            console.log(e.keyCode)
            if (e.shiftKey && e.keyCode == 9) {
                e.preventDefault();
                var indent = '    ';
                var start = this.selectionStart;
                var end = this.selectionEnd;
                var selected = window.getSelection().toString();
                //selected = indent + selected.replace(/\n/g, '\n' + indent);
                this.value = this.value.substring(0, start).replace('\n'+indent,'\n')
                    + this.value.substring(end);
                this.setSelectionRange(start + indent.length, start
                    + selected.length);
            }else if (e.keyCode == 9) {
                e.preventDefault();
                var indent = '    ';
                var start = this.selectionStart;
                var end = this.selectionEnd;
                var selected = window.getSelection().toString();
                selected = indent + selected.replace(/\n/g, '\n' + indent);
                this.value = this.value.substring(0, start) + selected
                    + this.value.substring(end);
                this.setSelectionRange(start + indent.length, start
                    + selected.length);
            }
        });*/
        $("#writePanel").on("keyup", function () {
            /*

            #<center>标题</center>

            ```
            public void main(){
                System.out.println("123");
            }
            ```

            - **虚拟机优化**

                虚拟机优化主要了解jvm内部的内存模型，和多线程架构。

                >这里只是简要的介绍一下布局，不做详细说明

                1. 电风扇

                 2. 似懂非懂

            - **线程池架构**

                1.啊啊

                  [简书](http://www.jianshu.com)

                2.胜多负少

                ![](http://upload-images.jianshu.io/upload_images/3067941-5f667c2cf05780bd.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

                3.是的范德萨

            */
            var converter = new showdown.Converter();
//            var markdownHtml = converter.makeHtml($(this).val().replace("<script>","```\r\n<script>").replace("<\/script>","<\/script>\r\n```"));
//            var panelHtml = $(this).val().replace(/\n/g,"");
            var writePanelHtml = $(this).val();
            var reg1 = /[`]{3}\n*[<script>]+/g;
            var reg2 = /[<\/script>]+\n*[`]{3}/g;
            if(reg1.test(writePanelHtml) && reg2.test(writePanelHtml)){
                console.log(" match")
            }else{
                writePanelHtml = writePanelHtml.replace("<script>","").replace("<\/script>","");
            }
            writePanelHtml = writePanelHtml.replace(/\n/g,"\n\n");
            var markdownHtml = converter.makeHtml(writePanelHtml);
            $("#previewPanel").html(markdownHtml);

            var newPanelNode = $($("#previewPanel")[0].outerHTML);
            if(newPanelNode.find("code").length!=0){
                newPanelNode = $($("#previewPanel")[0].outerHTML).find("code").addClass("language-java").parents("#previewPanel");
            }
            if(newPanelNode.find("pre").length!=0){
                newPanelNode = $($("#previewPanel")[0].outerHTML).find("pre").addClass("line-numbers").parents("#previewPanel");
            }
            var decodrText = encodeURIComponent(newPanelNode[0].outerHTML);
            $("#htmlText").val(decodrText);
            $("#originalText").val(encodeURIComponent(writePanelHtml));
        });
    });
</script>
</body>
</html>
