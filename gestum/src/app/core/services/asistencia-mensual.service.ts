import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { API_URL } from '../../environment/environment';
import { AsistenciaMensual } from '../model/asistencia-mensual';
import { Observable } from 'rxjs';
import { EntradaRequest } from '../model/entrada-request';
import { SalidaRequest } from '../model/salida-request';

@Injectable({
  providedIn: 'root'
})
export class AsistenciaMensualService {
  private apiUrl = `${API_URL}api/asistencia`
  constructor(private http:HttpClient) {

  }

  listarAsistencias(empleadoId:string):Observable<AsistenciaMensual>{
    return this.http.get<AsistenciaMensual>(`${this.apiUrl}/registrar-dias?empleadoId=${empleadoId}`)
  }

  registrarEntrada(request: EntradaRequest): Observable<any> {




    return this.http.post<any>(`${this.apiUrl}/marcar-entrada`, request);
  }

  
  registrarSalida(request: SalidaRequest): Observable<any> {




    return this.http.post<any>(`${this.apiUrl}/marcar-salida`, request);
  }
}
