//= require lib/knockout
//= require lib/jquery.min
//= require utils

namespace Tasker {
    export class SigninViewModel 
    {
        constructor(private element: HTMLElement) {

        }

        onClickSignin(data: never, event: Event) {
            $("[name=name]", this.element).val((event.target as HTMLElement).innerText);
            $("form", this.element).submit();
        }
    }
}

Tasker.applyViewModel(Tasker.SigninViewModel, "frmLogin");