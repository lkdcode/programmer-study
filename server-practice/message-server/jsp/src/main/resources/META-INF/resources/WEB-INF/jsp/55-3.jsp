<%@ page contentType="text/html; charset=UTF-8" %>
<%
    request.setCharacterEncoding("UTF-8");

    String name = request.getParameter("name");
    String phone = request.getParameter("phone");
    String gender = request.getParameter("gender");
    String[] sports = request.getParameterValues("sports");
    String season = request.getParameter("season");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원 정보 입력</title>
</head>
<body>
    <%
        if (name == null || name.isEmpty()) {
    %>
    <form action="5-3.jsp" method="post">
        이름: <input type="text" name="name"><br><br>
        전화번호: <input type="text" name="phone"><br><br>
        성별:
        <input type="radio" name="gender" value="남자">남자
        <input type="radio" name="gender" value="여자">여자<br><br>

        좋아하는 운동:
        <input type="checkbox" name="sports" value="야구">야구
        <input type="checkbox" name="sports" value="축구">축구
        <input type="checkbox" name="sports" value="농구">농구
        <input type="checkbox" name="sports" value="탁구">탁구<br><br>

        가장 좋아하는 계절:
        <select name="season">
            <option value="봄">봄</option>
            <option value="여름">여름</option>
            <option value="가을">가을</option>
            <option value="겨울">겨울</option>
        </select><br><br>

        <input type="submit" value="확인">
        <input type="reset" value="취소">
    </form>
    <%
        } else {
    %>
    <h2>입력하신 정보</h2>

    <p><strong>이름:</strong> <%= name %></p>
    <p><strong>전화번호:</strong> <%= phone %></p>
    <p><strong>성별:</strong> <%= gender %></p>

    <p><strong>좋아하는 운동:</strong>
    <%
        if (sports != null) {
            for (int i = 0; i < sports.length; i++) {
                out.print(sports[i]);
                if (i < sports.length - 1) {
                    out.print(", ");
                }
            }
        } else {
            out.print("선택 안 함");
        }
    %>
    </p>

    <p><strong>가장 좋아하는 계절:</strong> <%= season %></p>

    <br>
    <a href="5-3.jsp">다시 입력하기</a>
    <%
        }
    %>
</body>
</html>