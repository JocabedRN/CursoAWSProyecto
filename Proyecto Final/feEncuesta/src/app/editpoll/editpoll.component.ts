import { Component, OnInit, Inject } from '@angular/core';
import { Persona } from '../models/Persona';
import { FormGroup,FormControl,Validators } from '@angular/forms';
import { RespuestaAddPoll } from '../models/RespuestaAddPoll';
import { MAT_DIALOG_DATA } from '@angular/material';
import { OperationsPoll } from '../services/OperationsPoll';

@Component({
  selector: 'app-editpoll',
  templateUrl: './editpoll.component.html',
  styleUrls: ['./editpoll.component.css']
})
export class EditpollComponent implements OnInit {

  persona : Persona;
  formEditEncuesta:FormGroup;

  constructor(private operations:OperationsPoll,
    @Inject(MAT_DIALOG_DATA) public data:any) { 
    this.persona = new Persona();
    this.formEditEncuesta = new FormGroup(
      {
        '$key':new FormControl(null),
        'nombres':new FormControl('',Validators.required),
        'apellidos':new FormControl('',Validators.required),
        'edad':new FormControl('',[Validators.required,Validators.maxLength(3),Validators.pattern('[0-9]*')]),
        'lenguaje':new FormControl('Java')
      });
  }

  ngOnInit() {
    if(this.data!=null && this.data.persona!=null){
      this.persona = this.data.persona;
      this.formEditEncuesta.get('nombres').setValue(this.persona.nombres);
      this.formEditEncuesta.get('apellidos').setValue(this.persona.apellidos);
      this.formEditEncuesta.get('edad').setValue(this.persona.edad);
      if(this.persona.lenguaje=="C#"){
        this.formEditEncuesta.get('lenguaje').setValue("C");
      }else{
        this.formEditEncuesta.get('lenguaje').setValue(this.persona.lenguaje);
      }
    }
  }

  onSubmit(){
    this.persona.nombres = this.formEditEncuesta.get('nombres').value;
    this.persona.apellidos = this.formEditEncuesta.get('apellidos').value;
    this.persona.edad = this.formEditEncuesta.get('edad').value;
    if(this.formEditEncuesta.get('lenguaje').value=="C"){
      this.persona.lenguaje = "C#";
    }else{
      this.persona.lenguaje = this.formEditEncuesta.get('lenguaje').value;
    }
    this.operations.addPoll(this.persona).subscribe((data:RespuestaAddPoll)=>{
      if(data.status=='OK'){
        this.operations.message.next('Se actualizó el registro');
      }else{
        this.operations.message.next('No se pudo actualizar')
      }
    },(error)=>{
      this.operations.message.next('No se pudo actualizar')
    });

  }

}
