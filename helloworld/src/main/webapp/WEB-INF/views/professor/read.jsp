<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/inc/top.jsp" />

<h1>교수정보</h1>
<ul>
    <li><p>교수번호: ${output.profno}</p></li>
    <li><p>교수이름: ${output.name}</p></li>
    <li><p>아이디: ${output.userid}</p></li>
    <li><p>직급: ${output.position}</p></li>
    <li><p>급여: ${output.sal}</p></li>
    <li><p>입사일: ${output.hiredate}</p></li>
    <li><p>보직수당: ${output.comm}</p></li>
    <li><p>학과이름: ${output.dname}</p></li>
    <li><p>학과위치: ${output.loc}</p></li>
</ul>



<hr />
<a href="${contextPath}/professor/list.do">[목록보기]</a>
<a href="${contextPath}/professor/add.do">[교수추가]</a>
<a href="${contextPath}/professor/edit.do?profno=${output.profno}">[교수수정]</a>
<a href="${contextPath}/professor/delete.do?profno=${output.profno}">[교수삭제]</a>

<c:import url="/WEB-INF/views/inc/bottom.jsp" />
