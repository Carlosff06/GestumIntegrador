import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { UsuarioService } from '../../services/usuario.service';


@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  loginForm:FormGroup;


  constructor(private formBuilder:FormBuilder, private router:Router,
    private authService:AuthService, private usuarioService:UsuarioService
  ){
    this.loginForm = this.formBuilder.group({
      username: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required])
    });
  }

  onLogin(){

    if(this.loginForm.valid){

      this.authService.onLogin(this.loginForm.value).subscribe({
        next: (value) => {

        this.router.navigate(['./home'])
        }, error: (err) => {
          alert('Usuario Incorrecto')
        }
      })
  }
  else {
    ////console.log('invalidado')
    this.loginForm.markAllAsTouched();
  }
  }
}
