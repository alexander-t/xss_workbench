<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Oops!</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <h1>Oops!</h1>

    <p>Something went wrong!</p>

    <p><c:out value="${errorMessage}"/></p>
</div>
</body>
</html>