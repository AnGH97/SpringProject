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
    <title>home</title>
</head>
<body>
    <h1>Hello, home</h1>
    <h1>Java 메일 발송 연습</h1>
    <form method="post" action="${contextPath}/sendmail/send.do">
        <div>
            <label for="name">수신인:</label>
            <input type="text" name="name" id="name" />
        </div>
        <div>
            <label for="email">이메일:</label>
            <input type="email" name="email" id="email" />
        </div>
        <div>
            <label for="subject">메일제목:</label>
            <input type="text" name="subject" id="subject" />
        </div>
        <hr />
        <textarea name="content" id="content"></textarea>
        <hr />
        <input type="submit" value="메일보내기" />
    </form>
    <script src="https://cdn.ckeditor.com/ckeditor5/37.0.1/classic/ckeditor.js"></script>
    <script>
        ClassicEditor.create( document.querySelector( '#content' ) );
    </script>
    </body>
</html>