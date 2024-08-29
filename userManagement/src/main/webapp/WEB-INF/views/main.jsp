<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>User 관리 프로젝트</title>
</head>
<body>

  <%-- 로그인이 안되있는경우선이여</h1>
    <ul>
      <li>userNo     : ${loginUser.userNo}</li>
      <li>userId     : ${loginUser.user
    == session scope에 "loginUser"가 없는경우 --%>
  <c:if test="${empty sessionScope.loginUser}">
    <h1>Login</h1>
    <form action="/login" method="post">
      <div>
        ID : <input type="text" name="userId">
      </div>
      <div>
        PW : <input type="password" name="userPw">
      <div>
        <button>로그인</button>
        <a href="/signUp">사용자 등록</a>
      </div>
      </div>
    </form>
  </c:if>

  <%-- 로그인 상태인경우 --%>
  <c:if test="${not empty sessionScope.loginUser}" >
    
    <h1>${loginUser.userName} 환영하오 낯선이여</li>
      <li>userName   : ${loginUser.userName}</li>
      <li>enrollDate : ${loginUser.enrollDate}</li>
    </ul>

    <button id="logout">Logout</button>

    <hr>

    <h3>메뉴</h3>
    <ul>
     <%-- 
       - 클릭 시 /selectAll GET방식 요청 
       - 모든 사용자 회원번호, id, pw, name, enrollDate 조회
       - 조회 결과를 request scope에 세팅하여
         /WEB-INF/views/selectAll.jsp로 forward
      
       - <table> 태그를 이용해서 모든 정보 출력
        컬럼명 : 회원번호 | 아이디 | 비밀번호 | 이름 | 등록일
        hint. JSTL 중 <c:forEach> 사용
      --%>
      <li><a href="/selectAll">사용자 목록조회</a></li>
    </ul>

  </c:if>


<%-- session에 message가 존재하는 경우 --%>
<c:if test="${!empty sessionScope.message}" >
  <script>
    alert("${sessionScope.message}");
  </script>

  <%-- session에 존재하는 message 제거 --%>
  <c:remove var="message" scope="session" />
</c:if>

  <script src="/resources/js/main.js"></script>
</body>
</html>