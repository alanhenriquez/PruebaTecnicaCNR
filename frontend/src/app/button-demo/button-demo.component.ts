import { Component } from '@angular/core';
import { ButtonModule } from 'primeng/button';

@Component({
    selector: 'app-button-demo',
    templateUrl: './button-demo.component.html',
    standalone: true,
    imports: [ButtonModule]
})
export class ButtonDemoComponent {}