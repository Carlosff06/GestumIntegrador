import { Dia } from "./dia";

export class Horario{
  id:string;
  empleadoId:any;
  fechaInicio:string;
  fechaFin:string;
  dias:Dia[];
  totalSemanal:number;
  constructor(id:string, empleadoId:any, fechaInicio:string, fechaFin:string,
   dias:Dia[], totalSemanal:number
  ){
    this.id=id;
    this.empleadoId=empleadoId;
    this.fechaInicio=fechaInicio;
    this.fechaFin=fechaFin;
    this.dias=dias;
    this.totalSemanal=totalSemanal;
  }

}
