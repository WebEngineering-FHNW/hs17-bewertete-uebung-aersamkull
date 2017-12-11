//= require lib/knockout
//= require lib/bootbox

//= require utils
namespace Tasker {
    export class FormValidationViewModel {
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
            }));

        }
    }
    export class TaskListViewModel {
        constructor(private element: HTMLElement) {
            
            $(".deleteButton", this.element).click(function(){
                const jqThis = $(this);
                bootbox.confirm("Are you sure to delete this task?", (res)=>{
                    if(res) {
                        const taskid = jqThis.data('taskid');
                        const taskmasterid = jqThis.data('taskmasterid');
                        const date = jqThis.data('date');
                        const url = `/task/delete?id=${ (taskid || "0")}`
                            + (taskmasterid ? `&masterid=${taskmasterid}`: "")
                            + (date ? `&date=${date}`: "");
                        $.ajax({
                            url: url,
                            type: "DELETE",
                            success: ()=>{
                                window.location.reload(true);
                            }
                        })
                    }
                })
            })
        }
    }
}
Tasker.applyViewModel(Tasker.FormValidationViewModel, "frmReloadFilter");
Tasker.applyViewModel(Tasker.TaskListViewModel, "taskList");