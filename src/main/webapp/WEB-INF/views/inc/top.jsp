<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/inc/header.jsp"></c:import>
<header class="top">
    <h1>Spring MySchool</h1>
    <ul>
        <li><a href=${contextPath}/department>학과관리</a></li>
        <li><a href=${contextPath}/professor>교수관리</a></li>
        <li><a href=${contextPath}/student>학생관리</a></li>
    </ul>
</header>