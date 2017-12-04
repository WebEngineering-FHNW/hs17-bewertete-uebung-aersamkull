<html>
<head>
  <meta name="layout" content="main">
  <title>Edit Task</title>
</head>
<body>
    <form>
        <div class="form-group row">
          <label class="col-sm-2 col-form-label">Name</label>
          <div class="col-sm-10">
            <input required type="text" class="form-control" placeholder="Name">
          </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Description</label>
            <div class="col-sm-10">
              <textarea class="form-control" placeholder=""> </textarea>

            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Repetition Frequency</label>
          <div class="col-sm-10">
              <select class="form-control">
                  <option value="ONCE">Once</option>  
                  <option value="DAILY">Daily</option>
                  <option value="WEEKLY">Weekly</option>
                  <option value="MONTHLY">Monthly</option>
                  <option value="YEARLY">Yearly</option>
                </select>
          </div>
        </div>
          <fieldset class=form-group>
            <div class="row">
              <label class="col-sm-2 col-form-label">Repetition</label>
              <div class="col-sm-10">
                  <div class=row>
                      <div class="col-md-2">
                        <label class="col-form-label">Start</label>
                      </div>
                      <div class="col-md-10">
                        <input type=date class="form-control" required>
                      </div>
                    </div>
                <div class=row>
                  <div class="col-md-2">
                    <label class="col-form-label">Until</label>
                  </div>
                  <div class="col-md-10">
                    <input type=date class="form-control">
                  </div>
                </div>
                <div class=row>
                    <div class="col-md-2">
                      <label class="col-form-label">Interval</label>
                    </div>
                    <div class="col-md-10">
                      <input min=1 value=1 type=number step=1 class="form-control">
                    </div>
                  </div>
                  <div class=row>
                      <div class="col-md-2">
                        <label class="col-form-label">Responsibles</label>
                      </div>
                      <div class="col-md-10">
                          <select class="form-control" multiple>
                            <option>User1</option>
                            <option>User2</option>
                          </select>
                      </div>
                    </div>
              </div>
            </div>
          </fieldset>
          <div class="form-group row">
              <label class="col-sm-2 col-form-label">Responsible</label>
            <div class="col-sm-10">
                <select class="form-control">
                    <option>User1</option>
                    <option>User2</option>
                  </select>
            </div>
          </div>
          <div class="form-group row">
              <label class="col-sm-2 col-form-label">Date</label>
            <div class="col-sm-10">
                <input type=date required name=date class=form-control>
            </div>
          </div>
        <div class="form-group row">
          <div class="col-sm-10">
            <button type="submit" class="btn btn-primary">Save</button>
          </div>
        </div>
      </form>
</body>
</html>