import { Component, OnInit } from '@angular/core';
import {LoginDTO} from '../models/LoginDTO';
import { Router } from '@angular/router';
import {MatDialog} from '@angular/material';
import {Security} from '../services/Security';
import {RespuestaApi} from '../models/RespuestaApi';
import {TOKEN_NAME,REFRESH_TOKEN_NAME,ACCESS_TOKEN_NAME,PARAM_USUARIO} from '../constants';
import {ErrorComponent} from '../error/error.component';
import { FormGroup,FormControl,Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  login: LoginDTO;
  formLogin: FormGroup;

  constructor( private router: Router,
    private securityService: Security,
    private dialog:MatDialog) { 
      this.login = new LoginDTO();
      this.formLogin = new FormGroup(
        {
          'username':new FormControl('',Validators.required),
          'password':new FormControl('',Validators.required),
        });
    }

  ngOnInit() {
  }

  onSubmit() {
    this.login.username = this.formLogin.get('username').value;
    this.login.password = this.formLogin.get('password').value;
    this.securityService.login(this.login).subscribe((data: RespuestaApi)=>{
      if(data.status == 'OK'){
        sessionStorage.setItem(TOKEN_NAME, data.idToken);
        sessionStorage.setItem(REFRESH_TOKEN_NAME, data.refreshToken);
        sessionStorage.setItem(ACCESS_TOKEN_NAME, data.accessToken);

        this.securityService.validarToken().subscribe((dato: any)=>{
          sessionStorage.setItem(PARAM_USUARIO, JSON.stringify(dato.body));
          if(this.securityService.esRoleAdmin()){
            this.router.navigate(["home/admin"]);
          }else{
            this.router.navigate(["home/poll"]);
          }
          
        });
      }else{
        this.dialog.open(ErrorComponent, {
          width: '60%',
          height: '60%',
          data: { 
            error: data.body,
            dato: data,
            usuario: this.login.username 
          }
        });
      }
  }, (error) => {
    alert(error.message);
    });
  }

}
