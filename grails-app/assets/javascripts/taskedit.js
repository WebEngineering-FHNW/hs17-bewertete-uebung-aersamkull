//= require lib/knockout
//= require lib/jquery.min
//= require utils
var Tasker;
(function (Tasker) {
    var TaskEditViewModel = /** @class */ (function () {
        function TaskEditViewModel(element) {
            var _this = this;
            this.name = ko.observable('');
            this.description = ko.observable('');
            this.repetionValue = ko.observable('');
            this.responsible = ko.observable();
            this.responsibles = ko.observableArray();
            this.taskDate = ko.observable();
            this.repetitionStart = ko.observable(new Date().toJSON());
            this.repetitionUntil = ko.observable(new Date().toJSON());
            this.repetitionInterval = ko.observable(1);
            this.allUsers = ko.observableArray([]);
            this.errorMessage = ko.observable('');
            this.isLoading = ko.observable(false);
            this.isRepetition = ko.pureComputed(function () { return _this.repetionValue() !== ''; });
            $.getJSON("/user/usersdata").then(function (res) {
                _this.allUsers(res.dt);
            });
        }
        TaskEditViewModel.prototype.save = function (dt, evt) {
            if (!this.name()) {
                this.errorMessage("Please provide a name");
                return false;
            }
            if (!this.taskDate() && !this.isRepetition()) {
                this.errorMessage("Please provide a date");
                return false;
            }
            if (!this.responsible() && !this.isRepetition()) {
                this.errorMessage("Please provide a responsible");
                return false;
            }
            if (this.isRepetition() && this.responsibles().length === 0) {
                this.errorMessage('You must select at least one responsible');
                return false;
            }
            this.errorMessage('');
            return true;
        };
        return TaskEditViewModel;
    }());
    Tasker.TaskEditViewModel = TaskEditViewModel;
})(Tasker || (Tasker = {}));
Tasker.applyViewModel(Tasker.TaskEditViewModel, "frmEditTask");
