import { ChangeDetectorRef, Component, OnInit, Signal, signal, WritableSignal } from '@angular/core';
import { EmpleadoService } from '../../core/services/empleado.service';
import { Empleado } from '../../core/model/empleado';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-colaboradores',
  imports: [CommonModule],
  templateUrl: './colaboradores.html',
  styleUrl: './colaboradores.scss'
})
export class Colaboradores implements OnInit{

  empleados:WritableSignal<Empleado[]> = signal<Empleado[]>([]);

  constructor(private empleadoService:EmpleadoService){
    this.listarEmpleados();
  }

  ngOnInit(): void {
  this.empleadoService.escucharCambiosAsistencia().subscribe({
      next: cambio => {
        console.log('Cambio detectado:', cambio);
        // Aquí puedes volver a llamar a tu método de listarEmpleadosConAsistencia()
      },
      error: err => console.error(err)
    });
}


  listarEmpleados(){
    this.empleadoService.listarEmpleados().subscribe({
      next:(res)=>{
        this.empleados.set(res);
        console.log(res)
      }, error:(err)=>{
        console.error(err);
        alert("Hubo un error");
      }
    })
  }

}
