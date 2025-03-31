import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { providePrimeNG } from 'primeng/config';
import { TreeTableModule } from 'primeng/treetable';
import Aura from '@primeng/themes/aura';

import { routes } from './app.routes';
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { SharedModule } from 'primeng/api';

export const appConfig: ApplicationConfig = {
    providers: [
        provideZoneChangeDetection({ eventCoalescing: true }), 
        provideRouter(routes),
        provideHttpClient(withInterceptorsFromDi()),
        BrowserModule,
        TreeTableModule,
        SharedModule,
        provideAnimationsAsync(),
        providePrimeNG({
            theme: {
                preset: Aura, // Usa el tema "Aura"
                options: {
                    prefix: 'p', // Prefijo para las clases CSS
                    darkModeSelector: 'light', // Modo oscuro basado en el sistema
                    cssLayer: false // Desactiva el uso de capas CSS
                }
            },
            ripple: true, // Habilita la animación ripple en componentes compatibles
            inputVariant: 'filled', // Estilo de campos de entrada (filled o outlined)
            zIndex: {
                modal: 1100,    // Para diálogos y sidebars
                overlay: 1000,  // Para dropdowns y overlays
                menu: 1000,     // Para menús
                tooltip: 1100   // Para tooltips
            },
            csp: {
                nonce: 'your-nonce-value' // Valor nonce para CSP (si es necesario)
            },
            filterMatchModeOptions: {
                text: [
                    'startsWith', 'contains', 'notContains', 
                    'endsWith', 'equals', 'notEquals'
                ],
                numeric: [
                    'equals', 'notEquals', 'lessThan', 
                    'lessThanOrEqual', 'greaterThan', 'greaterThanOrEqual'
                ],
                date: [
                    'dateIs', 'dateIsNot', 'dateBefore', 'dateAfter'
                ]
            }
        })
    ]
};
