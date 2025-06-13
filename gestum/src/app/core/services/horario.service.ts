import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { API_URL } from '../../environment/environment';
import { Observable } from 'rxjs';
import { Horario } from '../model/horario';

@Injectable({
  providedIn: 'root'
})
export class HorarioService {
  private readonly apiUrl = `${API_URL}api/horarios`
  constructor(private readonly http:HttpClient) {

   }

   buscarHorarioPorSemana(fecha:string, empleadoId:string):Observable<Horario[]>{
    let params = new HttpParams()
    .set('fecha', fecha)
    .set('empleadoId', empleadoId);

    return this.http.get<Horario[]>(`${this.apiUrl}/buscar`, { params });
   }

   descargarExcel(fecha:string, empleadoId:string):Observable<Blob>{
    let params = new HttpParams()
    .set('fecha', fecha)
    .set('empleadoId', empleadoId);

    return this.http.get<Blob>(`${this.apiUrl}/descargar-excel`, { params, responseType:'blob' as 'json' });
   }

}
