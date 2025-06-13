import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { TokenService } from '../../auth/services/token.service';
import { map, take } from 'rxjs/operators';

export const authGuard: CanActivateFn = (route, state) => {
  const tokenService = inject(TokenService);
  const router = inject(Router);

  return tokenService.isAuthentication.pipe(
    take(1),
    map(isAuth => {
     
     if (!isAuth) {
        router.navigate(['/login']);
        return false;
      }
      return true;
    })
  );
};
