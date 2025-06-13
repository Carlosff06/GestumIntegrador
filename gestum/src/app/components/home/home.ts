import { Component } from '@angular/core';
import { Nav } from '../../layout/nav/nav';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-home',
  imports: [Nav, RouterOutlet],
  templateUrl: './home.html',
  styleUrl: './home.scss'
})
export class Home {

}
