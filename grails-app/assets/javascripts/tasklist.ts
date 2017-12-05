//= require lib/knockout
//= require utils

class FormValidationViewModel {
    fromDate: KnockoutObservable<string>;
    toDate: KnockoutObservable<string>;

    isValid: KnockoutComputed<boolean>;

    constructor(element: HTMLElement){
        this.fromDate = ko.observable(<string>$("[name=fromDate]", element).val());
        this.toDate = ko.observable(<string>$("[name=toDate]", element).val());

        this.isValid = ko.computed((()=>{
            if(!this.fromDate())
                return false;
            if(!this.toDate())
                return false;
            const fromDateDt: Date = new Date(this.fromDate());
            const toDateDt: Date = new Date(this.toDate());
            return fromDateDt < toDateDt;
        }))
    }
}

Tasker.applyViewModel(FormValidationViewModel, "frmReloadFilter");
