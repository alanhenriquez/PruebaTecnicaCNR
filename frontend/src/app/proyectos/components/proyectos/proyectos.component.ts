import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { TreeTableModule } from 'primeng/treetable';
import { TreeNode } from 'primeng/api';
import { ProyectosService } from '../../services/proyectos.service';

@Component({
  selector: 'app-proyectos',
  standalone: true, // Marcar el componente como standalone
  imports: [
    CommonModule,
    HttpClientModule, // Para realizar solicitudes HTTP
    TreeTableModule // Para usar TreeTable de PrimeNG
  ],
  templateUrl: './proyectos.component.html',
  styleUrls: ['./proyectos.component.css']
})
export class ProyectosComponent implements OnInit {
  proyectos: TreeNode[] = []; // Datos formateados para TreeTable
  cols: any[] = []; // Columnas de la tabla

  constructor(private proyectosService: ProyectosService) {}

  ngOnInit(): void {
    this.proyectosService.obtenerProyectosCombinados().subscribe((data) => {
      this.proyectos = this.formatDataToTree(data); // Formatear los datos para TreeTable
      this.cols = [
        { field: 'codigoProyecto', header: 'Código Proyecto' },
        { field: 'uuid', header: 'UUID' },
        { field: 'nombre', header: 'Nombre' },
        { field: 'fechaCreacion', header: 'Fecha Creación' },
        { field: 'estado', header: 'Estado Proyecto' },
        { field: 'codigoProyectoDetalle', header: 'Código Detalle' },
        { field: 'descripcion', header: 'Descripción' },
        { field: 'area', header: 'Área' },
        { field: 'estadoDetalle', header: 'Estado Detalle' }
      ];
    });
  }

  // Transforma los datos de la API en un formato compatible con TreeTable
  formatDataToTree(data: any[]): TreeNode[] {
    const nodes: TreeNode[] = [];

    data.forEach((proyecto) => {
      const proyectoNode: TreeNode = {
        data: {
          codigoProyecto: proyecto.codigoProyecto,
          uuid: proyecto.uuid,
          nombre: proyecto.nombre,
          fechaCreacion: proyecto.fechaCreacion,
          estado: proyecto.estado ? 'Activo' : 'Inactivo',
          codigoProyectoDetalle: '', // Vacío para el nivel de proyecto
          descripcion: '', // Vacío para el nivel de proyecto
          area: '', // Vacío para el nivel de proyecto
          estadoDetalle: '' // Vacío para el nivel de proyecto
        },
        children: this.processDetalles(proyecto.detalles)
      };

      nodes.push(proyectoNode);
    });

    return nodes;
  }

  // Función auxiliar para procesar los detalles de cada proyecto
  processDetalles(detalles: any[]): TreeNode[] {
    if (!detalles || detalles.length === 0) {
      return [
        {
          data: {
            codigoProyecto: '', // Vacío para el nivel de detalle sin datos
            uuid: '', // Vacío para el nivel de detalle sin datos
            nombre: 'Sin detalles', // Mensaje personalizado
            fechaCreacion: '', // Vacío para el nivel de detalle sin datos
            estado: '', // Vacío para el nivel de detalle sin datos
            codigoProyectoDetalle: '', // Vacío para el nivel de detalle sin datos
            descripcion: '', // Vacío para el nivel de detalle sin datos
            area: '', // Vacío para el nivel de detalle sin datos
            estadoDetalle: '' // Vacío para el nivel de detalle sin datos
          }
        }
      ];
    }

    return detalles.map((detalle) => ({
      data: {
        codigoProyecto: '', // Vacío para el nivel de detalle
        uuid: '', // Vacío para el nivel de detalle
        nombre: '', // Vacío para el nivel de detalle
        fechaCreacion: '', // Vacío para el nivel de detalle
        estado: '', // Vacío para el nivel de detalle
        codigoProyectoDetalle: detalle.codigoProyectoDetalle,
        descripcion: detalle.descripcion,
        area: detalle.area,
        estadoDetalle: detalle.estado ? 'Activo' : 'Inactivo'
      }
    }));
  }
}