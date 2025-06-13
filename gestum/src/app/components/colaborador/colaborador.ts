import { Component, resource, signal } from '@angular/core';
import { Empleado } from '../../core/model/empleado';
import { CommonModule } from '@angular/common';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { EmpleadoService } from '../../core/services/empleado.service';
import { AsistenciaMensualService } from '../../core/services/asistencia-mensual.service';
import { EntradaRequest } from '../../core/model/entrada-request';
import { UsuarioService } from '../../auth/services/usuario.service';
import { SalidaRequest } from '../../core/model/salida-request';

@Component({
  selector: 'app-colaborador',
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './colaborador.html',
  styleUrl: './colaborador.scss'
})
export class Colaborador {
  private readonly RESOURCE_URL = 'http://localhost:8080/empleado';
  formGroup = new FormGroup({
    nombre_apellidos: new FormControl('', Validators.required),
    dni: new FormControl('', [Validators.required, Validators.minLength(8)]),
    email: new FormControl('', [Validators.required, Validators.email]),
    estado: new FormControl('', Validators.required), // lo estás usando en HTML pero no lo definiste antes
    area: new FormControl('', Validators.required),
    codigo: new FormControl('', Validators.required)
  });

  constructor(private readonly asistenciaService:AsistenciaMensualService,
    private readonly empleadoService:EmpleadoService,
    private readonly usuarioService:UsuarioService){
    this.buscarEmpleado();
  }

  buscarEmpleado(){
    const empleadoId=this.usuarioService.getUsuarioId() ?? '';
    this.empleadoService.buscarEmpleado(empleadoId).subscribe({
      next:(res)=>{
       // console.log(res)
        this.cargarFormulario(res);
      }, error:(err)=>{
        console.error(err);
        alert("Hubo un error")
      }

    })
  }
  cargarFormulario(empleado:Empleado){
    this.formGroup.get('nombre_apellidos')?.setValue(empleado.nombre);
    this.formGroup.get('dni')?.setValue(empleado.dni);
    this.formGroup.get('email')?.setValue(empleado.email);
    this.formGroup.get('estado')?.setValue(empleado.estado);
    this.formGroup.get('area')?.setValue(empleado.area);
    this.formGroup.get('codigo')?.setValue(empleado.id);
  }

  marcarEntrada(){
    const registroEntrada : EntradaRequest = {
      empleadoId:this.usuarioService.getUsuarioId() ?? '',
      horaEntrada: new Date().toLocaleString('sv-SE', { timeZone: 'America/Lima' }).replace(' ', 'T')
    }
    console.log(registroEntrada)
    this.asistenciaService.registrarEntrada(registroEntrada).subscribe({
      next:(res)=>{
        console.log(res)
        alert("Tiempo de Entrada Registrado "+registroEntrada.horaEntrada)
      },
      error:(err)=>{
        console.error(err);
      }
    })
  }

   marcarSalida(){
    const registroSalida : SalidaRequest = {
      empleadoId:this.usuarioService.getUsuarioId() ?? '',
      horaSalida: new Date().toLocaleString('sv-SE', { timeZone: 'America/Lima' }).replace(' ', 'T')
    }

    this.asistenciaService.registrarSalida(registroSalida).subscribe({
      next:(res)=>{
        console.log(res)
        alert("Tiempo de Salida Registrado "+registroSalida.horaSalida)
      },
      error:(err)=>{
        console.error(err);
      }
    })
  }

}
