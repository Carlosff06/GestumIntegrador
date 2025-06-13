import { ChangeDetectorRef, Component, signal, WritableSignal } from '@angular/core';
import { Horario } from '../../core/model/horario';
import { HorarioService } from '../../core/services/horario.service';
import { UsuarioService } from '../../auth/services/usuario.service';
import { EmpleadoService } from '../../core/services/empleado.service';
import { FormsModule } from '@angular/forms';
import { Empleado } from '../../core/model/empleado';
import { Dia } from '../../core/model/dia';

@Component({
  selector: 'app-horarios',
  imports: [FormsModule],
  templateUrl: './horarios.html',
  styleUrl: './horarios.scss'
})
export class Horarios {
  dias:Dia[]=[];
  //horario:WritableSignal<Horario> = signal<Horario>(new Horario('','','','',[],0));
  horario!:Horario;
  userid= signal('')
  dni:string='';
  empleado:WritableSignal<Empleado> = signal<Empleado>(new Empleado('','','','','',''))

  formattedDate = new Date().toISOString().split('T')[0];
  constructor(private cdr:ChangeDetectorRef, private readonly horarioService:HorarioService,
    private readonly usuarioService:UsuarioService,
  private readonly empleadoService:EmpleadoService){
      this.userid.set(this.usuarioService.getUsuarioId() ?? '');
     // this.buscarHorarioPorSemana('20250612192053');
  }


  extraerHora(fecha: string): string {
  if (!fecha) return '';

  const date = new Date(fecha);
  const horas = String(date.getHours()).padStart(2, '0');
  const minutos = String(date.getMinutes()).padStart(2, '0');

  return `${horas}:${minutos}`;
}


actualizarHora(dia: any, campo: 'horaEntrada' | 'horaSalida', event: Event): void {
  const input = event.target as HTMLInputElement;
  const nuevaHora = input?.value;

  if (!nuevaHora) return;

  const fechaActual = new Date(dia[campo]);
  const [hh, mm] = nuevaHora.split(':').map(Number);

  fechaActual.setHours(hh, mm, 0, 0);
  dia[campo] = fechaActual.toISOString();

  // Calcular diferencia si ambas fechas están definidas
  if (dia.horaEntrada && dia.horaSalida) {
    const entrada = new Date(dia.horaEntrada);
    const salida = new Date(dia.horaSalida);
    const diffMs = salida.getTime() - entrada.getTime();

    if (diffMs > 0) {
      const horas = Math.floor(diffMs / (1000 * 60 * 60));
      const minutos = Math.floor((diffMs % (1000 * 60 * 60)) / (1000 * 60));
      dia.horasTrabajadas = horas;
    } else {
      dia.horasTrabajadas = 0; // o puedes marcar como inválido si salida < entrada
    }
  }
}



  buscarHorarioPorSemana(userid:string){

    this.horarioService.buscarHorarioPorSemana(this.formattedDate,userid).subscribe({
      next:(res)=>{
        console.log(res)

        this.horario = res;
        const fechaBase = new Date(); // hoy
const lunesSemana = this.obtenerLunes(fechaBase);

let fechaInicio = lunesSemana;
  console.log(lunesSemana)
//console.log(fechaInicio)
  const diasMap = ["LUNES", "MARTES", "MIERCOLES", "JUEVES", "VIERNES", "SABADO"];

  this.horario.dias = this.horario.dias.map((diaObj) => {
    const index = diasMap.indexOf(diaObj.dia.toUpperCase());
    if (index !== -1) {
      const fecha = new Date(fechaInicio);
      fecha.setDate(fechaInicio.getDate() + index);
      const fechaBase = fecha.toISOString().split("T")[0];
      if(diaObj.horaEntrada==null){
      diaObj.horaEntrada = `${fechaBase}T00:00`;
      }
      if(diaObj.horaSalida==null){
      diaObj.horaSalida = `${fechaBase}T00:00`;
      }
    }
    return diaObj;
  });
  //console.log(this.horario)
        this.cdr.markForCheck();
      }, error:(err)=>{
        console.error(err);
        alert("Hubo un error");
      }
    })
  }

   obtenerLunes(hoy: Date): Date {
  const diaSemana = hoy.getDay(); // 0 (domingo) al 6 (sábado)
  const diferencia = (diaSemana + 6) % 7; // días desde el lunes

  const lunes = new Date(hoy);
  lunes.setDate(hoy.getDate() - diferencia);

  lunes.setHours(0, 0, 0, 0); // borrar la hora
  return lunes;
}



  buscarPorDni(event:KeyboardEvent){

    if(event.key=='Enter'){
    this.empleadoService.buscarEmpleadoPorDni(this.dni).subscribe({
      next:(res)=>{
        this.empleado.set(res);
        this.buscarHorarioPorSemana(res.id);
      },
      error:(err)=>{
        console.error(err);
        alert("Hubo un error");
      }
    })
    }
  }

  guardarHorario(){
    console.log(this.horario)
    this.horarioService.guardarHorario(this.horario).subscribe({
      next:(res)=>{
        alert("Horario guardado correctamente");
      }, error:(err)=>{
        console.error(err);
        alert("Hubo un error");
      }
    })
  }




}
