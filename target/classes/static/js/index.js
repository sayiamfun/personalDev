fnResize();
window.onresize = function () {
    fnResize();
}
function fnResize() {
    var deviceWidth = document.documentElement.clientWidth || window.innerWidth;
    if (deviceWidth >= 750) {
        deviceWidth = 750;
    }
    if (deviceWidth <= 320) {
        deviceWidth = 320;
    }
    document.documentElement.style.fontSize = (deviceWidth / 7.5) + 'px';
}
window.onload = function () {
    var _height = window.screen.availHeight ;//获取当前窗口的高度
    var _width = $("body").width();//获取当前窗口的宽度
    var height = document.querySelector('.personHFive')
    height.style.height = _height + 'px'
}