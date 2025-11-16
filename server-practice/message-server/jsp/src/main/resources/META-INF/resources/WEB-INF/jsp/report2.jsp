<%@ page contentType="text/html; charset=UTF-8" %>
<%!
    int count = 0;
    public String greet(String name) {
        return "Hello, " + name + "!";
    }
%>
<!DOCTYPE html>
<html>
<body>
    <h2>접속 횟수: <%= ++count %></h2>
    <h2><%= greet("이기덕 사용자")%></h2>
</body>
</html>