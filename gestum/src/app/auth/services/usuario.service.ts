import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { constants } from '../../environment/environment';


@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  constructor(private readonly  http:HttpClient) { }

  getUsuarioId(){
    return localStorage.getItem(constants.CURRENT_USERID) ?? null;
  }


  setUsuarioId(usuarioId:string){
    localStorage.setItem(constants.CURRENT_USERID, usuarioId);
  }

  setUsuario(usuario:string){

    localStorage.setItem(constants.CURRENT_USER, usuario);
  }

  setRol(rol:string){
    localStorage.setItem(constants.CURRENT_USER_ROLE, rol)
  }

  getRol(){
    return localStorage.getItem(constants.CURRENT_USER_ROLE) ?? null;

  }

  getUsuario(): string | null {
    return localStorage.getItem(constants.CURRENT_USER) ?? null;
  }

  removeUsuario(){

    return localStorage.removeItem(constants.CURRENT_USER);
  }


}
