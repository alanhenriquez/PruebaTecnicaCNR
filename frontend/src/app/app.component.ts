import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ProyectosComponent } from "./proyectos/components/proyectos/proyectos.component";
import { TreeTableComponent } from "./api-external/components/tree-table/tree-table.component";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, ProyectosComponent, TreeTableComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'frontend';
}
