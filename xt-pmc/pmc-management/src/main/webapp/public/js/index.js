function AutoScroll(selector) {
    var firstUl = $(selector).find("ul:first");
    firstUl.animate(
        {
            marginTop: "-30px"
        },
        500,
        function () {
            //ul设置会原位，并把推上去的li放到最后
            $(this).css({marginTop: "0px"}).find("li:first").appendTo(this);
        }
    );
}