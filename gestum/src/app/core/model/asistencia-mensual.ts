import { DiaAsistencia } from "./dia-asistencia";

export class AsistenciaMensual{
  id:string;
  empleadoId:string;
  anio:number;
  mes:number;
  asistencias:DiaAsistencia[];

  constructor(id:string, empleadoId:string, anio:number,
    mes:number, asistencias:DiaAsistencia[]
  ){
    this.id=id;
    this.empleadoId=empleadoId;
    this.anio=anio;
    this.mes=mes;
    this.asistencias=asistencias;
  }
}
