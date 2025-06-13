import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { constants } from '../../environment/environment';


@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  constructor(private readonly  http:HttpClient) { }

  getUsuarioId(){
    return sessionStorage.getItem(constants.CURRENT_USERID) ?? null;
  }


  setUsuarioId(usuarioId:string){
    sessionStorage.setItem(constants.CURRENT_USERID, usuarioId);
  }

  setUsuario(usuario:string){

    sessionStorage.setItem(constants.CURRENT_USER, usuario);
  }

  setRol(rol:string){
    sessionStorage.setItem(constants.CURRENT_USER_ROLE, rol)
  }

  getRol(){
    return sessionStorage.getItem(constants.CURRENT_USER_ROLE) ?? null;

  }

  getUsuario(): string | null {
    return sessionStorage.getItem(constants.CURRENT_USER) ?? null;
  }

  removeUsuario(){

    return sessionStorage.removeItem(constants.CURRENT_USER);
  }


}
