import { inject } from "@angular/core";
import { CanActivateFn, Router } from "@angular/router";
import { UsuarioService } from "../../auth/services/usuario.service";

export const rolGuard: CanActivateFn = (route, state) => {

    const usuarioService = inject(UsuarioService);
    const router = inject(Router);
    //console.log('hola')
    if(usuarioService.getRol() !=='ADMINISTRADOR'){
      router.navigate(['home']);
      return false;
    }


    return true;
}
