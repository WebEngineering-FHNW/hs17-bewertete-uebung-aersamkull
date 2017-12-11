//= require lib/knockout
//= require lib/bootbox
//= require utils
var Tasker;
(function (Tasker) {
    var FormValidationViewModel = /** @class */ (function () {
        function FormValidationViewModel(element) {
            var _this = this;
            this.fromDate = ko.observable($("[name=fromDate]", element).val());
            this.toDate = ko.observable($("[name=toDate]", element).val());
            this.isValid = ko.computed((function () {
                if (!_this.fromDate())
                    return false;
                if (!_this.toDate())
                    return false;
                var fromDateDt = new Date(_this.fromDate());
                var toDateDt = new Date(_this.toDate());
                return fromDateDt < toDateDt;
            }));
        }
        return FormValidationViewModel;
    }());
    Tasker.FormValidationViewModel = FormValidationViewModel;
    var TaskListViewModel = /** @class */ (function () {
        function TaskListViewModel(element) {
            this.element = element;
            $(".deleteButton", this.element).click(function () {
                var jqThis = $(this);
                bootbox.confirm("Are you sure to delete this task?", function (res) {
                    if (res) {
                        var taskid = jqThis.data('taskid');
                        var taskmasterid = jqThis.data('taskmasterid');
                        var date = jqThis.data('date');
                        var url = "/task/delete?id=" + (taskid || "0")
                            + (taskmasterid ? "&masterid=" + taskmasterid : "")
                            + (date ? "&date=" + date : "");
                        $.ajax({
                            url: url,
                            type: "DELETE",
                            success: function () {
                                window.location.reload(true);
                            }
                        });
                    }
                });
            });
        }
        return TaskListViewModel;
    }());
    Tasker.TaskListViewModel = TaskListViewModel;
})(Tasker || (Tasker = {}));
Tasker.applyViewModel(Tasker.FormValidationViewModel, "frmReloadFilter");
Tasker.applyViewModel(Tasker.TaskListViewModel, "taskList");
