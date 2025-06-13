import { inject } from "@angular/core";
import { CanActivateFn, Router } from "@angular/router";
import { TokenService } from "../../auth/services/token.service";


export const guestGuard: CanActivateFn = (route, state) => {

    const tokenService = inject(TokenService);
    const router = inject(Router);
    //console.log('hola')
    tokenService.isAuthentication.subscribe({
        next:(value)=>{
            if(value){
                router.navigate(['home']);
            }
        }
    })

    return true;
}
