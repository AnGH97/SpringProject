<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/inc/top.jsp"></c:import>
<body>
    <h1>학과 관리</h1>

    <hr />
    <a href="${contextPath}/department/add.do">[학과추가]</a>

    <!-- 검색 폼-->
    <form method="get" action="${contextPath}/department/list.do">
        <div>
            <label for="search">검색어 선택: </label>
            <select name="search" id="search">
                <option value="">-----선택하세요-----</option>
                <option value="dname"  <c:if test="${search == 'dname'}">selected</c:if>>학과명</option>
                <option value="loc"  <c:if test="${search == 'loc'}">selected</c:if>>위치</option>
            </select>
            <label for="keyword"></label>
            <input type="search" name="keyword" id="keyword" placeholder="학과명 or 위치 검색" value="${keyword}" />
            <button type="submit">검색</button>
        </div>

    </form>

    <hr />

    <!-- 조회 결과 목록 -->
    <table border = "1">
        <thead>
            <tr>
                <th width="100" align="center">학과 번호</th>
                <th width="300" align="center">학과 이름</th>
                <th width="200" align="center">학과 위치</th>
            </tr>
        </thead>
        <tbody>
            <c:choose>
                <%-- 조회 결과가 없는 경우 --%>
                <c:when test="${output == null || fn:length(output) == 0}">
                    <tr>
                        <td colspan="3" align="center">조회결과가 없습니다.</td>
                    </tr>
                </c:when>
                <%-- 조회결과가 있는 경우 --%>
                <c:otherwise>
                    <%-- 조회 결과에 따른 반복 처리--%>
                    <c:forEach var="item" items="${output}" varStatus="status">

                        <%-- 출력을 위해 준비한 학과이름과 위치--%>
                        <c:set var="dname" value="${item.dname}" />
                        <c:set var="loc" value="${item.loc}" />
                        <%-- 검색어가 있다면? --%>
                        <c:if test="${keyword != ''}">
                            <%-- 검색어에 <mark> 태그를 적용한 문자열 변수를 준비 --%>
                                <c:set var="mark" value="<mark>${keyword}</mark>"></c:set>
                                
                                <%-- 출력을 위해 준비한 학과 이름과 위치에서 검색어와 일치하는 단어를 형광팬 효과로 변경 --%>
                                <c:set var="dname" value="${fn:replace(dname, keyword, mark)}"></c:set>
                                <c:set var="loc" value="${fn:replace(loc, keyword, mark)}"></c:set>
                        </c:if>
                        <%-- 상세페이지로 이동하기 위한 URL --%>
                        <c:url value="/department/read.do" var="viewUrl">
                            <c:param name="deptno" value="${item.deptno}"></c:param>
                        </c:url>

                        <tr>
                            <td align="center">${item.deptno}</td>
                            <td align="center"><a href="${viewUrl}">${dname}</a></td>
                            <td align="center">${loc}</td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>
    <!-- 페이지 번호 구현-->
    <%-- 이전 그룹에 대한 링크 --%>
    <c:choose>
        <%-- 이전 그룹으로 이동 가능하다면?--%>
        <c:when test="${pagenation.prevPage > 0}">
            <%-- 이동할 URL 생성 --%>
            <c:url value="/department/list.do" var="prevPageUrl">
                <c:param name="page" value="${pagenation.prevPage}"></c:param>
                <c:param name="keyword" value="${keyword}"></c:param>
            </c:url>
            <a href="${prevPageUrl}">[이전]</a>
        </c:when>
        <c:otherwise>
            [이전]
        </c:otherwise>
    </c:choose>

    <%--페이지 번호(시작 페이지부터 끝 페이지까지 반복) --%>
    <c:forEach var="i" begin="${pagenation.startPage}" end="${pagenation.endPage}" varStatus="status">
        <%--이동할 URL 생성--%>
        <c:url value="/department/list.do" var="pageUrl">
            <c:param name="page" value="${i}"></c:param>
            <c:param name="keyword" value="${keyword}"></c:param>
        </c:url>

        <%-- 페이지 번호 출력 --%>
        <c:choose>
            <%--현재 머물고 있는 페이지 번호를 출력할 경우 링크 적용 안함 --%>
            <c:when test="${pagenation.nowPage == i}">
                <strong>[${i}]</strong>
            </c:when>
            <%-- 나머지 페이지의 경우 링크 적용함 --%>
            <c:otherwise>
                <a href="${pageUrl}">[${i}]</a>
            </c:otherwise>
        </c:choose>
    </c:forEach>

    <%--다음 그룹에 대한 링크 --%>
    <c:choose>
        <%--다음 그룹으로 이동이 가능하다면?--%>
        <c:when test="${pagenation.nextPage > 0}">
            <%--이동할 URL 생성 --%>
            <c:url value="/department/list.do" var="nextPageUrl">
                <c:param name="page" value="${pagenation.nextPage}"></c:param>
                <c:param name="keyword" value="${keyword}"></c:param>
            </c:url>
            <a href="${nextPageUrl}">[다음]</a>
        </c:when>
        <c:otherwise>
            [다음]
        </c:otherwise>
    </c:choose>
    <br/>
    <hr/>
<c:import url="/WEB-INF/views/inc/bottom.jsp"></c:import>