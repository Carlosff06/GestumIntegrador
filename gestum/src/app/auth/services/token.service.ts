import { Inject, Injectable} from '@angular/core';
import { stat } from 'fs';
import { BehaviorSubject } from 'rxjs';
import { constants } from '../../environment/environment';


@Injectable({
  providedIn: 'root'
})
export class TokenService {

  isAuthentication: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(
    false
  );

  constructor() {
    const token = this.getToken();
    if (token) {
      this.updateToken(true);
    }
  }

  initAuth(): Promise<void> {
  return new Promise(resolve => {
    const token = this.getToken();
    this.updateToken(!!token);
    resolve();
  });
}






  private isBrowser(): boolean {
    return typeof window !== 'undefined' && typeof sessionStorage !== 'undefined';
  }

  updateToken(status: boolean) {
    this.isAuthentication.next(status);
  }

  setToken(token: string) {
    this.updateToken(true);
    if (this.isBrowser()) {
      sessionStorage.setItem(constants.CURRENT_TOKEN, token);
    }
  }

  getToken(): string | null {
    if (this.isBrowser()) {
      return sessionStorage.getItem(constants.CURRENT_TOKEN);
    }
    return null;
  }

  removeToken() {
    this.updateToken(false);
    if (this.isBrowser()) {
      sessionStorage.removeItem(constants.CURRENT_TOKEN);
    }
  }
}
