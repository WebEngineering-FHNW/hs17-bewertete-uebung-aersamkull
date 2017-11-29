<html>
<head>
  <meta name="layout" content="main">
  <title>Tasks</title>
</head>
<body>

Tasks in period:
<ul>
<g:each var="task" in="${tasks}">
  <li>
    <div>
        <h1>${task.name}</h1>
        <p>${task.description}</p>
        <p>Date: <input type="date" readonly value="${task.date}" ></p>
        <p>Responsible: ${task.responsible.name}</p>
    </div>
  </li>
</g:each>
</ul>

</body>
</html>