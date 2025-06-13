import { ChangeDetectorRef, Component, OnInit, signal, WritableSignal } from '@angular/core';
import { HorarioService } from '../../core/services/horario.service';
import { Horario } from '../../core/model/horario';
import { UsuarioService } from '../../auth/services/usuario.service';

@Component({
  selector: 'app-horario',
  imports: [],
  templateUrl: './horario.html',
  styleUrl: './horario.scss'
})
export class HorarioComponent implements OnInit{


   horario:WritableSignal<Horario> = signal<Horario>(new Horario('','','','',[],0));

  userid= signal('')

  formattedDate = new Date().toISOString().split('T')[0];
  constructor(private readonly horarioService:HorarioService,
    private cdr: ChangeDetectorRef, private usuarioService:UsuarioService){
      this.userid.set(this.usuarioService.getUsuarioId() ?? '');
  }
  ngOnInit(): void {
    this.buscarHorarioPorSemana()
  }

  extraerHora(fecha: string): string {
  if (!fecha) return '';

  const date = new Date(fecha);
  const horas = String(date.getHours()).padStart(2, '0');
  const minutos = String(date.getMinutes()).padStart(2, '0');

  return `${horas}:${minutos}`;
}



  buscarHorarioPorSemana(){

    this.horarioService.buscarHorarioPorSemana(this.formattedDate,this.userid()).subscribe({
      next:(res)=>{
        console.log(res)
        this.horario.set(res);
        this.cdr.markForCheck();
      }, error:(err)=>{
        console.error(err);
        alert("Hubo un error");
      }
    })
  }

  descargarExcel(){
    this.horarioService.descargarExcel(this.formattedDate,this.userid()).subscribe({
      next:(blob)=>{
        const a = document.createElement('a');
    const objectUrl = URL.createObjectURL(blob);
    a.href = objectUrl;
    a.download = 'horario.xlsx';
    a.click();
    URL.revokeObjectURL(objectUrl);
        this.cdr.detectChanges();
      }, error:(err)=>{
        console.error(err);
        alert("Hubo un error");
      }
    })
  }

}
