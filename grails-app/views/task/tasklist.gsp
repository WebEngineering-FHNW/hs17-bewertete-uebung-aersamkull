<html>
<head>
  <meta name="layout" content="main">
  <title>Tasks</title>
</head>
<body>

<h1>Tasks in period</h1>
<a class="btn btn-outline-primary" href="/task/new">New task</a>
<form id="frmReloadFilter" class="form-inline">
    <label class="sr-only" for="fromDate">From</label>
    <input data-bind="value: fromDate" required type="date" class="form-control mb-2 mr-sm-2 mb-sm-0" name="fromDate" id="fromDate" value="${fromDate}">
  
    <label class="sr-only" for="toDate">To</label>
    <div class="input-group mb-2 mr-sm-2 mb-sm-0">
      <input data-bind="value: toDate" required type="date" class="form-control" name="toDate" id="toDate" value="${toDate}" placeholder="Username">
    </div>  
    <button data-bind="enable: isValid" type="submit" class="btn btn-primary">Reload</button>
  </form>
<ul class="tasklist">
<g:each var="task" in="${tasks}">
  <li class="taskItem">
    <div>
        <h4>${task.name}</h4>
        <p>${task.description}</p>
        <p>Date: <input type="date" readonly value="${task.date}" ></p>
        <p>Responsible: ${task.responsible.name}</p>
        <div class="taskButtons">
          <button class="btn btn-outline-danger" type="button">Delete</button>  

          <g:if test="${task.type == 'SINGLE' || task.type == 'MASTER'}">
              <a href="/task/edit?id=${task.id}"  class="btn btn-outline-secondary">Edit</a>
          </g:if>
            <g:if test="${task.type == 'OCCURENCE' || task.type == 'EXCEPTION'}">
                <a href="/task/edit?masterid=${task.master.id}&date=${task.date}" class="btn btn-outline-primary">Edit</a>
                <a href="/task/edit?id=${task.master.id}"  class="btn btn-outline-secondary">Edit Master</a>
            </g:if>
        </div>
    </div>
  </li>
</g:each>
</ul>
  
<asset:javascript src="tasklist.js"/>
</body>
</html>