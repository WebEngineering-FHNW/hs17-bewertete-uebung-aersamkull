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

        taskType = ko.observable("new");

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
            const taskId = parseInt($(element).data('taskid'));
            const masterId = parseInt($(element).data('taskmasterid'));
            const date = $(element).data('taskdate');
            this.load(taskId, masterId, date);
        }

        /** Loads the data for the task.
         * Currently, this is called once only, but later this could be called multiple times 
         * (eg, if used in a SPA App)
         */
        load(id: number|undefined, masterid: number|undefined, date: string|undefined) {
            if(!id && !masterid) {
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
            $.getJSON(`/task/taskdata?id=${(id||0).toString()}&masterid=${masterid||0}&date=${date||""}`)
                .then(res=>{
                    this.isLoading(false);
                    this.description(res.description);
                    this.name(res.name);
                    this.taskType(res.type);
                    const rruleData = res.rrule ? (<string>res.rrule).split(';')
                        .map(s=>s.split('='))
                        .reduce((p, c)=> {
                            p[c[0]] = c[1]
                            return p;
                        }, <any>{}): null;
                        if(rruleData){
                            this.repetionValue(rruleData.FREQ);
                            this.repetitionInterval(parseInt(rruleData.INTERVAL));
                            this.repetitionStart(rruleData.START);
                            if(rruleData.COUNT) {
                                this.repetitionUntil(null);
                                // No support for COUNT on GUI currently
                            }
                            else {
                                this.repetitionUntil(rruleData.UNTIL||null);
                            }
                        }
                        else {
                            this.repetionValue("");
                        }
                        if(res.responsibles) {
                            this.responsibles(res.responsibles.map((v: UserData)=>v.id));
                        }
                        else {
                            this.responsibles([]);
                        }
                        if(res.responsible) {
                            this.responsible((<UserData>res.responsible).id);
                        }
                        else {
                            this.responsible(null);
                        }
                        if(res.date) {
                            this.taskDate(res.date);
                        }
                        else {
                            this.taskDate(null);
                        }
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
            
            // If it occurs multiple times, this will be a "Master", else it's a single
            if (this.taskType()==='new') {
                this.taskType(this.repetionValue() ? "MASTER": "SINGLE"); 
            }
            return true;
        }
    }

}
Tasker.applyViewModel(Tasker.TaskEditViewModel, "frmEditTask");
