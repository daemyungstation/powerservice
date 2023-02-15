/**
 * 쿠키값 설정하기
 *
 * @param cKey
 * @param cValue
 */
function setCookie(cKey, cValue) {
    var date = new Date(); // 오늘 날짜

    // 만료시점 : 오늘날짜 + 30일 설정
    var validity = 30;
    date.setDate(date.getDate() + validity);

    // 쿠키 저장
    document.cookie = cKey + '=' + escape(cValue) + ';expires=' + date.toGMTString();
}

/**
 * 쿠키 삭제
 *
 * @param cKey
 */
function delCookie(cKey) {
    // 동일한 키(name)값으로
    // 1. 만료날짜 과거로 쿠키저장
    // 2. 만료날짜 설정 않는다.
    //    브라우저가 닫힐 때 제명이 된다
    var date = new Date(); // 오늘 날짜
    var validity = -1;

    date.setDate(date.getDate() + validity);
    document.cookie = cKey + "=;expires=" + date.toGMTString();
}

/**
 * 쿠키값 가져오기
 *
 * @param cKey
 * @returns 쿠키값
 */
function getCookie(cKey) {
    var allcookies = document.cookie;

    var cookies = allcookies.split("; ");

    for (var i = 0; i < cookies.length; i++) {
        var keyValues = cookies[i].split("=");

        if (keyValues[0] == cKey) {
            return unescape(keyValues[1]);
        }
    }
    return "";
}
