import { Component, OnInit } from '@angular/core';
import {Security} from '../services/Security';
import {RespuestaApi} from '../models/RespuestaApi';
import {ACCESS_TOKEN_NAME, TOKEN_NAME} from '../constants';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  opened: boolean = false;
  isAdmin: boolean = false;

  constructor(
    private securityService: Security ){
  }

  ngOnInit() {
    setTimeout(() => {
      this.isAdmin = this.securityService.esRoleAdmin();
    },1500);

    setInterval(()=> {
      this.securityService.refreshToken().subscribe((data: RespuestaApi)=>{
        if(data.status == 'OK'){
          sessionStorage.setItem(TOKEN_NAME, data.idToken);
          sessionStorage.setItem(ACCESS_TOKEN_NAME, data.accessToken);
        }
      });
    },1000 * 60 * 30 );
  }

}
