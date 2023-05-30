<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Spring Example</title>
</head>
<body>
    <h1>쿠키 예제</h1>
    <%-- JSTL을 통해 쿠키에 직접 접근하기 --%>
    <c:choose>
        <c:when test="${cookie.my_cookie == null}">
            <h2>저장된 쿠키 없음</h2>
        </c:when>
        <c:otherwise>
            <h2>저장된 쿠키=${cookie.my_cookie.value}</h2>
        </c:otherwise>
    </c:choose>

    <%--컨트롤러에서 Model객체를 통해 넘어온 값 출력하기 --%>
    <c:choose>
        <c:when test="${my_cookie==''}">
            <h2>컨트롤러에서 식별한 쿠키 없음</h2>
        </c:when>
        <c:otherwise>
            <h2>컨트롤러에서 식별한 쿠키=${my_cookie}</h2>
        </c:otherwise>
    </c:choose>

    <form method="post" action="${contextPath}/cookie/save.do">
        <label for="user_input">저장할 내용 입력</label>
        <input type="text" name="user_input" id="user_input"/>
        <button type="submit">쿠키 저장</button>
    </form>
    
</body>
</html>