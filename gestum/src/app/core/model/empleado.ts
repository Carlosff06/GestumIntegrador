export class Empleado{
  id:string;
  dni:string;
  nombre:string;
  email:string;
  estado:string;
  area:string;

  constructor(id:string,
  dni:string,
  nombre:string,
  email:string,
  estado:string,
  area:string){
    this.id=id;
    this.dni=dni;
    this.nombre=nombre;
    this.email=email;
    this.estado=estado;
    this.area=area;
  }
}
