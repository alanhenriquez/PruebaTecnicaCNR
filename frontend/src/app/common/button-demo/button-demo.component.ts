import { Component, Input } from '@angular/core';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-button-demo',
  templateUrl: './button-demo.component.html',
  standalone: true,
  imports: [ButtonModule]
})
export class ButtonDemoComponent {
  @Input() onClick: () => void = () => {}; // Función por defecto vacía

  handleButtonClick(): void {
    this.onClick(); // Ejecutar la función pasada como entrada
  }
}