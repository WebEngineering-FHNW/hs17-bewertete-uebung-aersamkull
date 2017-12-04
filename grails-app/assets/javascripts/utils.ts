//= require lib/knockout

namespace Tasker {
    export function applyViewModel(viewModelFactory: {new(element: HTMLElement): any}, elementId: string) {
        $(document).ready(()=>{
            const element = document.getElementById(elementId) as HTMLElement;
            if(!element) {
                throw "Element not found!";
            }
            ko.applyBindings(new viewModelFactory(element), element);
        })
        
    }
}