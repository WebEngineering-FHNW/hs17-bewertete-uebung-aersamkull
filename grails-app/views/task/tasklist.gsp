<html>
<head>
  <meta name="layout" content="main">
  <title>Tasks</title>
</head>
<body>

<h1>Tasks in period</h1>
<a class="btn btn-outline-primary newTaskButton" href="/task/new">
  <span class="oi oi-plus" aria-hidden="true"></span>
  New task
</a>
<form id="frmReloadFilter" class="form-inline">
    <label class="sr-only" for="fromDate">From</label>
    <input data-bind="value: fromDate" required type="date" class="form-control mb-2 mr-sm-2 mb-sm-0" name="fromDate" id="fromDate" value="${fromDate}">
  
    <label class="sr-only" for="toDate">To</label>
    <div class="input-group mb-2 mr-sm-2 mb-sm-0">
      <input data-bind="value: toDate" required type="date" class="form-control" name="toDate" id="toDate" value="${toDate}" placeholder="Username">
    </div>  
    <button data-bind="enable: isValid" type="submit" class="btn btn-primary">
        <span class="oi oi-reload" aria-hidden="true"></span>  
    </button>
  </form>
<ul class="tasklist">
<g:each var="task" in="${tasks}">
  <li class="taskItem">
    <div>
        <h4>${task.name}</h4>
        <div class="taskBody">
          <p>${task.description}</p>
          <p>Date: <input type="date" readonly value="${task.date}" ></p>
          <p>Responsible: ${task.responsible.name}</p>
        </div>
        <div class="taskButtonHoster">
          <div class="taskButtons">
            <button class="btn btn-outline-danger" type="button">
                <span class="oi oi-trash" aria-hidden="true"></span>  
            </button>  

            <g:if test="${task.type == 'SINGLE' || task.type == 'MASTER'}">
                <a href="/task/edit?id=${task.id}"  class="btn btn-outline-secondary">
                  <span class="oi oi-pencil" aria-hidden="true"></span>
                </a>
            </g:if>
              <g:if test="${task.type == 'OCCURENCE' || task.type == 'EXCEPTION'}">
                  <a href="/task/edit?masterid=${task.masterid}&date=${task.date}" class="btn btn-outline-primary">
                    <span class="oi oi-pencil" aria-hidden="true"></span> Edit
                  </a>
                  <a href="/task/edit?id=${task.masterid}"  class="btn btn-outline-secondary">
                    <span class="oi oi-pencil" aria-hidden="true"></span>
                    Edit Master</a>
              </g:if>
          </div>
        </div>
    </div>
  </li>
</g:each>
</ul>
  
</body>
</html>
<content tag="js">

  <asset:javascript src="tasklist.js" />
</content>