<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/inc/top.jsp"></c:import>
<body>
    <h1>학과 추가</h1>
    <form method="post" action="${contextPath}/department/add_ok.do">
        <div>
            <label for="dname">학과명: </label>
            <input type="text" name="dname" id="dname" />
        </div>
        <div>
            <label for="loc">위치: </label>
            <input type="text" name="loc" id="loc" />
        </div>
        <hr />
        <button type="submit">저장하기</button>
        <button type="reset">다시작성</button>
    </form>
<c:import url="/WEB-INF/views/inc/bottom.jsp"></c:import>