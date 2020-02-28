import { Component, OnInit } from '@angular/core';
import { OperationsPoll } from '../services/OperationsPoll';
import { Persona } from '../models/Persona';
import {MatTableDataSource, MatDialog} from '@angular/material';
import { EditpollComponent } from '../editpoll/editpoll.component';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  dataSource:MatTableDataSource<Persona>;
  displayedColumns:string[]=['idpersona','nombres','apellidos','edad','lenguaje','editar'];

  constructor(private operations:OperationsPoll,
    private dialog:MatDialog) {
    this.dataSource = new MatTableDataSource<Persona>();
   }

  ngOnInit() {
    this.operations.selectPollResults().subscribe((data)=>{
      let respuesta = JSON.parse(JSON.stringify(data));
      this.dataSource = new MatTableDataSource<Persona>(respuesta);
    })
  }

  editar(persona:Persona){
    this.dialog.open(EditpollComponent, {
      width: '70%',
      height: '60%',
      data: { persona: persona }
    });
  }

}
