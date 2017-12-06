//= require lib/knockout
//= require lib/jquery.min
//= require utils

type repetitionType = "" | "DAILY" |"WEEKLY" | "MONTHLY" | "YEARLY";

interface UserData {id: number; name: string}

namespace Tasker {
    export class TaskEditViewModel {
        name = ko.observable('');
        description = ko.observable('');
        repetionValue = ko.observable<repetitionType>('');
        responsible = ko.observable<number>();
        responsibles = ko.observableArray<number>();
        taskDate = ko.observable<string>();

        taskType : string;

        repetitionStart = ko.observable(new Date().toJSON());
        repetitionUntil = ko.observable(new Date().toJSON());
        repetitionInterval = ko.observable<string|number>(1);

        isRepetition : KnockoutObservable<boolean>;

        allUsers = ko.observableArray<UserData>([]);

        isValid: KnockoutComputed<boolean>;

        errorMessage = ko.observable('');
        isLoading = ko.observable(false);


        constructor(element: HTMLElement){
            
            this.isRepetition = ko.pureComputed(()=>this.repetionValue() !== '');
            $.getJSON("/user/usersdata").then((res)=>{
                this.allUsers(res.dt);
            });
        }

        save(dt: never, evt: Event) {
            if(!this.name()) {
                this.errorMessage("Please provide a name");
                return false;
            }
            if(!this.taskDate() && !this.isRepetition()) {
                this.errorMessage("Please provide a date");
                return false;
            }
            if(!this.responsible() && !this.isRepetition()) {
                this.errorMessage("Please provide a responsible");
                return false;
            }
            if(this.isRepetition() && this.responsibles().length===0) {
                this.errorMessage('You must select at least one responsible');
                return false;
            }
            this.errorMessage('');
            return true;
        }
    }

}
Tasker.applyViewModel(Tasker.TaskEditViewModel, "frmEditTask");
