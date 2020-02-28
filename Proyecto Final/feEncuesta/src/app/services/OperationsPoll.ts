import { Injectable } from "@angular/core";
import { HOST_BACKEND } from '../constants';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Persona } from '../models/Persona';
import { Subject } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class OperationsPoll {
    urlAddPoll:string = `${HOST_BACKEND}/api/encuesta/guardarpersona`;
    urlSelectPollResults:string = `${HOST_BACKEND}/api/encuesta/obtenerpersonas`;
    message = new Subject<string>();
    constructor(
        private http:HttpClient,
        private router: Router){}
    
    addPoll(persona: Persona){
        return this.http.post(`${this.urlAddPoll}`,persona);
    }

    selectPollResults(){
        return this.http.get<Persona[]>(`${this.urlSelectPollResults}`);
    }
}