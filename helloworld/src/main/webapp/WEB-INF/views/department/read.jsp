<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/inc/top.jsp"></c:import>
<body>
    <h1>학과정보</h1>
    <ul>
        <li><p>학과번호: ${output.deptno}</p></li>
        <li><p>학과이름: ${output.dname}</p></li>
        <li><p>학과위치: ${output.loc}</p></li>
    </ul>

    <hr />
    <a href="${contextPath}/department/list.do">[목록보기]</a>
    <a href="${contextPath}/department/add.do">[학과추가]</a>
    <a href="${contextPath}/department/edit.do?deptno=${output.deptno}">[학과수정]</a>
    <a href="${contextPath}/department/delete.do?deptno=${output.deptno}">[학과삭제]</a>
<c:import url="/WEB-INF/views/inc/bottom.jsp"></c:import>