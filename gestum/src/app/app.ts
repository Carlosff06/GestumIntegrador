import { Component, OnInit, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { TokenService } from './auth/services/token.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, CommonModule, FormsModule],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App implements OnInit{
  protected title = 'gestum';
  
  loading = signal(true);
  constructor(private readonly tokenService:TokenService){

  }

  ngOnInit(): void {
    console.log("cargando")
    console.log(this.loading)
      this.tokenService.initAuth().then(() => {

        this.loading.set(false);
         console.log("no cargando")
         console.log(this.loading)
  });
  }
}
