<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    // 한글 처리
    request.setCharacterEncoding("UTF-8");

    String name   = request.getParameter("name");
    String phone  = request.getParameter("phone");
    String gender = request.getParameter("gender");
    String season = request.getParameter("season");

    // checkbox 는 여러 개가 넘어올 수 있으므로 getParameterValues 사용
    String[] sports = request.getParameterValues("sports");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원 정보 결과</title>
</head>
<body>
    <h2>입력하신 정보</h2>
    이름 : <%= name %><br>
    전화번호 : <%= phone %><br>
    성별 : <%= gender %><br>

    좋아하는 운동 :
    <%
        if (sports != null) {
            for (int i = 0; i < sports.length; i++) {
    %>
                <%= sports[i] %>
    <%
                if (i < sports.length - 1) {
    %>,
    <%
                }
            }
        } else {
    %>
            선택하지 않음
    <%
        }
    %>
    <br>

    가장 좋아하는 계절 : <%= season %><br><br>

    <a href="5-2.jsp">다시 입력하기</a>
</body>
</html>
