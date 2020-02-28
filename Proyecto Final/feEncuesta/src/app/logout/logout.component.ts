import { Component, OnInit } from '@angular/core';
import {Security} from '../services/Security';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})
export class LogoutComponent implements OnInit {

  constructor(private security:Security) { }

  ngOnInit() {
    this.security.cerrarSesion();
  }

}
