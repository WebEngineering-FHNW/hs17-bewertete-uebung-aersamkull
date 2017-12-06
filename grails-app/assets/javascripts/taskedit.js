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
            this.taskType = ko.observable("new");
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
            var taskId = parseInt($(element).data('taskid'));
            var masterId = parseInt($(element).data('taskmasterid'));
            var date = $(element).data('taskdate');
            this.load(taskId, masterId, date);
        }
        /** Loads the data for the task.
         * Currently, this is called once only, but later this could be called multiple times
         * (eg, if used in a SPA App)
         */
        TaskEditViewModel.prototype.load = function (id, masterid, date) {
            var _this = this;
            if (!id && !masterid) {
                // It's a new task!
                // Reset everything to default
                this.taskType("new");
                this.responsibles([]);
                this.responsible(null);
                this.name("");
                this.description("");
                this.repetionValue("");
                this.repetitionInterval(1);
                this.repetitionStart(new Date().toJSON());
                this.repetitionUntil(null);
                this.taskDate(new Date().toJSON());
                return;
            }
            this.isLoading(true);
            $.getJSON("/task/taskdata?id=" + (id || 0).toString() + "&masterid=" + (masterid || 0) + "&date=" + (date || ""))
                .then(function (res) {
                _this.isLoading(false);
                _this.description(res.description);
                _this.name(res.name);
                _this.taskType(res.type);
                var rruleData = res.rrule ? res.rrule.split(';')
                    .map(function (s) { return s.split('='); })
                    .reduce(function (p, c) {
                    p[c[0]] = c[1];
                    return p;
                }, {}) : null;
                if (rruleData) {
                    _this.repetionValue(rruleData.FREQ);
                    _this.repetitionInterval(parseInt(rruleData.INTERVAL));
                    _this.repetitionStart(rruleData.START);
                    if (rruleData.COUNT) {
                        _this.repetitionUntil(null);
                        // No support for COUNT on GUI currently
                    }
                    else {
                        _this.repetitionUntil(rruleData.UNTIL || null);
                    }
                }
                else {
                    _this.repetionValue("");
                }
                if (res.responsibles) {
                    _this.responsibles(res.responsibles.map(function (v) { return v.id; }));
                }
                else {
                    _this.responsibles([]);
                }
                if (res.responsible) {
                    _this.responsible(res.responsible.id);
                }
                else {
                    _this.responsible(null);
                }
                if (res.date) {
                    _this.taskDate(res.date);
                }
                else {
                    _this.taskDate(null);
                }
            });
        };
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
            // If it occurs multiple times, this will be a "Master", else it's a single
            if (this.taskType() === 'new') {
                this.taskType(this.repetionValue() ? "MASTER" : "SINGLE");
            }
            return true;
        };
        return TaskEditViewModel;
    }());
    Tasker.TaskEditViewModel = TaskEditViewModel;
})(Tasker || (Tasker = {}));
Tasker.applyViewModel(Tasker.TaskEditViewModel, "frmEditTask");
