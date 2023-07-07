<% if( request.getAttribute("liste") !=null){
        out.println(request.getAttribute("liste")); 
          
    }
    String a = getServletConfig().getInitParameter("isConnected");
%>
<%= a  %>



<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <h1>Accueil</h1>
    <a href="TestFramework/emp-all"> lien11</a>
    <form action="http://localhost:8082/test_framework7/up.do">
        <p>Id: <input type="number" name="A" ></p>
        <p>Nom: <input type="number" name="B" ></p>
        <p> <input type="submit" value="ok" ></p>
    </form>
    <a href="emp-all.do?hihi=1"> lien11</a>
</body>
</html>