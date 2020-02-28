import { Component, OnInit,Inject } from '@angular/core';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';

@Component({
  selector: 'app-success',
  templateUrl: './success.component.html',
  styleUrls: ['./success.component.css']
})
export class SuccessComponent implements OnInit {


  ngOnInit() {
  }

  constructor(
    public dialogRef: MatDialogRef<SuccessComponent>) {}

  onClick(): void {
    this.dialogRef.close();
  }


}
