//= require lib/knockout
//= require lib/jquery.min
//= require utils
var Tasker;
(function (Tasker) {
    var SigninViewModel = /** @class */ (function () {
        function SigninViewModel(element) {
            this.element = element;
        }
        SigninViewModel.prototype.onClickSignin = function (data, event) {
            $("[name=name]", this.element).val(event.target.innerText);
            $("form", this.element).submit();
        };
        return SigninViewModel;
    }());
    Tasker.SigninViewModel = SigninViewModel;
})(Tasker || (Tasker = {}));
Tasker.applyViewModel(Tasker.SigninViewModel, "frmLogin");
