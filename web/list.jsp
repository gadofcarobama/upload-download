<%--
  Created by IntelliJ IDEA.
  User: 胡毅
  Date: 2017/12/27
  Time: 18:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>所有的文件</h1>
<c:forEach var="list" items="${list }">
    <h4>
        <a href="/download?id=${list.id}">${list.realname }</a>
        <br/>
            ${list.desc }
    </h4>
</c:forEach>
</body>
</body>
</html>
