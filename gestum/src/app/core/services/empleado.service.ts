import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { API_URL } from '../../environment/environment';
import { Observable } from 'rxjs';
import { Empleado } from '../model/empleado';

@Injectable({
  providedIn: 'root'
})
export class EmpleadoService {

  private readonly apiUrl = `${API_URL}empleado`

  constructor(private readonly http:HttpClient) {
   }
   listarEmpleados():Observable<Empleado[]>{
    return this.http.get<Empleado[]>(`${this.apiUrl}/listar-empleados-asistencia`)
   }

   buscarEmpleado(id:string):Observable<Empleado>{
    const params = new HttpParams().set('id', id);

    return this.http.get<Empleado>(this.apiUrl, { params });
   }

   escucharCambiosAsistencia(): Observable<any> {
    return new Observable(observer => {
      const eventSource = new EventSource(`${this.apiUrl}/asistencia/stream`);

      eventSource.onmessage = (event) => {
        const data = JSON.parse(event.data);
        observer.next(data);
      };

      eventSource.onerror = (error) => {
        console.error('Error en el stream de asistencia:', error);
        eventSource.close();
        observer.error(error);
      };
    });
  }
}
