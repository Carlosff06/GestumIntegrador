import { CommonModule } from '@angular/common';
import { Component, inject, resource, signal } from '@angular/core';
import { AsistenciaMensualService } from '../../core/services/asistencia-mensual.service';
import { UsuarioService } from '../../auth/services/usuario.service';
import { AsistenciaMensual } from '../../core/model/asistencia-mensual';
import { API_URL } from '../../environment/environment';
import { TokenService } from '../../auth/services/token.service';

@Component({
  selector: 'app-asistencia',
  imports: [CommonModule],
  templateUrl: './asistencia.html',
  styleUrl: './asistencia.scss'
})
export class Asistencia {
  private apiUrl = `${API_URL}api/asistencia`
  hoy = new Date()
  meses: string[] = [
  'Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio',
  'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'
];
usuarioService = inject(UsuarioService);
horasTrabajadas = signal(0);
  constructor(private asistenciaService:AsistenciaMensualService,
    private tokenService:TokenService
  ){
   // this.listarAsistenciaMensual()
  }

  // Signal con el ID del usuario
  usuarioId = signal(this.usuarioService.getUsuarioId() ?? '');

  // Resource que se recarga cuando cambia el usuarioId
  asistencias = resource({
  params: () => ({ id: this.usuarioId() }),
  loader: async ({ params }) => {
    const token = this.tokenService.getToken(); // o de donde estés guardando el token
    console.log(token)
    const res = await fetch(`${this.apiUrl}/registrar-dias?empleadoId=${params.id}`, {
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
      }
    });

    if (!res.ok) {
      throw new Error('Error al obtener las asistencias');
    }
    const respuesta = await res.json();
console.log(respuesta);
    let totalMinutos = 0;
    for(let asistencia of respuesta.asistencias){
      //console.log(asistencia)
      if(asistencia.tiempoTotal!=null){
       const [horasStr, minutosStr] = asistencia.tiempoTotal.split(':');
    const horas = parseInt(horasStr, 10);
      const minutos = parseInt(minutosStr, 10);
    totalMinutos += horas ;
      }
    }
    this.horasTrabajadas.set(totalMinutos);
    console.log(this.horasTrabajadas())
    return respuesta;
  }
});



  listarAsistenciaMensual(){
    const userid= this.usuarioService.getUsuarioId() ?? '';
    console.log("buscando")
    this.asistenciaService.listarAsistencias(userid).subscribe({
      next:(res)=> {
         console.log(res);
         this.asistencias.set(res);
      },
      error:(err)=>{
        alert("Hubo un error listando la asistencia");
        console.error(err);
      }
    })
  }
}
