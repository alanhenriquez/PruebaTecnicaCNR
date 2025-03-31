import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ButtonDemoComponent } from "./button-demo/button-demo.component";
import { TreeTableComponent } from "./components/tree-table/tree-table.component";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, TreeTableComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'frontend';
}
