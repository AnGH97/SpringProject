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
    <style>
        * {
                box-sizing: border-box;
                font-size: 14px;
            }

            .image-list {
                display: flex;
                flex-wrap: wrap;
            }

            .image-item {
                width: 25%;
                padding: 15px;
                cursor: pointer;
            }

            .image-item > div {
                overflow: hidden;
            }

            .image-item img {
                width: 100%;
                height: 240px;
                object-fit: cover;
                transition: all 0.3s ease-in-out;
            }

            .image-item:hover {
                background-color: #eee;
            }

            .image-item:hover img {
                transform: scale(1.1);
            }
    </style>
    
</head>
<body>
    <c:choose>
        <%-- 조회 결과가 없는 경우 --%>
        <c:when test="${list == null || fn:length(list) == 0}">
            <h2>업로드 결과가 없습니다.</h2>
        </c:when>
        <%-- 조회 결과가 있는 경우 --%>
        <c:otherwise>
            <div class="image-list">
                <%--조회 결과에 따른 반복 처리 --%>
                <c:forEach var="item" items="${list}" varStatus="status">
                    <div class="image-item">
                        <div>
                            <a href="${item.fileUrl}" data-fslightbox="gallery">
                                <img src="${item.thumbnailUrl}">
                            </a>
                            
                        </div>
                        <ul>
                            <li>fieldName: ${item.fieldName}</li>
                            <li>originName: ${item.originName}</li>
                            <li>contentType: ${item.contentType}</li>
                            <li>fileSize: ${item.fileSize}</li>
                            <li>filePath: ${item.filePath}</li>
                        </ul>
                    </div>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/fslightbox/3.0.9/index.min.js"></script>
</body>
</html>