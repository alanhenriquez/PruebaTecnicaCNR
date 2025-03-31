import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { TreeTableModule } from 'primeng/treetable';
import { TreeNode } from 'primeng/api';
import { ProyectosService } from '../../services/proyectos.service';
import { FormsModule } from '@angular/forms';
import { InputGroupModule } from 'primeng/inputgroup';
import { InputGroupAddonModule } from 'primeng/inputgroupaddon';
import { InputTextModule } from 'primeng/inputtext';
import { SelectModule } from 'primeng/select';
import { ButtonDemoComponent } from "../../../common/button-demo/button-demo.component";

@Component({
  selector: 'app-proyectos',
  standalone: true,
  imports: [
    CommonModule,
    HttpClientModule,
    TreeTableModule,
    FormsModule,
    InputGroupModule,
    InputGroupAddonModule,
    InputTextModule,
    SelectModule,
    ButtonDemoComponent
],
  templateUrl: './proyectos.component.html',
  styleUrls: ['./proyectos.component.css']
})
export class ProyectosComponent implements OnInit {
  proyectos: TreeNode[] = []; // Datos formateados para TreeTable
  cols: any[] = []; // Columnas de la tabla
  selectedNode: TreeNode | null = null; // Nodo seleccionado actualmente
  nuevoProyecto: any = {}; // Objeto para almacenar los datos del nuevo proyecto

  // Opciones para el campo "Estado"
  estados = [
    { label: 'Activo', value: true },
    { label: 'Inactivo', value: false }
  ];

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

  // Callback para manejar el evento de clic en una fila
  onRowClick(nodes: any): void {
    const parentId = nodes.node.data.codigoProyecto; // Obtener el ID del padre desde los datos del nodo
    if (parentId) {
      console.log('ID del padre:', parentId);
    } else {
      console.log('Este nodo no tiene padre (es un nodo raíz).');
    }
  }

  // Método para crear un nuevo proyecto
  crearNuevoProyecto(): void {
    this.proyectosService.crearProyecto(this.nuevoProyecto).subscribe((response) => {
      console.log('Proyecto creado:', response);

      // Agregar el nuevo proyecto a la lista de proyectos
      const nuevoNodo: TreeNode = {
        data: {
          seleccion: false,
          codigoProyecto: response.codigoProyecto,
          uuid: response.uuid,
          nombre: response.nombre,
          fechaCreacion: response.fechaCreacion,
          estado: response.estado ? 'Activo' : 'Inactivo',
          codigoProyectoDetalle: '',
          descripcion: '',
          area: '',
          estadoDetalle: '',
          parentId: null
        },
        children: []
      };

      this.proyectos.push(nuevoNodo);
      this.nuevoProyecto = {}; // Limpiar el formulario
    });
  }

  // Transforma los datos de la API en un formato compatible con TreeTable
  formatDataToTree(data: any[]): TreeNode[] {
    const nodes: TreeNode[] = [];

    data.forEach((proyecto) => {
      const proyectoNode: TreeNode = {
        data: {
          seleccion: false,
          codigoProyecto: proyecto.codigoProyecto,
          uuid: proyecto.uuid,
          nombre: proyecto.nombre,
          fechaCreacion: proyecto.fechaCreacion,
          estado: proyecto.estado ? 'Activo' : 'Inactivo',
          codigoProyectoDetalle: '',
          descripcion: '',
          area: '',
          estadoDetalle: '',
          parentId: null
        },
        children: this.processDetalles(proyecto.detalles, proyecto.uuid)
      };

      nodes.push(proyectoNode);
    });

    return nodes;
  }

  // Función auxiliar para procesar los detalles de cada proyecto
  processDetalles(detalles: any[], parentId: string): TreeNode[] {
    if (!detalles || detalles.length === 0) {
      return [
        {
          data: {
            seleccion: false,
            codigoProyecto: '',
            uuid: '',
            nombre: 'Sin detalles',
            fechaCreacion: '',
            estado: '',
            codigoProyectoDetalle: '',
            descripcion: '',
            area: '',
            estadoDetalle: '',
            parentId: parentId
          }
        }
      ];
    }

    return detalles.map((detalle) => ({
      data: {
        seleccion: false,
        codigoProyecto: '',
        uuid: '',
        nombre: '',
        fechaCreacion: '',
        estado: '',
        codigoProyectoDetalle: detalle.codigoProyectoDetalle,
        descripcion: detalle.descripcion,
        area: detalle.area,
        estadoDetalle: detalle.estado ? 'Activo' : 'Inactivo',
        parentId: parentId
      }
    }));
  }
}