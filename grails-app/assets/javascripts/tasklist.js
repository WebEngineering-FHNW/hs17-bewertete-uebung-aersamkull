//= require lib/knockout
//= require utils
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
Tasker.applyViewModel(FormValidationViewModel, "frmReloadFilter");
