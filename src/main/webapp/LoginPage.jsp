<%-- 
    Document   : LoginPage
    Created on : Apr 19, 2016, 8:21:40 PM
    Author     : Matthew_2
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
       <link rel="stylesheet" href='<%= this.getServletContext().getContextPath() + "/Content/BookWebApp.css"%>'>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    </head>
    <body>
        <h1>Welcome To Matthew Awesome Book Store</h1>

        <header>
            <input type="button" class="btn btn-primary" data-toggle="modal" data-target=".bs-login-modal-sm" value="Login" id="login" name="login" />
        </header>


        <div class="modal fade bs-login-modal-sm" tabindex="-1" role="dialog" aria-labelledby="myModal">
            <div class="modal-dialog modal-sm">

                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h3 class="modal-title">Login</h3>
                    </div>
                    

                        <form id="signInForm" role="form" method='POST' action="<c:url value='j_spring_security_check' />">
                            <sec:csrfInput />

                            <div class="modal-body">
                                <h3 style="font-weight: 200;">Sign in </h3>
                                <div class="form-group">
                                    <input tabindex="1" class="form-control" id="j_username" name="j_username" placeholder="Email address" type="text" autofocus />
                                    <input tabindex="2" class="form-control" id="j_password" name="j_password" type="password" placeholder="password" />
                                </div>
                                
                            </div>
                            
                            <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                    <input class="btn btn-warning" name="submit" type="submit" value="Sign in" />
                                </div>
                        </form>




                </div>

            </div>
        </div>




        <script src="https://code.jquery.com/jquery-latest.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
        <script src="Content/BookWebApp.js" type="text/javascript"></script>
    </body>
</html>
