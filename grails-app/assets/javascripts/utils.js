var Tasker;
(function (Tasker) {
    function applyViewModel(viewModelFactory, elementId) {
        $(document).ready(function () {
            var element = document.getElementById(elementId);
            if (!element) {
                throw "Element not found!";
            }
            ko.applyBindings(new viewModelFactory(element), element);
        });
    }
    Tasker.applyViewModel = applyViewModel;
})(Tasker || (Tasker = {}));
