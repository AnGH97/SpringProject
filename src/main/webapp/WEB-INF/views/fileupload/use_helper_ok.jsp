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
    <title>Hello JSP</title>
</head>
<body>
    <h1>WebHelper를 활용한 파일업로드 결과</h1>

    <h2>원본 이미지</h2>
    <a href="${item.fileUrl}" data-fslightbox="gallery">
        <img src="${item.thumbnailUrl}" width="240" />
    </a>
    
    <h2>DB에 저장할 정보</h2>
    <ul>
        <li>fieldName: ${item.fieldName}</li>
        <li>originName: ${item.originName}</li>
        <li>contentType: ${item.contentType}</li>
        <li>fileSize: ${item.fileSize}</li>
        <li>filePath: ${item.filePath}</li>
    </ul>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/fslightbox/3.0.9/index.min.js"></script>
</body>
</html>