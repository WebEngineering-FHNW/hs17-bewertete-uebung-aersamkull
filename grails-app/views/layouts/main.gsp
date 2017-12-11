<!doctype html>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>
        <g:layoutTitle default="Simple Task System"/>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>

    <asset:stylesheet src="application.css"/>

    <g:layoutHead/>
</head>
<body>

    <nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top">
        <a class="navbar-brand" href="/#">
            <i class="fa grails-icon">
                 <asset:image src="grails-cupsonly-logo-white.svg"/>
            </i> Simple Task
        </a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                        <li class="nav-item active">
                            <g:link controller="task" class="nav-link" >Home</g:link>
                       </li>
                       <g:if test="${request.cookies.find{ 'Username' == it.name }}">
                        <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    <span class="oi oi-person" aria-hidden="true"></span>   
                                    ${request.cookies.find{ 'Username' == it.name }?.value}
                                </a>
                                <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                                <g:link controller="user" action="logout" class="dropdown-item" href="#">
                                    <span class="oi oi-account-logout" aria-hidden="true"></span>
                                    Logout</g:link>
                                </div>
                            </li>
                        </g:if>
                </ul>
        </div>
    </nav>
    <div class="content">
        <g:layoutBody/>
    </div>
    <asset:javascript src="application.js"/>
    <g:pageProperty name="page.js"></g:pageProperty>
</body>
</html>
