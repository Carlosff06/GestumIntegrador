import { Component, signal } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { TokenService } from '../../auth/services/token.service';
import { UsuarioService } from '../../auth/services/usuario.service';

@Component({
  selector: 'app-nav',
  imports: [RouterModule],
  templateUrl: './nav.html',
  styleUrl: './nav.scss'
})
export class Nav {

  rol = signal('');
  username = signal('');

  constructor(private router: Router, private tokenService:TokenService, private usuarioService:UsuarioService){
      //console.log(tokenService.getToken())

      this.rol.set(this.usuarioService.getRol() == 'ADMINISTRADOR' ? 'ADMIN' : 'COLABORADOR');
      this.username.set(this.usuarioService.getUsuario() ?? '');
  }
  logout(){
    this.tokenService.removeToken();
    this.router.navigate(['../login']);
  }
}
