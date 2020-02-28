import { Component, OnInit, Inject } from '@angular/core';
import { Persona } from '../models/Persona';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { OperationsPoll } from '../services/OperationsPoll';
import { MatDialog } from '@angular/material';
import { RespuestaAddPoll } from '../models/RespuestaAddPoll';
import {SuccessComponent} from '../success/success.component';

@Component({
  selector: 'app-poll',
  templateUrl: './poll.component.html',
  styleUrls: ['./poll.component.css']
})
export class PollComponent implements OnInit {

  persona : Persona;
  formEncuesta:FormGroup;

  constructor(private router:Router,
    private operations:OperationsPoll,
    private dialog:MatDialog) {
    this.persona = new Persona();
    this.formEncuesta = new FormGroup(
      {
        '$key':new FormControl(null),
        'nombres':new FormControl('',Validators.required),
        'apellidos':new FormControl('',Validators.required),
        'edad':new FormControl('',[Validators.required,Validators.maxLength(3),Validators.pattern('[0-9]*')]),
        'lenguaje':new FormControl('Java')
      });
   }

  ngOnInit() { }

  onSubmit(){
    this.persona.nombres = this.formEncuesta.get('nombres').value;
    this.persona.apellidos = this.formEncuesta.get('apellidos').value;
    this.persona.edad = this.formEncuesta.get('edad').value;
    if(this.formEncuesta.get('lenguaje').value=="C"){
      this.persona.lenguaje = "C#";
    }else{
      this.persona.lenguaje = this.formEncuesta.get('lenguaje').value;
    }
    this.operations.addPoll(this.persona).subscribe((data:RespuestaAddPoll)=>{
      if(data.status=='OK'){
        this.operations.message.next('Gracias por contestar.');
        this.router.navigate(['logout']);
      }else{
        this.operations.message.next('Tus respuestas no han sido enviadas.')
      }

      this.openDialog();
    },(error)=>{
      this.operations.message.next('Tus respuestas no han sido enviadas.')
    });

  }

  openDialog(): void {
    this.dialog.open(SuccessComponent, {
      width: '32%',
      height: '25%',
    });
  }

}
