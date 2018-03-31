<%--
  Created by IntelliJ IDEA.
  User: 胡毅
  Date: 2017/12/27
  Time: 20:37
  To change this template use File | Settings | File Templates.
--%>
<<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>上传文件</title>
</head>
<body>
<form action="/uploade" method="post" enctype="multipart/form-data">
    选择文件：<input type="file" name="file"/><br/>
    文件描述：<textarea name="desc" rows="20" cols="10"></textarea>
    <input type="submit" value="上传">
</form>
</body>
</html>
