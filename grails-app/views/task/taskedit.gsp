<html>
<head>
  <meta name="layout" content="main">
  <title>Edit Task</title>
</head>
<body>
    <form id="frmEditTask" 
      style="display: none" data-bind="visible: true" data-taskid="${id}" 
      data-taskmasterid="${masterid}" 
      data-taskdate="${date}"
      action="/task/save"
      method="POST"
      enctype="application/x-www-form-urlencoded"  
      >
        <input type="hidden" value="${id}" name="id" />
        <input type="hidden" value="${masterid}" name="masterid" />
        <input type="hidden" data-bind="value: taskType" name="type" />
        <div class="form-group row">
          <label class="col-sm-2 col-form-label">Name</label>
          <div class="col-sm-10">
            <input name="name" data-bind="value: name" required type="text" class="form-control" placeholder="Name">
          </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Description</label>
            <div class="col-sm-10">
              <textarea name="description" rows="4" data-bind="value: description" class="form-control" placeholder=""> </textarea>

            </div>
        </div>
        <div class="form-group row" data-bind="if: taskType()==='new'">
            <label class="col-sm-2 col-form-label">Repetition Frequency</label>
          <div class="col-sm-10">
              <select class="form-control" name="rrule_freq" data-bind="value: repetionValue">
                  <option value="">Once</option>  
                  <option value="DAILY">Daily</option>
                  <option value="WEEKLY">Weekly</option>
                  <option value="MONTHLY">Monthly</option>
                  <option value="YEARLY">Yearly</option>
                </select>
          </div>
        </div>
          <fieldset class="form-group" data-bind="if: isRepetition">
            <div class="row">
              <label class="col-sm-2 col-form-label">Repetition</label>
              <div class="col-sm-10">
                  <div class=row>
                      <div class="col-md-2">
                        <label class="col-form-label">Start</label>
                      </div>
                      <div class="col-md-10">
                        <input type="date" name="rrule_start" data-bind="value: repetitionStart" class="form-control" required>
                      </div>
                    </div>
                <div class=row>
                  <div class="col-md-2">
                    <label class="col-form-label">Until</label>
                  </div>
                  <div class="col-md-10">
                    <input name="rrule_until" type="date" class="form-control" data-bind="value: repetitionUntil" />
                  </div>
                </div>
                <div class=row>
                    <div class="col-md-2">
                      <label class="col-form-label">Interval</label>
                    </div>
                    <div class="col-md-10">
                      <input name="rrule_interval" data-bind="value: repetitionInterval" min="1" type="number" step="1" class="form-control">
                    </div>
                  </div>
                  <div class=row>
                      <div class="col-md-2">
                        <label class="col-form-label">Responsibles</label>
                      </div>
                      <div class="col-md-10">
                          <select name="responsibles" class="form-control" multiple  data-bind="selectedOptions: responsibles, options: allUsers, optionsText: 'name', optionsValue: 'id'">
                            <option>User1</option>
                            <option>User2</option>
                          </select>
                      </div>
                    </div>
              </div>
            </div>
          </fieldset>
          <div class="form-group row" data-bind="ifnot: isRepetition">
              <label class="col-sm-2 col-form-label">Responsible</label>
            <div class="col-sm-10">
                <select name="responsible" class="form-control" data-bind="value: responsible, options: allUsers, optionsText: 'name', optionsValue: 'id'">
                    
                  </select>
            </div>
          </div>
          <div class="form-group row" data-bind="ifnot: isRepetition">
              <label class="col-sm-2 col-form-label">Date</label>
            <div class="col-sm-10">
                <input type="date" required name="date" data-bind="value: taskDate, enable: taskType() !== 'OCCURENCE' && taskType() !== 'EXCEPTION'" class=form-control>
            </div>
          </div>
        <div class="form-group row">
          <div class="col-sm-10">
            <div class="alert alert-danger" data-bind="text: errorMessage, visible: errorMessage"></div>
            <button data-bind="click: save" type="submit" class="btn btn-primary">Save</button>
          </div>
        </div>
      </form>
</body>
</html>
<content tag="js">
  <asset:javascript src="taskedit.js" />
</content>