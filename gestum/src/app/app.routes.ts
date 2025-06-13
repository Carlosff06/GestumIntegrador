import { Routes } from '@angular/router';
import { LoginComponent } from './auth/components/login/login.component';
import { Home } from './components/home/home';
import { Colaborador } from './components/colaborador/colaborador';
import { HorarioComponent } from './components/horario/horario';
import { Vacaciones } from './components/vacaciones/vacaciones';
import { Asistencia } from './components/asistencia/asistencia';
import { guestGuard } from './core/guards/guest.guard';
import { authGuard } from './core/guards/auth.guard';
import { Colaboradores } from './components/colaboradores/colaboradores';
import { rolGuard } from './core/guards/rol.guard';
import { Horarios } from './components/horarios/horarios';

export const routes: Routes = [
    {path:'', redirectTo: 'login', pathMatch: 'full'},
    {path: 'login',
      component:LoginComponent, canActivate: [guestGuard]
    },
    {
      path:'home',component:Home,
      children:[
      { path: '', redirectTo: 'colaborador', pathMatch: 'full' },
        {path:'colaborador', component:Colaborador},
        {path:'horarios', component:Horarios, canActivate:[rolGuard]},
        {path:'horario', component:HorarioComponent},
        {path: 'vacaciones', component:Vacaciones},
        {path:'asistencia', component:Asistencia},
        {path:'colaboradores', component:Colaboradores, canActivate:[rolGuard]}
      ], canActivate:[authGuard]
    }

      ]
