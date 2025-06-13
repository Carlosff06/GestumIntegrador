import { ChangeDetectorRef, Component, OnInit, Signal, signal, WritableSignal } from '@angular/core';
import { EmpleadoService } from '../../core/services/empleado.service';
import { Empleado } from '../../core/model/empleado';
import { CommonModule } from '@angular/common';
import { API_URL } from '../../environment/environment';
import { TokenService } from '../../auth/services/token.service';

@Component({
  selector: 'app-colaboradores',
  imports: [CommonModule],
  templateUrl: './colaboradores.html',
  styleUrl: './colaboradores.scss'
})
export class Colaboradores implements OnInit{
  private socket!:WebSocket;
  empleados:WritableSignal<Empleado[]> = signal<Empleado[]>([]);

  constructor(private empleadoService:EmpleadoService, private tokenService:TokenService){
    this.listarEmpleados();

  }

  ngOnInit(): void {
    this.connect();
  }

  ngOnDestroy(): void {
    this.close();
  }


get totalAsistentes(): number {
  return this.empleados().filter(e => e.estado === 'asistencia').length;
}

connect():void{
  this.socket = new WebSocket(`ws://localhost:8080/ws/asistencia`)

  this.socket.onopen = () => {
    console.log("WebSocket conectado");
  }

  this.socket.onmessage = (event) => {
    console.log("Mensaje recibido", event.data);
    this.listarEmpleados();
  }

  this.socket.onclose = () => {
    console.log("WebSocket cerrado");
  }

  this.socket.onerror = (error) => {
    console.error("WebSocket error:", error);
  }


}

close(): void {
    this.socket?.close();
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
